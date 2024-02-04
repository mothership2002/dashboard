package hyun.post.dashboard.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.exception.auth.ExpiredAccessTokenException;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.security.handler.CommonRespHeaderComponent;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomExceptionFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final CommonRespHeaderComponent headerComponent;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(req, resp);
        } catch (ExpiredJwtException e) {
            exceptionResponse(req, resp, new ExpiredAccessTokenException("Expired Access Token"));
        } catch (ExpiredAccessTokenException e) {
            exceptionResponse(req, resp, e);
        }
    }

    private void exceptionResponse(HttpServletRequest req, HttpServletResponse resp, Exception e) throws IOException {
        headerComponent.addContentTypeResponse(resp);
        objectMapper.writeValue(resp.getWriter(),
                new CommonResponse<>(e.getMessage(), req.getHeader(HttpHeaders.AUTHORIZATION)));
    }
}
