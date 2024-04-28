package jndc.jndcorg.web.controller.admin;


import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.service.AdminService;
import jndc.jndcorg.web.aop.LogManage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminPermissionController {

    private final AdminService adminService;

    @GetMapping("/permission")
    public String adminFlag()
    {
        return "admin/adminPermission";
    }

    @PostMapping("/permission/search")
    public String permissionSearch(
            @RequestParam("searchCon") String searchCon,
            @RequestParam("searchInput") String searchInput,
            Model model
    ){
        List<User> users = adminService.getUsers(searchCon, searchInput);
        model.addAttribute("users",users);
        return "admin/adminPermission";
    }



    @LogManage
    @PostMapping("/permission/changeFlag")
    public String changeFlag(
            HttpServletRequest request,
            @RequestParam("userId") String userId,
            @RequestParam("flag") String flag,
            RedirectAttributes redirectAttributes
    ){

        adminService.changeFlag(userId,flag);

        redirectAttributes.addAttribute("updateStatus",true);
        return "redirect:/admin/permission";
    }





}

