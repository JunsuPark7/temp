package jndc.jndcorg.config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import org.slf4j.LoggerFactory;
public class LoggingConfiguration {



    public static void configure() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        Logger rootLogger = loggerContext.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.INFO); // 루트 로거의 로깅 레벨 설정

        // 파일 앱렌더 추가
        RollingFileAppender appender = new RollingFileAppender();
        appender.setContext(loggerContext);
        appender.setFile("daily_logs.log");

        // 롤링 정책 설정
        SizeAndTimeBasedRollingPolicy rollingPolicy = new SizeAndTimeBasedRollingPolicy();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(appender);
        rollingPolicy.setFileNamePattern("daily_logs-%d{yyyy-MM-dd}.log");
        rollingPolicy.setMaxHistory(30); // 최대 보관 일수 설정

        appender.setRollingPolicy(rollingPolicy);
        rollingPolicy.start();

        // 로그 패턴 설정
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n");
        encoder.start();

        appender.setEncoder(encoder);
        appender.start();

        rootLogger.addAppender(appender);
    }
}
