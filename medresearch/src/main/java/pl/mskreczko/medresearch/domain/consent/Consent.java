package pl.mskreczko.medresearch.domain.consent;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.mskreczko.medresearch.domain.patient.Patient;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProject;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "consents")
public class Consent {

    @Id
    @Column(name = "consent_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private ResearchProject project;
}
