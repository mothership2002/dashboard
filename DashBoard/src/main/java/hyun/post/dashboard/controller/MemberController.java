package hyun.post.dashboard.controller;

import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.MemberDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/add")
    public ResponseEntity<CommonResponse<Long>> createMember(@RequestBody MemberDto memberDto) {
        Member member = new Member(memberDto.getAccount(),
                passwordEncoder.encode(memberDto.getPassword()),
                memberDto.getEmail());
        return new ResponseEntity<>(new CommonResponse<>("success", memberService.createMember(member)), HttpStatus.CREATED);
    }
}
