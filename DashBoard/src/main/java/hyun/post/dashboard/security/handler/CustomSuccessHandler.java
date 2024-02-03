package hyun.post.dashboard.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.model.common.CommonResponse;
import hyun.post.dashboard.model.dto.JwtDto;
import hyun.post.dashboard.model.entity.Member;
import hyun.post.dashboard.security.jwt.JsonWebToken;
import hyun.post.dashboard.security.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final CommonRespHeaderComponent headerComponent;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JsonWebToken jsonWebToken = jwtProvider.saveToken(member);
        String accessToken = jsonWebToken.getAccessToken().getAccessToken();
        String refreshToken = jsonWebToken.getRefreshToken().getRefreshToken();
        headerComponent.addHeader(response, jwtProvider.getAccessTokenHeaderName(), accessToken);
        headerComponent.addHeader(response, jwtProvider.getRefreshTokenHeaderName(), refreshToken);
        headerComponent.addContentTypeResponse(response);
        objectMapper.writeValue(response.getWriter(),
                new CommonResponse<>("Login Success", new JwtDto(accessToken, refreshToken)));
    }

}
