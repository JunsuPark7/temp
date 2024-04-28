//package jndc.jndcorg.basic.service;
//
//import jndc.jndcorg.basic.domain.LoginUser;
//import jndc.jndcorg.basic.domain.entity.User;
//import jndc.jndcorg.basic.repository.LoginRepositroy;
//import jndc.jndcorg.basic.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//
//import org.springframework.stereotype.Service;
//
//
//import java.util.Optional;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class LoginService {
//
//    private final LoginRepositroy loginRepositroy;
//    private final PasswordEncoder passwordEncoder;
//
//
//        public User login(String loginId, String uiPassword) {
//
//
//
//
//            Optional<User> loginUser = loginRepositroy.findByLoginId(loginId);
//            if(loginUser.isEmpty()){
//                log.info("loginUser empty");
//            }
//
//            User user = loginUser.get();
//            LoginUser loginUser1 = user.getLoginUser();
//            String password = loginUser1.getUiPassword();
//
//            log.info("보낸 패스워드 ={}", uiPassword);
//            log.info("user={}",user);
//            log.info("loginuser={}",loginUser1);
//            log.info("uiPassword1={}",password);
//
//
//            boolean matches = passwordEncoder.matches(uiPassword, password);
//            if (matches){
//                log.info("gggo");
//            } else {
//                log.info("nnno");
//            }
//
////            boolean isPasswordCorrect = passwordEncoder.matches(uiPassword, password);
////
////            if (isPasswordCorrect) {
////                System.out.println("Password is correct!");
////                return user;
////            } else {
////                System.out.println("Password is incorrect!");
////            }
//
//
//            return null;
//
//        }
//
//
//
//
//
//
//
//
////        return loginRepositroy.findByLoginId(loginId)
////                .filter(u -> passwordEncoder.matches(encodedPassword, uiPassword1))
////                .orElse(null);
//    // optional 객체에 값이 비어 있다면 null 값을 반환함.
//
//}
