package hyun.post.dashboard.exception;

public class DtoConvertXssException extends RuntimeException {

    public DtoConvertXssException(Throwable cause) {
        super("Exception Throws in Dto Converting", cause);
    }

}
