package hyun.post.dashboard.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.JsonWebToken;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.provider.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final CommonRespHeaderComponent headerComponent;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JsonWebToken jsonWebToken = jwtProvider.saveToken(member);
        headerComponent.addHeader(response, jwtProvider.getAccessTokenHeaderName(), jsonWebToken.getAccessToken().getAccessToken());
        headerComponent.addHeader(response, jwtProvider.getRefreshTokenHeaderName(), jsonWebToken.getRefreshToken().getRefreshToken());
        headerComponent.addContentTypeResponse(response);
        objectMapper.writeValue(response.getWriter(), new CommonResponse<>("Login Success", String.valueOf(member.getId())));
    }

}
