package com.example.commonmodules.common.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ApiLoggingAspect {

    /**
     * CustomApiLogger 애노테이션이 붙은 클래스 또는 메서드에 대해 AOP 적용
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    // fixme: 경로 수정 필요
    @Around("@annotation(com.miroit.contentapi.global.annotation.common.CustomApiLogger) || @within(com.miroit.contentapi.global.annotation.common.CustomApiLogger)")
    public Object logAnnotatedApi(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getCurrentHttpRequest();

        // 요청 정보 추출 (없을 경우 N/A로 처리)
        String uri = request != null ? request.getRequestURI() : "N/A";
        String method = request != null ? request.getMethod() : "N/A";
        String clientIp = request != null ? request.getRemoteAddr() : "N/A";

        // 호출된 클래스 및 메서드 이름 추출
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String classMethod = signature.getDeclaringType().getSimpleName() + "." + signature.getName();

        // 요청 로그 시작
        log.info("==================== [@ApiLoggable] start ====================");
        log.info("Path = {} {}", method, uri);
        log.info("IP = {}", clientIp);
        log.info("Args = {}", Arrays.toString(joinPoint.getArgs()));

        // 시간 측정
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();

        // 요청 로그 종료
        log.info("Done =  {} | Time = {}ms", classMethod, (end - start));
        log.info("==================== [@ApiLoggable] end ====================");

        return result;
    }

    /**
     * 현재 요청(HttpServletRequest)을 가져오는 메서드
     *
     * @return HttpServletRequest
     */
    private HttpServletRequest getCurrentHttpRequest() {
        var attributes = RequestContextHolder.getRequestAttributes();
        if (attributes instanceof ServletRequestAttributes servletAttributes) {
            return servletAttributes.getRequest();
        }
        return null;
    }
}
