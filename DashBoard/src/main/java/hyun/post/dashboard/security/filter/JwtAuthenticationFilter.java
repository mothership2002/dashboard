package hyun.post.dashboard.security.filter;

import hyun.post.dashboard.exception.CustomAssert;
import hyun.post.dashboard.exception.auth.ExpiredAccessTokenException;
import hyun.post.dashboard.security.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtProvider jwtProvider;

//    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String authorization = req.getHeader(HttpHeaders.AUTHORIZATION);
//        CustomAssert.isTrue(jwtProvider.accessTokenValidate(authorization),
//                "Access Token Expired",
//                        ExpiredAccessTokenException.class);
//        filterChain.doFilter(req, resp);
//    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        CustomAssert.isTrue(jwtProvider.accessTokenValidate(authorization),
                "Access Token Expired",
                ExpiredAccessTokenException.class);
        return getAuthenticationManager().authenticate();
    }
}
