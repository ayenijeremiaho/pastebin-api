package com.ayenijeremiaho.pastebinapi.employee.service;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;

public interface EmployeeService {

    String createEmployee(String email, String password);

    Employee getEmployee(String email);
}
