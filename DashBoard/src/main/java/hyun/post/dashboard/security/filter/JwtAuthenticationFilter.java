package hyun.post.dashboard.security.filter;

import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.auth.EmptyTokenException;
import hyun.post.dashboard.exception.auth.ExpiredAccessTokenException;
import hyun.post.dashboard.security.member.CustomMemberContext;
import hyun.post.dashboard.security.provider.JwtProvider;
import hyun.post.dashboard.service.MemberService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    public JwtAuthenticationFilter(JwtProvider jwtProvider, MemberService memberService) {
        this.jwtProvider = jwtProvider;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        CustomAssert.hasText(authenticationHeader,
                "No Haven't Token",
                EmptyTokenException.class);
        String accessToken = jwtProvider.extractToken(authenticationHeader);
        CustomAssert.isTrue(jwtProvider.accessTokenValidate(accessToken),
                "Access Token Expired",
                ExpiredAccessTokenException.class);
        Claims claims = jwtProvider.extractBody(accessToken);
        Long memberId = claims.get("memberId", Long.class);
        String account = claims.get("account", String.class);
        CustomMemberContext context = (CustomMemberContext) memberService.loadUserByUsername(memberId, account);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(context.getMember(), null, context.getMember().getAuthorities()));
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
