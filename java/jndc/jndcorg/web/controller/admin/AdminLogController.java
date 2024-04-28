package jndc.jndcorg.web.controller.admin;


import jndc.jndcorg.basic.domain.entity.Log;
import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminLogController {

    private final LogService logService;

    @GetMapping("/log")
    public String adminLog(){
        return "admin/adminLog";
    }

    @ResponseBody
    @GetMapping("/log/search")
    public Page<Log> logSearch(
            @RequestParam("searchCon") String searchCon,
            @RequestParam("searchInput") String searchInput,
            @RequestParam("searchDate") String searchDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model
    ){
        return logService.getLogList(searchCon, searchInput, searchDate, page, size);
    }


    @ResponseBody
    @PostMapping("/log/searchUserId")
    public User adminSearchLog(
            @RequestParam("userId") String userId
    ) {
        return logService.getUser(userId);
    }



}

