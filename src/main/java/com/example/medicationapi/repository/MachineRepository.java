package com.example.medicationapi.repository;

import com.example.medicationapi.models.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine,Long> {

}
