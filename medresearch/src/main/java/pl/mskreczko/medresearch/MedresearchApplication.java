package pl.mskreczko.medresearch;

import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mskreczko.medresearch.domain.fileupload.service.FileStorageService;

@SpringBootApplication
public class MedresearchApplication implements CommandLineRunner {

    @Resource
    FileStorageService fileStorageService;

    public static void main(String[] args) {
        SpringApplication.run(MedresearchApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        fileStorageService.init();
    }
}
