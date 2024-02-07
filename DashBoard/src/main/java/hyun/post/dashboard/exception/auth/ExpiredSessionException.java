package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class ExpiredSessionException extends AuthenticationException {

    public ExpiredSessionException() {
        super("Expired Session");
    }
}
