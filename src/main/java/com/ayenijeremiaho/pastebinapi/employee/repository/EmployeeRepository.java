package com.ayenijeremiaho.pastebinapi.employee.repository;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);
}