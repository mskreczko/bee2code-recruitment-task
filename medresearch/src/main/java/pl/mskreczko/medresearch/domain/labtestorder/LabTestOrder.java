package pl.mskreczko.medresearch.domain.labtestorder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.mskreczko.medresearch.domain.patient.Patient;
import pl.mskreczko.medresearch.domain.researchproject.ResearchProject;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "lab_tests_orders")
public class LabTestOrder {

    @Id
    private UUID id;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    private ResearchProject researchProject;

    private LocalDate deadline;
}
