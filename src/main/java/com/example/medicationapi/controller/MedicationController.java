package com.example.medicationapi.controller;

import com.example.medicationapi.models.Medication;
import com.example.medicationapi.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class MedicationController {

    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping("/medication")
    public List<Medication> getMedications() {
        return this.medicationRepository.findAll();
    }

    @GetMapping(value = "/hateoas/medication", produces = { "application/hal+json"})
    public CollectionModel<Medication> getHateOASMedication() {
        List<Medication> medications = this.medicationRepository.findAll();

        for(Medication medication : medications) {
            Long medicationId = medication.getId();
            Link selfLink = linkTo(MedicationController.class).slash("medication/"+medicationId).withSelfRel();
            medication.add(selfLink);
            if(medicationRepository.findAll().size() > 0) {
                Link medicationLink = linkTo(methodOn(MedicationController.class)
                        .getMedicationById(medicationId)).withRel("medicationIds");
                medication.add(medicationLink);
            }
        }

        Link link = linkTo(MedicationController.class).withSelfRel();
        CollectionModel<Medication> result = new CollectionModel<>(medications, link);
        return result;
    }

    @GetMapping("/medication/{id}")
    public ResponseEntity<Medication> getMedicationById(@PathVariable(value = "id") Long medicationId)
            throws ResourceNotFoundException {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found for id :: " + medicationId));
        return ResponseEntity.ok().body(medication);
    }

    @PostMapping("/medication")
    public Medication createMedication(@Validated @RequestBody Medication medication) {
        return medicationRepository.save(medication);
    }

    @PutMapping("/medication/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable(value = "id") Long medicationId,
                                                 @Validated @RequestBody Medication medicationInfo) throws ResourceNotFoundException {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found for this id :: " + medicationId));

        medication.setName(medicationInfo.getName());
        medication.setDosage(medicationInfo.getDosage());
        medication.setDate_created(medicationInfo.getDate_created());
        medication.setDate_expiry(medicationInfo.getDate_expiry());
        final Medication updatedMedication = medicationRepository.save(medication);
        return ResponseEntity.ok(updatedMedication);
    }

    @DeleteMapping("/medication/{id}")
    public Map<String, Boolean> deleteMedication(@PathVariable(value = "id") Long medicationId)
            throws ResourceNotFoundException {
        Medication medication = medicationRepository.findById(medicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Medication not found for this id :: " + medicationId));

        medicationRepository.delete(medication);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
