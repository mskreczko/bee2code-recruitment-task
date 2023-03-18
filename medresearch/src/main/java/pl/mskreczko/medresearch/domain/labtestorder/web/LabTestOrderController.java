package pl.mskreczko.medresearch.domain.labtestorder.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mskreczko.medresearch.domain.labtestorder.dto.LabTestOrderCreationDto;
import pl.mskreczko.medresearch.domain.labtestorder.service.LabTestOrderService;
import pl.mskreczko.medresearch.exceptions.NoSuchEntityException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/lab-orders")
public class LabTestOrderController {

    private final LabTestOrderService labTestOrderService;

    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody LabTestOrderCreationDto labTestOrderCreationDto) {
        try {
            labTestOrderService.addNewOrder(labTestOrderCreationDto);
            return new ResponseEntity<>(HttpStatusCode.valueOf(201));
        } catch (NoSuchEntityException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
