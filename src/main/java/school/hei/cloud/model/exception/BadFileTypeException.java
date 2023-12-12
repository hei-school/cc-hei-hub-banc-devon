package school.hei.cloud.model.exception;

public class BadFileTypeException extends ApiException{
  public BadFileTypeException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }
}
