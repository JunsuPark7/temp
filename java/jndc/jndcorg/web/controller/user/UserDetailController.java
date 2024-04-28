package jndc.jndcorg.web.controller.user;

import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.domain.webDTO.UserUpdateForm;
import jndc.jndcorg.basic.repository.DeptRepository;
import jndc.jndcorg.basic.repository.UserRepository;
import jndc.jndcorg.basic.service.ProfileService;
import jndc.jndcorg.web.session.SessionConst;
import jndc.jndcorg.web.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserDetailController {

    private final UserRepository userRepository;
    private final ProfileService profileService;

    private static final String UPLOAD_DIR = "src/main/resources/static/photos/";






    @GetMapping("/userMyPage")
    public String userDetail(HttpServletRequest request, Model model){
        model.addAttribute("user", ClientUtils.getLoginUser(request));
        return "user/userMyPage";
    }

    @GetMapping("/userMyPage/Form")
    public String userDetailForm(HttpServletRequest request, Model model){
        model.addAttribute("user", ClientUtils.getLoginUser(request));
        return "user/userMyPageForm";
    }

    @GetMapping("/userDetailPage/{userId}")
    public String userDetailPage(
            @PathVariable(value = "userId") String userId,
            Model model,
            HttpServletRequest request
    ){
        User user = userRepository.findById(userId).orElse(null);
        createSessionUser(request, user);
        model.addAttribute("user", user);
        return "user/userDetailPage";
    }




    @PostMapping("/user/updateUser")
    public String userUpdate(
            @RequestParam MultipartFile file,
            @ModelAttribute("userUpdateForm") UserUpdateForm userUpdateForm,
            RedirectAttributes redirectAttributes
    ) throws IOException {

        if(!file.isEmpty()){
            profileService.uploadProfile(file, userUpdateForm.getUserId());
        }

        boolean updateComplete = profileService.update(userUpdateForm);
        if(updateComplete){
            redirectAttributes.addAttribute("updateStatus", true);
        }
        return "redirect:/userMyPage";
    }


    @ResponseBody
    @GetMapping("/images/{filename}")
    public UrlResource showImage(@PathVariable String filename) throws MalformedURLException {
        return new UrlResource("file:" + UPLOAD_DIR + filename);
    }

    private void createSessionUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute(user.getUserId(), user);
    }


}
