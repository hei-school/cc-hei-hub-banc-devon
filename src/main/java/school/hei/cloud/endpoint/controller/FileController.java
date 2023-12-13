package school.hei.cloud.endpoint.controller;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.endpoint.controller.validator.UploadFileValidator;
import school.hei.cloud.model.UploadedFile;
import school.hei.cloud.service.FileService;

@RestController
@AllArgsConstructor
public class FileController {
  private final FileService service;
  private final UploadFileValidator validator;

  @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public UploadedFile uploadFile(
      @RequestParam(name = "folderDestination") String folderDestination,
      @RequestBody MultipartFile file) {
    validator.accept(folderDestination, file);
    return service.uploadFile(folderDestination, file);
  }

  @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public ResponseEntity<Resource> downloadFile(@RequestParam("file_name") String fileName) {
    Resource file = service.downloadFile(fileName);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
    return ResponseEntity.ok().headers(headers).body(file);
  }
}
