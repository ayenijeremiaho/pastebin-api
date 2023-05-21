package com.ayenijeremiaho.pastebinapi.employee.service.implementation;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.employee.repository.EmployeeRepository;
import com.ayenijeremiaho.pastebinapi.employee.service.EmployeeService;
import com.ayenijeremiaho.pastebinapi.exception.GeneralException;
import com.ayenijeremiaho.pastebinapi.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceImplTest {

    public static final String EMAIL = "user1@law.com";
    public static final String PASSWORD = "password";

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void createEmployeeTest() {
        when(employeeRepository.existsByEmail(EMAIL)).thenReturn(false);
        assertEquals(EMAIL + " was successfully created", employeeService.createEmployee(EMAIL, PASSWORD));
    }

    @Test
    void createEmployeeAlreadyExistTest() {
        when(employeeRepository.existsByEmail(EMAIL)).thenReturn(true);
        assertThrows(GeneralException.class, () -> employeeService.createEmployee(EMAIL, PASSWORD));
    }

    @Test
    void getEmployeeTest() {
        Employee employee = Employee.builder().email(EMAIL).password(PASSWORD).build();

        when(employeeRepository.findByEmail(EMAIL)).thenReturn(Optional.of(employee));
        assertEquals(EMAIL, employeeService.getEmployee(EMAIL).getEmail());
    }

    @Test
    void getEmployeeWithInvalidEmailTest() {
        when(employeeRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> employeeService.getEmployee(EMAIL));
    }

}