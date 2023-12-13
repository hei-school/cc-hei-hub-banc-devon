package school.hei.cloud.repository.implementation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;
import school.hei.cloud.model.UploadedFile;
import school.hei.cloud.model.exception.BadFileTypeException;
import school.hei.cloud.model.exception.FileNotFoundException;
import school.hei.cloud.repository.FileRepository;
import school.hei.cloud.service.utils.DestinationFolderConf;

@Repository
@AllArgsConstructor
@Slf4j
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

  private static String getSubFolderAccordingToFileType(String fileName) {
    if (fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
      return "/images/";
    } else if (fileName.endsWith(".pdf")) {
      return "/pdf/";
    } else if (fileName.endsWith(".docx") || fileName.endsWith(".csv") ||
        fileName.endsWith(".xlsx")) {
      return "/documents/";
    } else if (fileName.endsWith(".mp4") || fileName.endsWith(".avi") ||
        fileName.endsWith(".mov") || fileName.endsWith(".mkv")) {
      return "/videos/";
    } else {
      throw new BadFileTypeException("This file type is not supported.");
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

  @Override
  public FileSystemResource download(String fileName) {
    try {
      String filePath = ResourceUtils.getFile(
              destinationFolderConf.getPath() + getSubFolderAccordingToFileType(fileName) + fileName)
          .getAbsolutePath();
      log.info("File Path: {}", filePath);
      if (new File(filePath).exists()) {
        return new FileSystemResource(filePath);
      } else {
        throw new FileNotFoundException("The file " + fileName + " is not found.");
      }
    } catch (IOException e) {
      throw new RuntimeException("The file " + fileName + " is not found.");
    }
  }

  private List<UploadedFile> searchFile(File directory, String toSearch,
                                        List<UploadedFile> results) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          searchFile(file, toSearch, results);
        } else if (file.getName().contains(toSearch)) {
          results.add(UploadedFile.builder()
              .filename(file.getName())
              .folder(file.getParent())
              .build());
          log.info("Results: {}", results);
        }
      }
    }
    return results;
  }

  @Override
  public List<UploadedFile> searchFile(String namePattern) {
    File directory = new File(destinationFolderConf.getPath());
    List<UploadedFile> actual = new ArrayList<>();
    List<UploadedFile> results = searchFile(directory, namePattern, actual);
    if (results.isEmpty()) {
      throw new FileNotFoundException("File with name " + namePattern + " may not exists.");
    } else {
      return results;
    }
  }
}
