package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class ExpiredAccessTokenException extends AuthenticationException {

    public ExpiredAccessTokenException(String msg) {
        super(msg);
    }
}
