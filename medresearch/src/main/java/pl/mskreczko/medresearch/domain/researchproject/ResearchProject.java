package pl.mskreczko.medresearch.domain.researchproject;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.mskreczko.medresearch.domain.consent.Consent;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "research_projects")
public class ResearchProject {

    @Id
    @Column(name = "project_id")
    private UUID id;

    private String name;

    private String description;

    private LocalDate dateOfCreation;

    @OneToMany(mappedBy = "project")
    private Set<Consent> consents;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResearchProject that = (ResearchProject) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
