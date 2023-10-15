package hyun.post.dashboard.security.filter;

import hyun.post.dashboard.security.handler.CustomFailureHandler;
import hyun.post.dashboard.security.handler.CustomSuccessHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class AuthenticationLoginFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationLoginFilter(AuthenticationManager authenticationManager,
                                     CustomSuccessHandler successHandler,
                                     CustomFailureHandler failureHandler) {
        
        super(authenticationManager);
        super.setAuthenticationSuccessHandler(successHandler);
        super.setAuthenticationFailureHandler(failureHandler);
    }
}
