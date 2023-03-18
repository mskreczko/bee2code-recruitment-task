package pl.mskreczko.medresearch.patient;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mskreczko.medresearch.domain.patient.Patient;
import pl.mskreczko.medresearch.domain.patient.dto.PatientCreationDto;
import pl.mskreczko.medresearch.domain.patient.dto.PatientDetailsDto;
import pl.mskreczko.medresearch.domain.patient.service.PatientService;
import pl.mskreczko.medresearch.domain.patient.web.PatientController;
import pl.mskreczko.medresearch.exceptions.EntityAlreadyExistsException;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {

    @MockBean
    PatientService patientService;

    @Autowired
    MockMvc mvc;

    @Test
    void getPatientById_returnsOk() throws Exception {
        Mockito.when(patientService.getPatientById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenReturn(new Patient());

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/53b1ab15-0663-46fb-b21b-199a7ff20e8e"))
                .andExpect(status().isOk());
    }

    @Test
    void getPatientById_returnsNotFound() throws Exception {
        Mockito.when(patientService.getPatientById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenThrow(NoSuchEntityException.class);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/patients/53b1ab15-0663-46fb-b21b-199a7ff20e8e"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewPatient_returnsCreated() throws Exception {
        Mockito.doNothing().when(patientService).createPatient(
                new PatientCreationDto("John Doe", "test@test.com", "123456789")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "John Doe",
                    "email": "test@test.com",
                    "phoneNumber": "123456789"
                }
                """))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewPatient_returnsConflict() throws Exception {
        Mockito.doThrow(EntityAlreadyExistsException.class).when(patientService).createPatient(
                new PatientCreationDto("John Doe", "test@test.com", "123456789")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "John Doe",
                    "email": "test@test.com",
                    "phoneNumber": "123456789"
                }
                """))
                .andExpect(status().isConflict());
    }

    @Test
    void updatePatientDetails_returnsOk() throws Exception {
        Mockito.doNothing().when(patientService).updatePatientDetails(
                UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"),
                new PatientDetailsDto("", "test@gmail.com", ""));

        mvc.perform(MockMvcRequestBuilders
                .patch("/api/v1/patients/53b1ab15-0663-46fb-b21b-199a7ff20e8e")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "",
                    "email": "test@gmail.com",
                    "phoneNumber": ""
                }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void updatePatientDetails_returnsNotFound() throws Exception {
        Mockito.doThrow(NoSuchEntityException.class).when(patientService).updatePatientDetails(
                UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"),
                new PatientDetailsDto("", "test@gmail.com", ""));

        mvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/patients/53b1ab15-0663-46fb-b21b-199a7ff20e8e")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "",
                    "email": "test@gmail.com",
                    "phoneNumber": ""
                }
                """))
                .andExpect(status().isNotFound());
    }
}
