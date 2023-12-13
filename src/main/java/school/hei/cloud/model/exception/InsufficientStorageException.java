package school.hei.cloud.model.exception;

public class InsufficientStorageException extends ApiException {
  public InsufficientStorageException(String message) {
    super(ExceptionType.SERVER_EXCEPTION, message);
  }
}
