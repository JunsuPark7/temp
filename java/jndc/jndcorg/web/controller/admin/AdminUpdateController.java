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
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUpdateController {

    private final AdminService adminService;



    @GetMapping("/updateList")
    public String adminUpdate(Model model){
        List<User> userUpdateList = adminService.getUserUpdateList();
        model.addAttribute("users",userUpdateList);

        return "admin/adminUpdate";
    }


    @PostMapping("/update/search")
    public String updateSearch(
            @RequestParam("searchInput") String searchInput,
            Model model
    ){
        List<User> users = adminService.getUserUpdateList(searchInput);
        model.addAttribute("users",users);
        return "admin/adminUpdate";
    }


    @LogManage
    @PostMapping("/update/userUpdateCheck")
    public String updateCheck(
            HttpServletRequest request,
            @RequestParam("userId") String userId,
            RedirectAttributes redirectAttributes
    ){
        adminService.updateCheck(userId);
        redirectAttributes.addAttribute("updateStatus", true);
        return "redirect:/admin/updateList";
    }



    @ResponseBody
    @PostMapping("/update/userUpdateAll")
    public String updateAll(
            HttpServletRequest request,
            @RequestBody Map<String, List<String>> requestParam
    ) {
        List<String> userIds = requestParam.get("userIds");
        for (String userId : userIds) {
            adminService.updateCheck(request,userId);
        }
        return String.valueOf(userIds.size());
    }

}

