package school.hei.cloud.endpoint.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import school.hei.cloud.endpoint.rest.exception.Exception;
import school.hei.cloud.model.exception.ApiException;
import school.hei.cloud.model.exception.BadFileTypeException;
import school.hei.cloud.model.exception.BadFolderNameException;
import school.hei.cloud.model.exception.FileNameInvalidException;
import school.hei.cloud.model.exception.FileNotFoundException;
import school.hei.cloud.model.exception.InvalidFileException;

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {
  @ExceptionHandler(value = {BadFileTypeException.class})
  ResponseEntity<Exception> handleBadFileTypeException(BadFileTypeException e) {
    log.info("Bad file type", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {InvalidFileException.class})
  ResponseEntity<Exception> handleInvalidFileException(InvalidFileException e) {
    log.info("Bad folder name", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {FileNotFoundException.class})
  ResponseEntity<Exception> handleFileNotFoundException(FileNotFoundException e) {
    log.info("Bad file request", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {FileNameInvalidException.class})
  ResponseEntity<Exception> handleFileInvalidNameException(FileNameInvalidException e) {
    log.info("Bad filename", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {BadFolderNameException.class})
  ResponseEntity<Exception> BadFolderNameException(BadFolderNameException e) {
    log.info("Bad file type", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  private Exception toRest(ApiException e, HttpStatus status) {
    return new Exception(status.toString(), e.getMessage());
  }
}
