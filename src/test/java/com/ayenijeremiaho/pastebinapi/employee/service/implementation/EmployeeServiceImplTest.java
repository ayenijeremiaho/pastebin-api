package com.ayenijeremiaho.pastebinapi.employee.service.implementation;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.employee.repository.EmployeeRepository;
import com.ayenijeremiaho.pastebinapi.employee.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
class EmployeeServiceImplTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void createEmployeeTest() {
        String mail = "user1@law.com";
        Mockito.when(employeeRepository.findByEmail(mail)).thenReturn(Optional.of(Employee.builder().email(mail).build()));

        Assertions.assertEquals(mail, employeeService.getEmployee(mail).getEmail());

    }

    @Test
    void getEmployee() {
    }
}