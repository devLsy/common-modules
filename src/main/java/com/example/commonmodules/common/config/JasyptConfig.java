package com.example.commonmodules.common.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class JasyptConfig {

    private final Environment environment;
    // 전달받은 개인키 기반 암복호 객체를 빈으로 등록
    @Bean(name="jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
//        String password = System.getProperty("jasypt.password");
        String password = environment.getProperty("jasypt.password");

        log.info("✅ jasypt.password from Environment => {}", password);

        if (password == null || password.isBlank()) {
            throw new IllegalStateException("jasypt.password system property not set!");
        }
//        return getEncryptor(password);

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        config.setPassword(password);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;

    }

    /**
     * 인자값으로 받은 키값을 가진 암복호화 객체 반환
     * @param key
     * @return
     */
//    private StringEncryptor getEncryptor(String key) {
//        // Config
//        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
//        config.setPassword(key);                    // 암호화 키
//        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");    // 암호화 알고리즘
//        config.setKeyObtentionIterations("1000");   // 반복할 해싱 횟수
//        config.setPoolSize("1");                    // 인스턴스 pool
//        config.setProviderName("SunJCE");
//        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");    // salt 생성 클래스
//        config.setStringOutputType("base64");       // 인코딩 방식
//
//        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
//        encryptor.setConfig(config);
//        return encryptor;
//    }
}
