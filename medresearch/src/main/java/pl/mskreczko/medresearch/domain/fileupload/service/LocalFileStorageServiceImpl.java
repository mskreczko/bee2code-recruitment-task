package pl.mskreczko.medresearch.domain.fileupload.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
public class LocalFileStorageServiceImpl implements FileStorageService {
    private final Path ROOT_DIR = Paths.get("uploads");

    @Override
    public void init() {
        if (!Files.exists(ROOT_DIR)) {
            try {
                Files.createDirectory(ROOT_DIR);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean validateFilename(String filename) {
        return filename.endsWith(".jpg") || filename.endsWith(".pdf");
    }

    @Override
    public boolean storeFile(MultipartFile file) {
        if (!validateFilename(Objects.requireNonNull(file.getOriginalFilename()))) {
            return false;
        }

        try {
            Files.copy(file.getInputStream(), ROOT_DIR.resolve(Objects.requireNonNull(file.getOriginalFilename())),
                    StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException ioException) {
            return false;
        }
    }

    @Override
    public Optional<Resource> loadFile(String filename) {
        try {
            var file = ROOT_DIR.resolve(filename);
            var resource = new UrlResource(file.toUri());
            if (resource.isReadable() || resource.exists()) {
                return Optional.of(resource);
            }
        } catch (MalformedURLException e) {
            return Optional.empty();
        }
        return Optional.empty();
    }
}
