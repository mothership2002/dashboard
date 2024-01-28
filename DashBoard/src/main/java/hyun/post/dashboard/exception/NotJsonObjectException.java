package hyun.post.dashboard.exception;

public class NotJsonObjectException extends RuntimeException {
    public NotJsonObjectException(Throwable cause) {
        super("Not Json Data", cause);
    }
}
