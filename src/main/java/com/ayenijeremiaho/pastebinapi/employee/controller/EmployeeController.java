package com.ayenijeremiaho.pastebinapi.employee.controller;

import com.ayenijeremiaho.pastebinapi.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @GetMapping("/me")
    public ResponseEntity<?> me(Principal principal) {
        return ResponseEntity.ok("You're in %s".formatted(principal.getName()));
    }

    @GetMapping()
    public ResponseEntity<?> allEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }
}
