package school.hei.cloud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class UploadedFile {
  private String id;
  private String folder;
  private String filename;
}
