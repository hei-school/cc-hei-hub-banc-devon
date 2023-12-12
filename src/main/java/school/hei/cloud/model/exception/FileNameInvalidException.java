package school.hei.cloud.model.exception;

public class FileNameInvalidException extends ApiException{
  public FileNameInvalidException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }
}
