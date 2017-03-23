package penalties.exceptions;

public class ShotException extends RuntimeException {
    public ShotException() {
    }

    public ShotException(String message) {
        super(message);
    }

    public ShotException(String message, Throwable cause) {
        super(message, cause);
    }

    public ShotException(Throwable cause) {
        super(cause);
    }

    public ShotException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
