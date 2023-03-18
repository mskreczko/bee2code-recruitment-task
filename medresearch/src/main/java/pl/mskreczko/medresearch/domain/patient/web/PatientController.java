package pl.mskreczko.medresearch.domain.patient.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.medresearch.domain.patient.dto.PatientCreationDto;
import pl.mskreczko.medresearch.domain.patient.dto.PatientDetailsDto;
import pl.mskreczko.medresearch.domain.patient.service.PatientService;
import pl.mskreczko.medresearch.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<?> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPatientById(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(patientService.getPatientById(id));
        } catch (NoSuchEntityException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createNewPatient(@RequestBody PatientCreationDto patientCreationDto) {
        try {
            patientService.createPatient(patientCreationDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (EntityAlreadyExistsException exception) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updatePatientDetails(@PathVariable("id") UUID id,
                                                  @RequestBody PatientDetailsDto patientDetailsDto) {
        try {
            patientService.updatePatientDetails(id, patientDetailsDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") UUID patientId) {
        patientService.removePatient(patientId);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }
}
