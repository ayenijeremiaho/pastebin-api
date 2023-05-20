package com.ayenijeremiaho.pastebinapi.employee.service.implementation;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.employee.repository.EmployeeRepository;
import com.ayenijeremiaho.pastebinapi.exception.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return getUserDetails(username);
        } catch (Exception e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    public UserDetails getUserDetails(String email) throws Exception {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() ->
                new Exception("Invalid email address"));

        return User.builder().username(email).password(employee.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }

}