package school.hei.cloud.model.exception;

public class InvalidFileException extends ApiException{
  public InvalidFileException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }
}
