package hyun.post.dashboard.exception;

public class TryDuplicateLoginException extends RuntimeException {

    public TryDuplicateLoginException(String duplicationLogin) {
        super(duplicationLogin);
    }
}
