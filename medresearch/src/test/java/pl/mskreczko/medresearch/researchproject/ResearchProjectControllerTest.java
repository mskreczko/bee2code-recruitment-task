package pl.mskreczko.medresearch.researchproject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProject;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectCreationDto;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectDetailsDto;
import pl.mskreczko.medresearch.domain.researchproject.service.ResearchProjectService;
import pl.mskreczko.medresearch.domain.researchproject.web.ResearchProjectController;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;
import pl.mskreczko.medresearch.exceptions.ProjectAlreadyExistsException;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ResearchProjectController.class)
public class ResearchProjectControllerTest {

    @MockBean
    ResearchProjectService researchProjectService;

    @Autowired
    MockMvc mvc;

    @Test
    void getResearchProjectById_returnsOk() throws Exception {
        Mockito.when(researchProjectService.getResearchProjectById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenReturn(new ResearchProject());

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/projects/53b1ab15-0663-46fb-b21b-199a7ff20e8e"))
                .andExpect(status().isOk());
    }

    @Test
    void getResearchProjectById_returnsNotFound() throws Exception {
        Mockito.when(researchProjectService.getResearchProjectById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenThrow(NoSuchEntityException.class);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/projects/53b1ab15-0663-46fb-b21b-199a7ff20e8e"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createNewProject_returnsCreated() throws Exception {
        Mockito.doNothing().when(researchProjectService).createNewProject(
                new ProjectCreationDto("sample project", "test")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "sample project",
                    "description": "test"
                }
                """))
                .andExpect(status().isCreated());
    }

    @Test
    void createNewProject_returnsConflict() throws Exception {
        Mockito.doThrow(ProjectAlreadyExistsException.class).when(researchProjectService).createNewProject(
                new ProjectCreationDto("sample project", "test")
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/projects")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "sample project",
                    "description": "test"
                 }
                 """))
                 .andExpect(status().isConflict());
    }

    @Test
    void updateProjectDetails_returnsOk() throws Exception {
        Mockito.doNothing().when(researchProjectService).updateProjectDetails(
                UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"),
                new ProjectDetailsDto("test2", ""));

        mvc.perform(MockMvcRequestBuilders
                .patch("/api/v1/projects/53b1ab15-0663-46fb-b21b-199a7ff20e8e")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                {
                    "name": "test2",
                    "description": ""
                }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void updateProjectDetails_returnsNotFound() throws Exception {
        Mockito.doThrow(NoSuchEntityException.class).when(researchProjectService).updateProjectDetails(
                UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"),
                new ProjectDetailsDto("test2", ""));

        mvc.perform(MockMvcRequestBuilders
                        .patch("/api/v1/projects/53b1ab15-0663-46fb-b21b-199a7ff20e8e")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "test2",
                    "description": ""
                }
                """))
                .andExpect(status().isNotFound());
    }
}
