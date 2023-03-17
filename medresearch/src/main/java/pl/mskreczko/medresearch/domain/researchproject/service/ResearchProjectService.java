package pl.mskreczko.medresearch.domain.researchproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProject;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProjectRepository;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectCreationDto;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectDetailsDto;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;
import pl.mskreczko.medresearch.exceptions.ProjectAlreadyExistsException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ResearchProjectService {

    private final ResearchProjectRepository researchProjectRepository;

    public List<ResearchProject> getAllResearchProjects() {
        return researchProjectRepository.findAll();
    }

    public ResearchProject getResearchProjectById(UUID id) throws NoSuchEntityException {
        return researchProjectRepository.findById(id).orElseThrow(NoSuchEntityException::new);
    }

    public void createNewProject(ProjectCreationDto projectCreationDto) throws ProjectAlreadyExistsException {
        researchProjectRepository.findByName(projectCreationDto.name()).ifPresent((project) -> {
            throw new ProjectAlreadyExistsException();
        });

        var researchProject = new ResearchProject();
        researchProject.setId(UUID.randomUUID());
        researchProject.setName(projectCreationDto.name());
        researchProject.setDescription(projectCreationDto.description());
        researchProject.setDateOfCreation(LocalDate.now());

        researchProjectRepository.save(researchProject);
    }

    @Transactional
    public void updateProjectDetails(UUID id, ProjectDetailsDto projectDetailsDto) throws NoSuchEntityException {
        var project = researchProjectRepository.findById(id).orElseThrow(NoSuchEntityException::new);
        if (!projectDetailsDto.name().isEmpty()) {
            project.setName(projectDetailsDto.name());
        }
        if (!projectDetailsDto.description().isEmpty()) {
            project.setDescription(projectDetailsDto.description());
        }
    }

    public void deleteProject(UUID id) {
        researchProjectRepository.deleteById(id);
    }
}
