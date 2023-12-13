package school.hei.cloud.service;

import static school.hei.cloud.service.utils.FileTypeUtils.checkFileType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.UploadedFile;
import school.hei.cloud.model.exception.FileNameInvalidException;
import school.hei.cloud.model.exception.FileNotFoundException;
import school.hei.cloud.repository.FileRepository;

@Service
@AllArgsConstructor
public class FileService {
  private final FileRepository repository;

  public UploadedFile uploadFile(String folderDestination, MultipartFile fileToUpload) {
    checkFileType(fileToUpload);
    return repository.upload(fileToUpload, folderDestination);
  }

  public Resource downloadFile(String fileName) {
    if (fileName.isEmpty()) {
      throw new FileNameInvalidException("The filename you provided is invalid");
    } else {
      try {
        File file = new File(fileName);
        return new ByteArrayResource(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
      } catch (FileNotFoundException | IOException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
