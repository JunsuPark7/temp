package jndc.jndcorg.web.aop;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.UserRepository;
import jndc.jndcorg.basic.service.LogService;
import jndc.jndcorg.web.session.SessionConst;
import jndc.jndcorg.web.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class LogAspect {
    private final LogService logService;
    private final UserRepository userRepository;

    @AfterReturning("@annotation(jndc.jndcorg.web.aop.LogManage) && args(request,userId,..)")
    public void afterControllerExecution(JoinPoint joinPoint,
                                         HttpServletRequest request,
                                         String userId
    ) {
        //user -> 세션 작업자
        //userId -> 타겟대상
        User user = ClientUtils.getLoginUser(request);
        String ip = ClientUtils.getRemoteIP(request);
        String logWork = getLogWork(joinPoint);

        log.info("비즈니스 로그 성공 [userId={}] [작업자={}] [userIp={}] [작업={}] [URI={}]",
                user.getUserId(),
                user.getName(),
                ip,
                logWork,
                request.getRequestURI()
                );
        logService.createLog(user, ip, userId, logWork, "T");
    }

    @AfterThrowing("@annotation(jndc.jndcorg.web.aop.LogManage) && args(request,userId,..)")
    public void afterControllerException(JoinPoint joinPoint,
                                         HttpServletRequest request,
                                         String userId
    ) {
        User user = ClientUtils.getLoginUser(request);
        String ip = ClientUtils.getRemoteIP(request);
        String logWork = getLogWork(joinPoint);

        log.info("비즈니스 로그 실패 [userId={}] [작업자={}] [userIp={}] [작업={}] [URI={}]",
                user.getUserId(),
                user.getName(),
                ip,
                logWork,
                request.getRequestURI()
        );
        logService.createLog(user, ip, userId, logWork, "F");
    }


    private static String getLogWork(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String logWork = "";
        if (methodName.equals("changeFlag")) {
            logWork = "권한관리";
        } else if (methodName.equals("updateCheck") || methodName.equals("updateAllLogic") ) {
            logWork = "수정확인";
        } else if (methodName.equals("manageUpdateUser")) {
            logWork = "직원수정";
        } else if (methodName.equals("manageCreateUser")) {
            logWork = "직원생성";
        } else if (methodName.equals("commentComplte")) {
            logWork = "문의완료";
        }
        return logWork;
    }


//    private User getLoginUser(HttpServletRequest request) {
//        //세션이 없으면 home
//        HttpSession session = request.getSession(false);
//        return (User) session.getAttribute(SessionConst.LOGIN_USER);
//    }


}