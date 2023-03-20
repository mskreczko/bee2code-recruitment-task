package pl.mskreczko.medresearch.fileupload;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mskreczko.medresearch.domain.fileupload.service.FileStorageService;
import pl.mskreczko.medresearch.domain.fileupload.service.LocalFileStorageServiceImpl;

public class LocalFileStorageServiceTest {

    FileStorageService localFileStorageService;

    public LocalFileStorageServiceTest() {
        this.localFileStorageService = new LocalFileStorageServiceImpl();
    }

    @Test
    void validateFilename_returnsTrueOnJpg() {
        Assertions.assertTrue(localFileStorageService.validateFilename("file.jpg"));
    }

    @Test
    void validateFilename_returnsTrueOnPdf() {
        Assertions.assertTrue(localFileStorageService.validateFilename("file.pdf"));
    }

    @Test
    void validateFilename_returnsFalse() {
        Assertions.assertFalse(localFileStorageService.validateFilename("file.png"));
    }
}
