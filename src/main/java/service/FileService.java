package service;

import org.springframework.stereotype.Service;
import school.hei.cloud.endpoint.rest.UploadFile;
import school.hei.cloud.model.UploadedFile;

import static service.utils.FileTypeUtils.checkFileType;

@Service
public class FileService {
  public UploadedFile uploadFile(UploadFile toUpload){
    checkFileType(toUpload.getFile());
    return null;
  }
}
