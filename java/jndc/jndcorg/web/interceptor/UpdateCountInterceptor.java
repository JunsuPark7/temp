package jndc.jndcorg.web.interceptor;

import jndc.jndcorg.basic.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateCountInterceptor implements HandlerInterceptor {
    private final AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        int userUpdateCount = adminService.getUserUpdateCount();
        request.setAttribute("userUpdateCount",userUpdateCount);
        return true;
    }
}
