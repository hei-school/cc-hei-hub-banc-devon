package school.hei.cloud.repository;

import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.endpoint.rest.UploadFile;
import school.hei.cloud.model.UploadedFile;

public interface FileRepository {
  UploadedFile upload(MultipartFile toUpload);
}
