package hyun.post.dashboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {

    @GetMapping("/member")
    public void getMember() {

    }

    @GetMapping("/post")
    public void getPost() {

    }

    @GetMapping("/reply")
    public void getReply() {

    }



}
