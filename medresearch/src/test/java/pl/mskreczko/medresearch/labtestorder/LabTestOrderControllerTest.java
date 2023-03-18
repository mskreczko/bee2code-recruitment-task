package pl.mskreczko.medresearch.labtestorder;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mskreczko.medresearch.domain.labtestorder.dto.LabTestOrderCreationDto;
import pl.mskreczko.medresearch.domain.labtestorder.service.LabTestOrderService;
import pl.mskreczko.medresearch.domain.labtestorder.web.LabTestOrderController;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LabTestOrderController.class)
public class LabTestOrderControllerTest {

    @MockBean
    LabTestOrderService labTestOrderService;

    @Autowired
    MockMvc mvc;

    @Test
    void createNewOrder_returnsCreated() throws Exception {
        Mockito.doNothing().when(labTestOrderService).addNewOrder(new LabTestOrderCreationDto(
                "8cb13b13-6c1a-4ef9-8e61-1924d8906b3e",
                "c147342e-ae02-402f-a599-505da2756a80",
                "2023-03-26"
        ));

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/lab-orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "patientId": "8cb13b13-6c1a-4ef9-8e61-1924d8906b3e",
                    "researchProjectId": "c147342e-ae02-402f-a599-505da2756a80",
                    "deadline": "2023-03-26"
                }
                """))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewOrder_returnsNotFound() throws Exception {
        Mockito.doThrow(NoSuchEntityException.class).when(labTestOrderService).addNewOrder(new LabTestOrderCreationDto(
                "8cb13b13-6c1a-4ef9-8e61-1924d8906b3e",
                "c147342e-ae02-402f-a599-505da2756a80",
                "2023-03-26"
        ));

        mvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/lab-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "patientId": "8cb13b13-6c1a-4ef9-8e61-1924d8906b3e",
                    "researchProjectId": "c147342e-ae02-402f-a599-505da2756a80",
                    "deadline": "2023-03-26"
                }
                """))
                .andExpect(status().isNotFound());
    }
}
