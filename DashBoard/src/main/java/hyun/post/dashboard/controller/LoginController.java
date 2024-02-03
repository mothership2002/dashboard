package hyun.post.dashboard.controller;

import hyun.post.dashboard.model.dto.MemberLoginDto;
import hyun.post.dashboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @PostMapping("/re-connect")
    public ResponseEntity<?> sessionRenew(@RequestBody MemberLoginDto memberLoginDto) {
        boolean isSession = memberService.sessionCheck(memberLoginDto.getAccount(), memberLoginDto.getSessionCode());
        if (isSession) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/refrech")
    public ResponseEntity<?> refreshAccessToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
