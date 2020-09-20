package com.example.medicationapi;

import com.example.medicationapi.models.Machine;
import com.example.medicationapi.models.Medication;
import com.example.medicationapi.repository.MachineRepository;
import com.example.medicationapi.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//@EnableSwagger2
public class MedicationApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(MedicationApiApplication.class, args);
	}

	@Autowired
	private MachineRepository machineRepository;

	@Autowired
	private MedicationRepository medicationRepository;

	@Override
	public void run(String... args) throws Exception {


		this.machineRepository.save(new Machine("Nike","World"));
		this.machineRepository.save(new Machine("Adidas","World"));
		this.machineRepository.save(new Machine("Puma","World"));

		this.medicationRepository.save(new Medication("Ibuprofen","5 times day", "2020-01-20", "2022-03-25"));
	}
}
