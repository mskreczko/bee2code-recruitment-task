package pl.mskreczko.medresearch.domain.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.medresearch.domain.patient.Patient;
import pl.mskreczko.medresearch.domain.patient.PatientRepository;
import pl.mskreczko.medresearch.domain.patient.dto.PatientCreationDto;
import pl.mskreczko.medresearch.domain.patient.dto.PatientDetailsDto;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;
import pl.mskreczko.medresearch.exceptions.PatientAlreadyExistsException;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatientById(UUID id) throws NoSuchEntityException {
        return patientRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }

    public void createPatient(PatientCreationDto patientCreationDto) throws PatientAlreadyExistsException {
        patientRepository.findByEmail(patientCreationDto.email()).ifPresent((patient) -> {
            throw new PatientAlreadyExistsException();
        });
        var patient = new Patient();

        patient.setId(UUID.randomUUID());
        patient.setName(patientCreationDto.name());
        patient.setEmail(patientCreationDto.email());
        patient.setPhoneNumber(patientCreationDto.phoneNumber());

        patientRepository.save(patient);
    }

    public void removePatient(UUID id) {
        patientRepository.deleteById(id);
    }

    @Transactional
    public void updatePatientDetails(UUID id, PatientDetailsDto patientDetailsDto) throws NoSuchEntityException {
        var patient = patientRepository.findById(id).orElseThrow(NoSuchEntityException::new);
        if (!patientDetailsDto.name().isEmpty()) {
            patient.setName(patientDetailsDto.name());
        }
        if (!patientDetailsDto.email().isEmpty()) {
            patient.setEmail(patientDetailsDto.email());
        }
        if (!patientDetailsDto.phoneNumber().isEmpty()) {
            patient.setPhoneNumber(patientDetailsDto.phoneNumber());
        }
    }
}
