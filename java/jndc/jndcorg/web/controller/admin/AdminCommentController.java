package jndc.jndcorg.web.controller.admin;


import jndc.jndcorg.basic.domain.entity.Comment;
import jndc.jndcorg.basic.domain.entity.Log;
import jndc.jndcorg.basic.domain.entity.User;
import jndc.jndcorg.basic.service.CommentService;
import jndc.jndcorg.basic.service.LogService;
import jndc.jndcorg.web.aop.LogManage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCommentController {

    private final CommentService commentService;

    @GetMapping("/comment")
    public String adminComment() {
        return "admin/adminComment";
    }


    @GetMapping("/comment/search")
    public String commentSearch(
            @RequestParam("searchCon") String searchCon,
            @RequestParam("searchInput") String searchInput,
            @RequestParam("searchDate") String searchDate,
            Model model
    ) {
        List<Comment> commentList = commentService.getCommentList(searchCon, searchInput, searchDate);
        model.addAttribute("comments", commentList);
        return "admin/adminComment";
    }


    @LogManage
    @ResponseBody
    @PostMapping("/comment/complete")
    public void commentComplte(
            HttpServletRequest request,
            @RequestParam("userId") String userId,
            @RequestParam String commentId
    ) {
        commentService.complte(commentId);
    }


//
//    @ResponseBody
//    @GetMapping("/log/search")
//    public Page<Log> logSearch(
//            @RequestParam("searchCon") String searchCon,
//            @RequestParam("searchInput") String searchInput,
//            @RequestParam("searchDate") String searchDate,
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size,
//            Model model
//    ){
//        return logService.getLogList(searchCon, searchInput, searchDate, page, size);
//    }
//
//
//    @ResponseBody
//    @PostMapping("/log/searchUserId")
//    public User adminSearchLog(
//            @RequestParam("userId") String userId
//    ) {
//        return logService.getUser(userId);
//    }


}

