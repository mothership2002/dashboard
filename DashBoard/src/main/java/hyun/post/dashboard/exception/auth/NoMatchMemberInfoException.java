package hyun.post.dashboard.exception.auth;

import org.springframework.security.core.AuthenticationException;

public class NoMatchMemberInfoException extends AuthenticationException {
    public NoMatchMemberInfoException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public NoMatchMemberInfoException(String msg) {
        super(msg);
    }
}
