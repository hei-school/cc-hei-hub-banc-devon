package service.utils;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.exception.BadFileTypeException;

@Component
public class FileTypeUtils {
  public static void checkFileType(MultipartFile file) {
    String contentType = file.getContentType();
    List<String> supportedFileTypes = List.of("application/pdf", "image/png", "image/jpeg",
        "video/mp4", "video/quicktime", "video/x-msvideo", "video/x-matroska",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "text/csv");
    if (!supportedFileTypes.contains(contentType)) {
      throw new BadFileTypeException(
          "File with filename " + file.getOriginalFilename() + " has unsupported type " +
              contentType);
    }
  }
}
