package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class TryDuplicateLoginException extends AuthenticationException {

    public TryDuplicateLoginException(String duplicationLogin) {
        super(duplicationLogin);
    }
}
