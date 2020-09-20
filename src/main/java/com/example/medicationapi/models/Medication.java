package com.example.medicationapi.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Medication {
    public Medication() {}

    public Medication(String name, String dosage, String date_created, String date_expiry) {
        super();
        this.name = name;
        this.dosage = dosage;
        this.date_created = date_created;
        this.date_expiry = date_expiry;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    private String name;
    private String dosage;
    private String date_created;
    private String date_expiry;
    @ManyToOne
    Machine machine;

    @OneToMany(mappedBy = "medication")
    List<Schedule> scheduleList;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_expiry() {
        return date_expiry;
    }

    public void setDate_expiry(String date_expiry) {
        this.date_expiry = date_expiry;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
