package school.hei.cloud.endpoint.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.cloud.endpoint.rest.UploadFile;
import service.FileService;

@RestController
@AllArgsConstructor
public class FileController {
  @PostMapping("/file")
  public ResponseEntity<byte[]> uploadFile(@RequestBody UploadFile toUpload){
    return null;
  }
}
