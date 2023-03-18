package pl.mskreczko.medresearch.domain.labtestorder.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mskreczko.medresearch.domain.labtestorder.LabTestOrder;
import pl.mskreczko.medresearch.domain.labtestorder.LabTestOrderRepository;
import pl.mskreczko.medresearch.domain.labtestorder.dto.LabTestOrderCreationDto;
import pl.mskreczko.medresearch.domain.patient.PatientRepository;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProjectRepository;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class LabTestOrderService {

    private final LabTestOrderRepository labTestOrderRepository;
    private final ResearchProjectRepository researchProjectRepository;
    private final PatientRepository patientRepository;

    public void addNewOrder(LabTestOrderCreationDto labTestOrderCreationDto) throws NoSuchEntityException {
        var patient = patientRepository.findById(UUID.fromString(labTestOrderCreationDto.patientId()))
                .orElseThrow(NoSuchEntityException::new);
        var researchProject = researchProjectRepository.findById(UUID.fromString(labTestOrderCreationDto.researchProjectId()))
                .orElseThrow(NoSuchEntityException::new);

        var order = new LabTestOrder();
        order.setId(UUID.randomUUID());
        order.setPatient(patient);
        order.setResearchProject(researchProject);
        order.setDeadline(LocalDate.parse(labTestOrderCreationDto.deadline()));

        labTestOrderRepository.save(order);
    }
}
