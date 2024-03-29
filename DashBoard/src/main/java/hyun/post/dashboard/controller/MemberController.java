package hyun.post.dashboard.controller;

import hyun.post.dashboard.aop.annotation.InboundContent;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.MemberDto;
import hyun.post.dashboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/add")
    @InboundContent(MemberDto.class)
    public ResponseEntity<CommonResponse<Long>> createMember(@RequestBody MemberDto memberDto) {
        return new ResponseEntity<>(new CommonResponse<>(
                "success", memberService.createMember(memberDto)),
                HttpStatus.CREATED);
    }
//
//    @PutMapping
//    public ResponseEntity<CommonResponse<Void>> updateMember() {
//
//    }
}
