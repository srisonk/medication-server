package com.example.medicationapi.controller;

import com.example.medicationapi.repository.MachineRepository;
import com.example.medicationapi.models.Machine;
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
public class MachineController {

    @Autowired
    private MachineRepository machineRepository;

    @GetMapping("/machine")
    public List<Machine> getMachines() {
        return this.machineRepository.findAll();
    }

    @GetMapping(value = "/hateoas/machine", produces = { "application/hal+json"})
    public CollectionModel<Machine> getHateOASMAchine() {
        List<Machine> machines = this.machineRepository.findAll();

        for(Machine machine : machines) {
            Long machineId = machine.getId();
            Link selfLink = linkTo(MachineController.class).slash("machine/"+machineId).withSelfRel();
            machine.add(selfLink);
            if(machineRepository.findAll().size() > 0) {
                Link machinesLink = linkTo(methodOn(MachineController.class)
                .getMachineById(machineId)).withRel("machineIds");
                machine.add(machinesLink);
            }
        }

        Link link = linkTo(MachineController.class).withSelfRel();
        CollectionModel<Machine> result = new CollectionModel<>(machines, link);
        return result;
    }

    @GetMapping("/machine/{id}")
    public ResponseEntity<Machine> getMachineById(@PathVariable(value = "id") Long machineId)
            throws ResourceNotFoundException {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found for id :: " + machineId));
        return ResponseEntity.ok().body(machine);
    }

    @PostMapping("/machine")
    public Machine createMachine(@Validated @RequestBody Machine machine) {
        return machineRepository.save(machine);
    }

    @PutMapping("/machine/{id}")
    public ResponseEntity<Machine> updateMachine(@PathVariable(value = "id") Long machineId,
                                                 @Validated @RequestBody Machine machineInfo) throws ResourceNotFoundException {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found for this id :: " + machineId));

        machine.setDetails(machineInfo.getDetails());
        machine.setManufacturer(machineInfo.getManufacturer());
        final Machine updatedMachine = machineRepository.save(machine);
        return ResponseEntity.ok(updatedMachine);
    }

    @DeleteMapping("/machine/{id}")
    public Map<String, Boolean> deleteMachine(@PathVariable(value = "id") Long machineId)
            throws ResourceNotFoundException {
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine not found for this id :: " + machineId));

        machineRepository.delete(machine);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
