package school.hei.cloud.service;

import static school.hei.cloud.service.utils.FileTypeUtils.checkFileType;

import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
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

  public FileSystemResource downloadFile(String fileName) {
    if (fileName.isEmpty()) {
      throw new FileNameInvalidException("The filename you provided is invalid");
    } else {
      try {
        return new FileSystemResource(fileName);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
