package pl.mskreczko.medresearch.researchproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProject;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProjectRepository;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectCreationDto;
import pl.mskreczko.medresearch.domain.researchproject.service.ResearchProjectService;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;
import pl.mskreczko.medresearch.exceptions.ProjectAlreadyExistsException;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class ResearchProjectServiceTest {

    @Mock
    ResearchProjectRepository researchProjectRepository;

    @InjectMocks
    ResearchProjectService researchProjectService;

    @Test
    void getResearchProjectById_returnsResearchProject() {
        var mockResearchProject = new ResearchProject();
        mockResearchProject.setId(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"));
        Mockito.when(researchProjectRepository.findById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenReturn(Optional.of(mockResearchProject));

        final var researchProject = researchProjectService.getResearchProjectById(
                UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"));

        Assertions.assertEquals(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e"), researchProject.getId());
    }

    @Test
    void getResearchProjectById_throwsException() {
        Mockito.when(researchProjectRepository.findById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchEntityException.class, () ->
                researchProjectService.getResearchProjectById(UUID.fromString("53b1ab15-0663-46fb-b21b-199a7ff20e8e")));
    }

    @Test
    void createNewProject_throwsException() {
        Mockito.when(researchProjectRepository.findByName("sample project")).thenReturn(Optional.of(new ResearchProject()));

        Assertions.assertThrows(ProjectAlreadyExistsException.class, () ->
                researchProjectService.createNewProject(
                        new ProjectCreationDto("sample project", "test")));
    }
}
