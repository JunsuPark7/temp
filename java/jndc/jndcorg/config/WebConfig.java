package jndc.jndcorg.config;

import jndc.jndcorg.web.interceptor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final UpdateCountInterceptor updateCountInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인(세션) 체킹 로직
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login" , "/home", "/images/**",
                        "/*.ico",
                        "/css/**","/js/**","/img/**","/html/**","/photos/**",
                        "/error"
                );
        //에러 체킹 로직.
        registry.addInterceptor(new ErrorLogInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login" , "/home",
                        "/*.ico",
                        "/css/**","/js/**","/img/**","/html/**","/photos/**",
                        "/error"
                );
        // 어드민 권한 체킹
        registry.addInterceptor(new AdminCheckInterceptor())
                .order(3)
                .addPathPatterns("/admin/**");
        // 접속 로그
        registry.addInterceptor(new AccessLogInterceptor())
                .order(4)
                .addPathPatterns("/home");
        // 어드민 수정 요청 개수 반환
        registry.addInterceptor(updateCountInterceptor)
                .order(5)
                .addPathPatterns("/admin/**");
        // 다크모드 쿠키 반환
        registry.addInterceptor(new DarkModeInterceptor())
                .order(6)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login" , "/home", "/images/**",
                        "/*.ico",
                        "/css/**","/js/**","/img/**","/html/**","/photos/**",
                        "/error"
                );
    }

}
