package school.hei.cloud.repository;

import java.util.List;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.UploadedFile;

public interface FileRepository {
  UploadedFile upload(MultipartFile toUpload, String folderName);

  FileSystemResource download(String fileName);

  List<UploadedFile> searchFile(String namePattern);
}
