package school.hei.cloud.service.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DestinationFolderConf {
  @Getter
  private final String path;

  public DestinationFolderConf(@Value("${folder.destination.path}") String path) {
    this.path = path;
  }
}
