package pl.mskreczko.medresearch.domain.consent.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.medresearch.domain.consent.service.ConsentService;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/consents")
public class ConsentController {

    private final ConsentService consentService;

    @PostMapping
    public ResponseEntity<?> addNewConsent(@RequestParam("patient_id")UUID patientId,
                                           @RequestParam("project_id")UUID projectId) {
        try {
            consentService.createNewConsent(patientId, projectId);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("{consent_id}")
    public ResponseEntity<?> removeConsent(@PathVariable("consent_id") UUID id) {
        consentService.deleteConsent(id);
        return ResponseEntity.noContent().build();
    }
}
