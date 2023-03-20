package pl.mskreczko.medresearch.domain.fileupload.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileStorageService {
    void init();
    boolean validateFilename(String filename);
    boolean storeFile(MultipartFile file);
    Optional<Resource> loadFile(String filename);

    void deleteFile(String filename);
}
