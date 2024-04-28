package jndc.jndcorg.web.controller;


import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.UserRepository;
import jndc.jndcorg.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(){
        return "user/home";
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping("/login")
    public String requestBodyJson(
            @RequestParam("userId") String userId,
            HttpServletRequest request
    ) throws IOException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
            HttpSession session = request.getSession();
            //세션에 로그인 회원 정보 보관
            session.setAttribute(SessionConst.LOGIN_USER, user);
            return "/home";
        }else{
            return "/";
        }
    }


    //로그인 -> 홈컨트롤러
    //접속 로그 남기기 위함.
    //어드민 체크.
    @GetMapping("/home")
    public String homeControl(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        //세션이 없으면 home
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(SessionConst.LOGIN_USER);

        //admin -> user 로그인시 로직.
        if (loginUser.getFlag() == 1 &&
            session.getAttribute(SessionConst.ADMIN_CHECK) == null) {
            return "redirect:/admin/manage";
        }
        return "redirect:user/userHome";
    }


    //다크모드 활성화
    @ResponseBody
    @GetMapping("/darkmode")
    public void darkMode(@RequestParam("darkModeFlag") String darkModeFlag, HttpServletResponse response) {
        Cookie darkModeCookie = new Cookie("darkModeFlag", darkModeFlag);
        response.addCookie(darkModeCookie);
    }

    @Value("${kakao.app-key}")
    private String kakaoApiKey;

    @ResponseBody
    @GetMapping("/api/data")
    public String darkMode() {
        log.info(kakaoApiKey);

        return kakaoApiKey;
    }




    @GetMapping("/user/userHome")
    public String userHome() {
        return "user/userHome";
    }



    //로그아웃.
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SessionConst.LOGIN_USER);
        }
        return "redirect:/";
    }



    //관리자로 다시 로그인
    @PostMapping("/adminCheck")
    public String admin_login(HttpServletRequest request) {
        //세션을 삭제한다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(SessionConst.ADMIN_CHECK);
        }
        return "redirect:/admin/manage";
    }





}
