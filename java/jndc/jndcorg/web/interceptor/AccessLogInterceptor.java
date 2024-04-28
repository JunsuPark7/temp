package jndc.jndcorg.web.interceptor;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.web.session.SessionConst;
import jndc.jndcorg.web.utils.ClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Slf4j
public class AccessLogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uuid = UUID.randomUUID().toString().substring(0,8);

        request.setAttribute(LOG_ID, uuid);
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);
        log.info("접속로그 [userId={}] [이름={}] [직위={}] [부서={}] [접속시간={}] [requestUUID={}]",
                loginUser.getUserId(),
                loginUser.getName(),
                loginUser.getRole(),
                loginUser.getDept().getDeptName(),
                ClientUtils.getCurrentTime(),
                uuid);
        return true;
    }
}
