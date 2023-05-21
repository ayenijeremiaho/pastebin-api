package com.ayenijeremiaho.pastebinapi.employee.service.implementation;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.employee.repository.EmployeeRepository;
import com.ayenijeremiaho.pastebinapi.employee.service.EmployeeService;
import com.ayenijeremiaho.pastebinapi.exception.GeneralException;
import com.ayenijeremiaho.pastebinapi.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    @Override
    public String createEmployee(String email, String password) {
        log.info("Creating employee with email => {}", email);

        boolean alreadyExist = employeeRepository.existsByEmail(email);
        if (alreadyExist) {
            throw new GeneralException(email + " already exist");
        }

        Employee employee = getEmployee(email, password);

        employeeRepository.save(employee);

        return email + " was successfully created";
    }

    @Override
    public Employee getEmployee(String email) {
        log.info("Getting employee with email => {}", email);

        return employeeRepository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(email + " is invalid"));
    }

    private Employee getEmployee(String email, String password) {
        return Employee.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }
}
