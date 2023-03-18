package pl.mskreczko.medresearch.patient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mskreczko.medresearch.domain.patient.Patient;
import pl.mskreczko.medresearch.domain.patient.PatientRepository;
import pl.mskreczko.medresearch.domain.patient.dto.PatientCreationDto;
import pl.mskreczko.medresearch.domain.patient.service.PatientService;
import pl.mskreczko.medresearch.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    PatientService patientService;

    @Test
    void getPatientById_returnsPatient() {
        var mockPatient = new Patient();
        mockPatient.setId(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"));
        Mockito.when(patientRepository.findById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenReturn(Optional.of(mockPatient));

        final var patient = patientService.getPatientById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"));

        Assertions.assertEquals(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"), patient.getId());
    }

    @Test
    void getPatientById_throwsException() {
        Mockito.when(patientRepository.findById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchEntityException.class, () ->
                patientService.getPatientById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")));
    }

    @Test
    void createPatient_throwsException() {
        Mockito.when(patientRepository.findByEmail("test@test.com")).thenReturn(Optional.of(new Patient()));

        Assertions.assertThrows(EntityAlreadyExistsException.class, () ->
                patientService.createPatient(
                        new PatientCreationDto("John Doe", "test@test.com", "123456789")));
    }
}
