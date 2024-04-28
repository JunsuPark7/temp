//package jndc.jndcorg.web.controller;
//
//import jndc.jndcorg.user.domain.LoginService;
//import jndc.jndcorg.user.domain.User;
//import jndc.jndcorg.web.login.LoginForm;
//import jndc.jndcorg.web.login.SessionConst;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class LoginController {
//    private final LoginService loginService;
//    @GetMapping("/login")
//    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
//        return "login/loginForm";
//    }
//
//    @PostMapping("/login")
//    public String loginV4(@Valid @ModelAttribute LoginForm loginForm,
//                          BindingResult bindingResult,
//                          @RequestParam(defaultValue = "/") String redirectURL,
//                          HttpServletRequest request){
//
//        if(bindingResult.hasErrors()){
//            return "/login";
//        }
//
//        User loginUser = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
//        log.info("login ? {}",loginUser);
//
//        if(loginUser == null){
//            bindingResult.reject("loginFail","아이디 혹은 비밀번호가 맞지 않습니다.");
//            return "/login";
//        }
//
//        //HttpServletRequest에 세션을 생성 할 수 있음.
//        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성.
//        // 세션이 없으면 새로운 세션을 생성해서 반환!!
//        HttpSession session = request.getSession();
//        session.setAttribute(SessionConst.LOGIN_USER, loginUser);
//        return "redirect:" + redirectURL;
//    }
//
//
//
//
//
//
//    @PostMapping("/logout")
//    public String logoutV3(HttpServletRequest request){
//        HttpSession session = request.getSession(false);
//
//        if(session != null){
//            session.invalidate();
//        }
//        return "redirect:/";
//    }
//
//}
