package pl.mskreczko.medresearch.domain.researchproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectCreationDto;
import pl.mskreczko.medresearch.domain.researchproject.dto.ProjectDetailsDto;
import pl.mskreczko.medresearch.domain.researchproject.service.ResearchProjectService;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;
import pl.mskreczko.medresearch.exceptions.ProjectAlreadyExistsException;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/projects")
public class ResearchProjectController {

    private final ResearchProjectService researchProjectService;

    @GetMapping
    public ResponseEntity<?> getAllResearchProjects() {
        return ResponseEntity.ok(researchProjectService.getAllResearchProjects());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getResearchProjectById(@PathVariable("id") UUID id) {
        try {
            return ResponseEntity.ok(researchProjectService.getResearchProjectById(id));
        } catch (NoSuchEntityException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createNewProject(@RequestBody ProjectCreationDto projectCreationDto) {
        try {
            researchProjectService.createNewProject(projectCreationDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (ProjectAlreadyExistsException exception) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(409));
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateProjectDetails(@PathVariable("id") UUID id,
                                                  @RequestBody ProjectDetailsDto projectDetailsDto) {
        try {
            researchProjectService.updateProjectDetails(id, projectDetailsDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(200));
        } catch (NoSuchEntityException exception) {
            return new ResponseEntity<>(HttpStatusCode.valueOf(404));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProject(@PathVariable("id") UUID id) {
        researchProjectService.deleteProject(id);
        return new ResponseEntity<>(HttpStatusCode.valueOf(204));
    }
}
