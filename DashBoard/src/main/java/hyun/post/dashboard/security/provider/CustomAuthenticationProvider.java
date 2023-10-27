package hyun.post.dashboard.security.provider;

import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.TryDuplicateLoginException;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.Member.CustomMemberContext;
import hyun.post.dashboard.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String account = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        String accessToken = getAccessTokenByHeader();

        CustomMemberContext context =
                (CustomMemberContext) memberService.loadUserByUsername(account);
        Member member = context.getMember();

        CustomAssert.isTrue(!passwordEncoder.matches(password, member.getPassword()),
                "Password Not Match", BadCredentialsException.class);

        CustomAssert.isTrue(memberService.duplicateLoginCheck(account, accessToken),
                "Duplication Login", TryDuplicateLoginException.class);

        return new UsernamePasswordAuthenticationToken(member, null);
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
