package pl.mskreczko.medresearch.domain.labtestorder.dto;

public record LabTestOrderCreationDto(String patientId, String researchProjectId, String deadline) {
}
