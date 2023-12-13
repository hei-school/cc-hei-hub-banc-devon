package school.hei.cloud.model.exception;

public class FileNotFoundException extends ApiException {
  public FileNotFoundException(String message) {
    super(ExceptionType.CLIENT_EXCEPTION, message);
  }
}
