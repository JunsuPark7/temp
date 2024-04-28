package jndc.jndcorg.web.interceptor;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.web.session.SessionConst;
import jndc.jndcorg.web.utils.ClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class ErrorLogInterceptor implements HandlerInterceptor {



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        if(ex != null){
            String requestURI = request.getRequestURI();
            HttpSession session = request.getSession(false);
            User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
            log.info("에러로그 [userId={}] [이름={}] [직위={}] [부서={}] [에러시간={}] [URL={}] [에러메시지={}]",
                    loginUser.getUserId(),
                    loginUser.getName(),
                    loginUser.getRole(),
                    loginUser.getDept().getDeptName(),
                    ClientUtils.getCurrentTime(),
                    requestURI,
                    ex.getMessage()
            );
            log.error("에러발생", ex);
        }

    }

}
