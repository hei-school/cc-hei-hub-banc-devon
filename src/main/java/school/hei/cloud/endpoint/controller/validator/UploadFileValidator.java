package school.hei.cloud.endpoint.controller.validator;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.exception.BadFolderNameException;
import school.hei.cloud.model.exception.InvalidFileException;

@Component
public class UploadFileValidator {
  public void accept(String folderName, MultipartFile file) {
    List<String> validFolderNames = List.of("images", "documents", "pdf", "videos");
    if (!validFolderNames.contains(folderName)) {
      throw new BadFolderNameException("Bad folder name, only " + validFolderNames + " are valid.");
    } else {
      if (file.isEmpty()) {
        throw new InvalidFileException("File is empty. Must provide one.");
      }
    }
  }
}
