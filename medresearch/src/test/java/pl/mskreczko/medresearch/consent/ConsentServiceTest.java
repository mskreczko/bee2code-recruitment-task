package pl.mskreczko.medresearch.consent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mskreczko.medresearch.domain.consent.service.ConsentService;
import pl.mskreczko.medresearch.domain.patient.PatientRepository;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ConsentServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    ConsentService consentService;

    @Test
    void createNewConsent_throwsOnPatient() {
       Mockito.when(patientRepository.findById(UUID.fromString("8cb13b13-6c1a-4ef9-8e61-1924d8906b3e")))
               .thenReturn(Optional.empty());

       Assertions.assertThrows(NoSuchEntityException.class, () ->
               consentService.createNewConsent(UUID.fromString("8cb13b13-6c1a-4ef9-8e61-1924d8906b3e"),
                       UUID.fromString("c147342e-ae02-402f-a599-505da2756a80")));
    }
}
