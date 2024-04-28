package jndc.jndcorg.web.controller.user;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.UserRepository;
import jndc.jndcorg.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequiredArgsConstructor
@Slf4j
public class UserSearchController {


    private final UserRepository userRepository;

    @GetMapping("/userSearch")
    public String userSearch(
            HttpServletRequest request,
            Model model
    ){

        //세션 -> 최근조회기록
        List<User> users = getSessionUsers(request);
        model.addAttribute("searchRecode",true);
        model.addAttribute("users",users);
        return "user/userSearch";
    }

    @PostMapping("/userSearch")
    public String userSearchForm(
            @RequestParam("inputValue") String inputValue,
            Model model
    ){
        List<User> users = userRepository.findUserByInputValue(inputValue);
        model.addAttribute("users", users);
        if(users.isEmpty()){
            model.addAttribute("isNotResult",true);
        }

        return "user/userSearch";
    }


    @GetMapping("/userSearch/RecodeDelete")
    public String userRecodeDelete(HttpServletRequest request){

        throw new RuntimeException("테스트 에러");
        //세션을 삭제한다.
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.getAttributeNames().asIterator().forEachRemaining(sessionName -> {
//                if (!sessionName.equals(SessionConst.LOGIN_USER) && !sessionName.equals(SessionConst.ADMIN_CHECK)) {
//                    session.removeAttribute(sessionName);
//                }
//            });
//        }
//        return "redirect:/userSearch";
    }

    private static List<User> getSessionUsers(HttpServletRequest request) {

        List<User> users = new ArrayList<>();
        HttpSession session = request.getSession(false);
        session.getAttributeNames().asIterator().forEachRemaining(sessionName -> {
            if (!sessionName.equals(SessionConst.LOGIN_USER) && !sessionName.equals(SessionConst.ADMIN_CHECK)) {
                users.add((User) session.getAttribute(sessionName));
            }
        });
        return users;
    }
}
