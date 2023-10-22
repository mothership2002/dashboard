package hyun.post.dashboard.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hyun.post.dashboard.model.dto.MemberLoginDto;
import hyun.post.dashboard.security.encrypt.EncryptionProvider;
import hyun.post.dashboard.security.handler.CustomFailureHandler;
import hyun.post.dashboard.security.handler.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;


public class AuthenticationLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final EncryptionProvider encryptionProvider;

    public AuthenticationLoginFilter(AuthenticationManager authenticationManager,
                                     CustomSuccessHandler successHandler,
                                     CustomFailureHandler failureHandler,
                                     ObjectMapper objectMapper,
                                     EncryptionProvider encryptionProvider) {
        
        super(authenticationManager);
        super.setAuthenticationSuccessHandler(successHandler);
        super.setAuthenticationFailureHandler(failureHandler);
        this.encryptionProvider = encryptionProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp) throws AuthenticationException {
        MemberLoginDto member = null;
        try {
            member = objectMapper.readValue(req.getInputStream(), MemberLoginDto.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 암호화 풀 이유가 없지 않나?
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(member.getAccount(), member.getPassword());
        return getAuthenticationManager().authenticate(token);
    }
}
