package hyun.post.dashboard.controller;

import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.JwtDto;
import hyun.post.dashboard.model.dto.MemberLoginDto;
import hyun.post.dashboard.security.jwt.RefreshToken;
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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody JwtDto jwtDto) {
        return new ResponseEntity<>(new CommonResponse<>("renew Access Token",
                memberService.validateRefreshToken(jwtDto.getAccessToken(), jwtDto.getRefreshToken())),
                HttpStatus.CREATED);
    }
}
