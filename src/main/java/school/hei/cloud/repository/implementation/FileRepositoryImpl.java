package school.hei.cloud.repository.implementation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.UploadedFile;
import school.hei.cloud.model.exception.BadFileTypeException;
import school.hei.cloud.repository.FileRepository;
import school.hei.cloud.service.utils.DestinationFolderConf;

@Repository
@AllArgsConstructor
public class FileRepositoryImpl implements FileRepository {
  private final DestinationFolderConf destinationFolderConf;

  private static Path getDestinationSubFolder(
      MultipartFile file, String destinationFolder, String subFolder) {
    if (file.getContentType().contains("video/") && subFolder.equals("videos")) {
      return Paths.get(destinationFolder + "/videos", file.getOriginalFilename());
    } else if (file.getContentType().contains("image/") && subFolder.equals("images")) {
      return Paths.get(destinationFolder + "/images", file.getOriginalFilename());
    } else if (file.getContentType().contains("application/pdf") && subFolder.equals("pdf")) {
      return Paths.get(destinationFolder + "/pdf", file.getOriginalFilename());
    } else if ((file.getContentType().contains("application/vnd.openxmlformats-officedocument")
            || file.getContentType().contains("text/csv"))
        && subFolder.equals("documents")) {
      return Paths.get(destinationFolder + "/documents", file.getOriginalFilename());
    } else {
      throw new BadFileTypeException(
          "File with filename "
              + file.getOriginalFilename()
              + " has unsupported type "
              + file.getContentType());
    }
  }

  @Override
  public UploadedFile upload(MultipartFile toUpload, String folderName) {
    if (!toUpload.isEmpty()) {
      Path destinationFolder =
          getDestinationSubFolder(toUpload, destinationFolderConf.getPath(), folderName);
      try {
        Files.write(destinationFolder, toUpload.getBytes());
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
      return new UploadedFile("file:" + destinationFolder, toUpload.getOriginalFilename());
    } else {
      throw new NullPointerException("File is empty.");
    }
  }
}
