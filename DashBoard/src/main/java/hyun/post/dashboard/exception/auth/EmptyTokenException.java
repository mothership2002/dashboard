package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class EmptyTokenException extends AuthenticationException {

    public EmptyTokenException(String msg) {
        super(msg);
    }
}
