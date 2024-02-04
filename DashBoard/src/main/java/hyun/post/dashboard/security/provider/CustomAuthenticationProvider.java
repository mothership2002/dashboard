package hyun.post.dashboard.security.provider;

import hyun.post.dashboard.exception.auth.AlreadyHaveSessionException;
import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.auth.NoMatchMemberInfoException;
import hyun.post.dashboard.exception.auth.TryDuplicateLoginException;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.member.CustomMemberContext;
import hyun.post.dashboard.security.member.LoginSession;
import hyun.post.dashboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String account = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // 중복 로그인 관련. 로직 생각해야함.
        CustomAssert.isTrue(!memberService.duplicateLoginCheck(account),
                "Duplication Login", TryDuplicateLoginException.class);

        CustomMemberContext context =
                (CustomMemberContext) memberService.loadUserByUsername(account);
        Member member = context.getMember();

        CustomAssert.isTrue(passwordEncoder.matches(password, member.getPassword()),
                "Password Not Match", NoMatchMemberInfoException.class);

        memberService.getSession(account)
                .ifPresent(loginSession -> CustomAssert.isTrue(false,
                "Already Have Session;" + loginSession.getSessionCode(),
                AlreadyHaveSessionException.class));

        return new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private String getAccessTokenByHeader() {
        HttpServletRequest req;
        req = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String AUTHORIZATION = "Authorization";
        return req.getHeader(AUTHORIZATION);
    }


}
