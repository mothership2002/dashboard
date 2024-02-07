package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class ExpiredRefreshTokenException extends AuthenticationException {

    public ExpiredRefreshTokenException() {
        super("Expired refresh token");
    }
}
