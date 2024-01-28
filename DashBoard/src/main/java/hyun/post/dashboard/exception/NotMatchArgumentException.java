package hyun.post.dashboard.exception;

public class NotMatchArgumentException extends RuntimeException {

    public NotMatchArgumentException() {
    }

    public NotMatchArgumentException(Throwable cause) {
        super("Argument Match Fail", cause);
    }
}
