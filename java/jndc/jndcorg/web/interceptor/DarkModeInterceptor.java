package jndc.jndcorg.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Slf4j
public class DarkModeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("darkModeFlag".equals(cookie.getName())) {
                    String darkModeFlag = cookie.getValue();
                    request.setAttribute("darkModeFlag",darkModeFlag);
                    if (darkModeFlag.equals("dark")) {
                        request.setAttribute("darkModeCheck",true);
                    } else {
                        request.setAttribute("darkModeCheck",false);
                    }
                }
            }
        }
        return true;
    }
}
