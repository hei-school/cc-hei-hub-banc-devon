package school.hei.cloud.endpoint.controller;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.endpoint.rest.UploadFile;
import school.hei.cloud.model.UploadedFile;
import school.hei.cloud.service.FileService;

@RestController
@AllArgsConstructor
public class FileController {
  private final FileService service;
  @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public UploadedFile uploadFile(
      @RequestParam(name = "folderDestination") String folderDestination,
      @RequestBody MultipartFile toUpload) {
    return service.uploadFile(folderDestination, toUpload);
  }
}
