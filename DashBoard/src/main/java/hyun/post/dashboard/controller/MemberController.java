package hyun.post.dashboard.controller;

import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.MemberDto;
import hyun.post.dashboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;


    @PostMapping("/add")
    public ResponseEntity<CommonResponse<Long>> createMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(new CommonResponse<>("success", memberService.createMember(memberDto)), HttpStatus.CREATED);
    }
}
