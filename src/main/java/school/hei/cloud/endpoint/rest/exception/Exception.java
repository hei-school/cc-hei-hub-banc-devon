package school.hei.cloud.endpoint.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Exception {
  private String type;
  private String message;
}
