package pl.mskreczko.medresearch.domain.labtestorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LabTestOrderRepository extends JpaRepository<LabTestOrder, UUID> {
}
