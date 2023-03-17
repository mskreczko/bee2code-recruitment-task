package pl.mskreczko.medresearch.domain.consent;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private ResearchProject project;

    @Override
    public String toString() {
        return "Consent{" +
                "id=" + id +
                ", patient=" + patient +
                ", project=" + project +
                '}';
    }
}
