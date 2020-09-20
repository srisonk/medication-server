package com.example.medicationapi.models;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class Schedule {

    public Schedule() {}

    public Schedule(String period, LocalTime time) {
        super();
        this.period = period;
        this.time = time;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    private String period;
    private LocalTime time;
    @ManyToOne
    Medication medication;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }
}
