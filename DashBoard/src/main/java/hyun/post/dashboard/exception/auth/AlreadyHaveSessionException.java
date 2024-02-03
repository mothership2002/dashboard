package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class AlreadyHaveSessionException extends AuthenticationException {

    public AlreadyHaveSessionException(String message) {
        super(message);
    }

}
