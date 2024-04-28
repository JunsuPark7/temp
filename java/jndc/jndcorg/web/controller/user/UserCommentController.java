package jndc.jndcorg.web.controller.user;

import jndc.jndcorg.basic.service.CommentService;
import jndc.jndcorg.web.utils.ClientUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserCommentController {

    private final CommentService commentService;

    @ResponseBody
    @PostMapping("/user/comment")
    public void createComment(HttpServletRequest request,
                                @RequestParam String commentContent,
                                @RequestParam String commentCategory
                                ) {
        commentService.createComment(
                ClientUtils.getLoginUser(request),
                commentContent,
                commentCategory
        );
    }



}
