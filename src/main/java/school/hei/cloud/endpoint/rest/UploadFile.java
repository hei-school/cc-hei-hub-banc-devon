package school.hei.cloud.endpoint.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class UploadFile {
  private String destinationFolder;
  private MultipartFile file;
}
