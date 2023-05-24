package com.ayenijeremiaho.pastebinapi.auth.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthControllerTest {


    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    void authenticateUserWithWrongCredentialsTest() throws Exception {

        JSONObject requestObject = new JSONObject();
        String request = requestObject.put("email", "wrong@law.com")
                .put("password", "password").toString();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login").content(request).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized()).andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid username or password"));
    }

    @Test
    void authenticateUserWithValidCredentialsTest() throws Exception {

        JSONObject requestObject = new JSONObject();
        String request = requestObject.put("email", "user1@law.com")
                .put("password", "password").toString();

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login").content(request).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().is2xxSuccessful()).andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").isNotEmpty());
    }

}