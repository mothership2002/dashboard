package hyun.post.dashboard.security.filter;

import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authenticationHeader)) {
            String accessToken = jwtProvider.extractToken(authenticationHeader);
            Member member = jwtProvider.findMemberByToken(accessToken);
            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities()));
        }
        filterChain.doFilter(request, response);
    }
}


//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
//        super(authenticationManager);
////        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/**"));
//        this.jwtProvider = jwtProvider;
//    }

//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        CustomAssert.hasText(authenticationHeader,
//                "No Haven't Token",
//                EmptyTokenException.class);
//        String accessToken = jwtProvider.extractToken(authenticationHeader);
//        CustomAssert.isTrue(jwtProvider.accessTokenValidate(accessToken),
//                "Access Token Expired",
//                ExpiredAccessTokenException.class);
//        Claims claims = jwtProvider.extractBody(accessToken);
//        Long memberId = claims.get("memberId", Long.class);
//        String account = claims.get("account", String.class);
//
//        return getAuthenticationManager()
//                .authenticate(new JwtAuthenticationToken(account, memberId));
//    }
//}
