package com.example.medicationapi.controller;

import com.example.medicationapi.models.Machine;
import com.example.medicationapi.models.Schedule;
import com.example.medicationapi.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @GetMapping("/schedule")
    public List<Schedule> getSchedules() {
        return this.scheduleRepository.findAll();
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable(value = "id") Long scheduleId)
            throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found for id :: " + scheduleId));
        return ResponseEntity.ok().body(schedule);
    }

    @PostMapping("/schedule")
    public Schedule createSchedule(@Validated @RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @PutMapping("/schedule/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable(value = "id") Long scheduleId,
                                                 @Validated @RequestBody Schedule scheduleInfo) throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found for this id :: " + scheduleId));

        schedule.setPeriod(scheduleInfo.getPeriod());
        schedule.setTime(scheduleInfo.getTime());
        final Schedule updatedSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/schedule/{id}")
    public Map<String, Boolean> deleteSchedule(@PathVariable(value = "id") Long scheduleId)
            throws ResourceNotFoundException {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found for this id :: " + scheduleId));

        scheduleRepository.delete(schedule);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
