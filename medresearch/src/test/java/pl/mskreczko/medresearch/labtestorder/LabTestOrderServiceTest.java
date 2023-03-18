package pl.mskreczko.medresearch.labtestorder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mskreczko.medresearch.domain.labtestorder.dto.LabTestOrderCreationDto;
import pl.mskreczko.medresearch.domain.labtestorder.service.LabTestOrderService;
import pl.mskreczko.medresearch.domain.patient.PatientRepository;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LabTestOrderServiceTest {

    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    LabTestOrderService labTestOrderService;

    @Test
    void createNewOrder_returnsExceptionOnPatient() {
        Mockito.when(patientRepository.findById(
                UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"))).thenReturn(Optional.empty());


        Assertions.assertThrows(NoSuchEntityException.class, () ->
                labTestOrderService.addNewOrder(new LabTestOrderCreationDto(
                        "53b1ab15-0663-46fb-b21b-199a7ff20e8e",
                        "53b1ab15-0663-46fb-b21b-199a7ff20e8e",
                        "2023-03-26"
                ))
        );
    }
}
