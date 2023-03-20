package pl.mskreczko.medresearch.domain.fileupload.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.mskreczko.medresearch.domain.fileupload.service.LocalFileStorageServiceImpl;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/uploads")
public class FileUploadController {

    private final LocalFileStorageServiceImpl fileStorageService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        if (!fileStorageService.storeFile(file)) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("{name}")
    public ResponseEntity<?> getFile(@PathVariable("name") String filename) {
        var resourceOptional = fileStorageService.loadFile(filename);
        if (resourceOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                resourceOptional.get().getFilename() + "\"").body(resourceOptional.get());
    }

    @DeleteMapping("{name}")
    public ResponseEntity<?> deleteFile(@PathVariable("name") String filename) {
        try {
            fileStorageService.deleteFile(filename);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }
}
