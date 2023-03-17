package pl.mskreczko.medresearch.domain.consent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mskreczko.medresearch.domain.consent.Consent;
import pl.mskreczko.medresearch.domain.consent.ConsentRepository;
import pl.mskreczko.medresearch.domain.patient.PatientRepository;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProjectRepository;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ConsentService {

    private final ConsentRepository consentRepository;
    private final PatientRepository patientRepository;
    private final ResearchProjectRepository researchProjectRepository;

    @Transactional
    public void createNewConsent(UUID patientId, UUID projectId) {
        var patient = patientRepository.findById(patientId).orElseThrow(NoSuchEntityException::new);
        var project = researchProjectRepository.findById(projectId).orElseThrow(NoSuchEntityException::new);

        var consent = new Consent();
        consent.setId(UUID.randomUUID());
        consent.setPatient(patient);
        consent.setProject(project);

        consentRepository.save(consent);
    }

    public void deleteConsent(UUID id) {
        consentRepository.deleteById(id);
    }
}
