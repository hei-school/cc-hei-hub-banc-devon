package school.hei.cloud.model.exception;

public class BadFolderNameException extends ApiException {
  public BadFolderNameException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }
}
