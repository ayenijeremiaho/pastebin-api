package com.ayenijeremiaho.pastebinapi.startup;

import com.ayenijeremiaho.pastebinapi.employee.service.EmployeeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    private final EmployeeService employeeService;

    @Override
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        for (int i = 0; i < 3; i++) {
            String email = "user%s@law.com".formatted(i + 1);
            log.info("creating employee => {}", email);
            String result = employeeService.createEmployee(email, "password");
            log.info(result);
        }

        alreadySetup = true;
    }
}