package com.server.lab.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "s3FileUploadExecutor")
    public Executor taskExecutor() {
        int coreCount = Runtime.getRuntime().availableProcessors();//jvm 논리적 코어개수(물리적보다 합리적임)
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);//기본적으로 1~2개의 파일업로드를 가정하기에 2개로 세팅
        executor.setMaxPoolSize(coreCount * 2);//직접 저장이아닌 upload i/o 연산 처리라서 2배 정도로, context switching은 사실 코어 개수대로 하는게 좋긴함
        executor.setQueueCapacity(100);//큐가 너무 크면, 된다는 가능성하에서 대기를 길게해야하기 때문에 적정하게 해야한다.
        executor.setThreadNamePrefix("s3upload-");//디버깅을 위한 접두어
        executor.initialize();
        return executor;
    }
}
