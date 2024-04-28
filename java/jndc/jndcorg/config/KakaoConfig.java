package jndc.jndcorg.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KakaoConfig {
    @Value("${kakao.app-key}")
    private String kakaoAppKey;


}
