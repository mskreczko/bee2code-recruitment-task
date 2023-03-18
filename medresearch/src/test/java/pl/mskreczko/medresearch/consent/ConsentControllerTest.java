package pl.mskreczko.medresearch.consent;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mskreczko.medresearch.domain.consent.service.ConsentService;
import pl.mskreczko.medresearch.domain.consent.web.ConsentController;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ConsentController.class)
public class ConsentControllerTest {

    @MockBean
    ConsentService consentService;

    @Autowired
    MockMvc mvc;

    @Test
    void addNewConsent_returnsCreated() throws Exception {
        Mockito.doNothing().when(consentService).createNewConsent(UUID.fromString("8cb13b13-6c1a-4ef9-8e61-1924d8906b3e"),
                UUID.fromString("c147342e-ae02-402f-a599-505da2756a80"));

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/consents?patient_id=8cb13b13-6c1a-4ef9-8e61-1924d8906b3e&project_id=" +
                        "c147342e-ae02-402f-a599-505da2756a80"))
                .andExpect(status().isCreated());
    }

    @Test
    void addNewConsent_ReturnsNotFound() throws Exception {
        Mockito.doThrow(NoSuchEntityException.class).when(consentService).createNewConsent(
                UUID.fromString("8cb13b13-6c1a-4ef9-8e61-1924d8906b3e"),
                UUID.fromString("c147342e-ae02-402f-a599-505da2756a80"));

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/consents?patient_id=8cb13b13-6c1a-4ef9-8e61-1924d8906b3e&project_id=" +
                                "c147342e-ae02-402f-a599-505da2756a80"))
                .andExpect(status().isNotFound());
    }
}
