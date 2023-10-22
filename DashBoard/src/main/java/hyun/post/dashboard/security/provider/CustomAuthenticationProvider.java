package hyun.post.dashboard.security.provider;

import hyun.post.dashboard.exception.TryDuplicateLoginException;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.repository.redis.AccessTokenRepository;
import hyun.post.dashboard.repository.redis.SyncLoginRepository;
import hyun.post.dashboard.security.Member.CustomMemberContext;
import hyun.post.dashboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AccessTokenRepository accessTokenRepository;
    private final SyncLoginRepository syncLoginRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String account = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        CustomMemberContext context = (CustomMemberContext) memberService.loadUserByUsername(account);
        Member member = context.getMember();

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BadCredentialsException("password not match");
        }
        if (syncLoginRepository.findById(account).isPresent() ||
                accessTokenRepository.findById(account).isPresent()) {
            throw new TryDuplicateLoginException("duplication login");
        }

        return new UsernamePasswordAuthenticationToken(member, null);
    }



    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
