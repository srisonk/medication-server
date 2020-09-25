package com.example.medicationapi.models;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.List;

@Entity
public class Machine extends RepresentationModel<Machine> {

    public Machine() {}

    public Machine(String manufacturer, String details) {
        super();
        this.manufacturer = manufacturer;
        this.details = details;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String manufacturer;
    private String details;
    @OneToMany(mappedBy = "machine")
    List<Medication> medicationList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Medication> getMedicationList() {
        return medicationList;
    }

    public void setMedicationList(List<Medication> medicationList) {
        this.medicationList = medicationList;
    }
}
