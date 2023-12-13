package school.hei.cloud.service;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.UploadedFile;
import school.hei.cloud.model.exception.FileNameInvalidException;
import school.hei.cloud.repository.FileRepository;

import static school.hei.cloud.service.utils.FileTypeUtils.checkFileType;

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
      return repository.download(fileName);
    }
  }

  public List<UploadedFile> searchFile(String fileName) {
    return repository.searchFile(fileName);
  }
}
