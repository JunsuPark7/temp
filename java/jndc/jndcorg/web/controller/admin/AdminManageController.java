package jndc.jndcorg.web.controller.admin;


import jndc.jndcorg.basic.domain.entity.Dept;
import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.repository.DeptRepository;
import jndc.jndcorg.basic.service.AdminService;
import jndc.jndcorg.basic.service.ProfileService;
import jndc.jndcorg.web.aop.LogManage;
import jndc.jndcorg.web.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminManageController {

    private final DeptRepository deptRepository;
    private final AdminService adminService;
    private final ProfileService profileService;


    //일반 유저 로그인용 check
    @GetMapping("/userHome")
    public String homeControl(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes){
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.ADMIN_CHECK, true);
        return "user/userHome";
    }


    @GetMapping("/manage")
    public String adminManage(Model model){
        List<User> users = adminService.getUsers();
        List<Dept> depts = deptRepository.findAll();
        model.addAttribute("depts",depts);
        model.addAttribute("users",users);
        return "admin/adminManage";
    }

    @PostMapping("/manage/search")
    public String manageSearch(
            @RequestParam("searchCon") String searchCon,
            @RequestParam("searchInput") String searchInput,
            Model model
    ){
        List<User> users = adminService.getUsers(searchCon, searchInput);
        List<Dept> depts = deptRepository.findAll();
        model.addAttribute("depts",depts);
        model.addAttribute("users",users);
        return "admin/adminManage";
    }



    @ResponseBody
    @PostMapping("/manage/checkUserId")
    public String checkUserId(
            @RequestParam("userId") String userId
    ) {
        if(adminService.isUserDuplicate(userId)){
            return "고유 아이디 값이 중복입니다. 값을 바꿔주세요.";
        }
        return "중복이 아닙니다. 체크 완료 했습니다.";
    }



    @LogManage
    @PostMapping("/manage/createUser")
    public String manageCreateUser(
            HttpServletRequest request,
            @RequestParam("userId") String userId,
            @ModelAttribute("User") User updateParam,
            @RequestParam("deptId") String deptId,
            @RequestParam MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {

//        validation check 로직이 필요
//        아이디 중복 체크 넘기기.
        if(adminService.isUserDuplicate(updateParam.getUserId())){
            redirectAttributes.addAttribute("createStatus", false);
            return "redirect:/admin/manage";
        }

        //부서값 매핑
        Dept dept = deptRepository.findDeptNameByDeptId(deptId);
        updateParam.setDept(dept);


        //데이터 등록.
        adminService.createUser(updateParam);


        //사진 교체.
        if(!file.isEmpty()){
            profileService.uploadProfile(file, updateParam.getUserId());
        }

        redirectAttributes.addAttribute("createStatus", true);
        return "redirect:/admin/manage";
    }



    @LogManage
    @PostMapping("/manage/updateUser")
    public String manageUpdateUser(
            HttpServletRequest request,
            @RequestParam("userId") String userId,
            @ModelAttribute("User") User updateParam,
            @RequestParam("deptId") String deptId,
            @RequestParam MultipartFile file,
            RedirectAttributes redirectAttributes
    ) throws IOException {


        Dept dept = deptRepository.findDeptNameByDeptId(deptId);
        updateParam.setDept(dept);

        //사진 교체.
        if(!file.isEmpty()){
            profileService.uploadProfile(file, updateParam.getUserId());
        }

        //데이터 교체.
        adminService.updateUser(updateParam);
        redirectAttributes.addAttribute("updateStatus", true);

        return "redirect:/admin/manage";
    }





}

