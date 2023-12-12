package school.hei.cloud.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import school.hei.cloud.endpoint.rest.UploadFile;
import school.hei.cloud.model.UploadedFile;

@Repository
public interface FileRepository {
    List<UploadedFile> upload(UploadFile toUpload);
}
