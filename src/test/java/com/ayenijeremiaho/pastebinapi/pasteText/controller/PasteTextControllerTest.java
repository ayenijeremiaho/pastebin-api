package com.ayenijeremiaho.pastebinapi.pasteText.controller;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PasteTextControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    void createPasteTextWithoutAuthorizationHeaderTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Full authentication is required to access this resource"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithValidAuthorizationHeaderAndValidRequestTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithTextValueLessThan5CharactersTest() throws Exception {

        String request = generateRequest(null, "Go", "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Text Should be a minimum of 5 characters"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithTextValueAsNullTest() throws Exception {

        String request = generateRequest(null, null, "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Text cannot be null"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithEmptyTextValueTest() throws Exception {

        String request = generateRequest(null, "", "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Text Should be a minimum of 5 characters"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithInvalidCategoryTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NON",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty());
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithCategoryAsNullTest() throws Exception {

        String request = generateRequest(null, "Hello World", null,
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Category cannot be empty, default should be NONE"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithInvalidExposureTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                "PUB", "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty());
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWitExposureAsNullTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                null, "NEVER");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Exposure cannot be empty, should either be private or public"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWithInvalidExpirationTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                "PUBLIC", "NEV");

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .isNotEmpty());
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createPasteTextWitExpirationAsNullTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                "PUBLIC", null);

        mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Expiration cannot be empty, default should be NEVER"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void updatePasteTextWithoutIdTest() throws Exception {

        String request = generateRequest(null, "Hello World", "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("No selected PasteText to edit"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void updatePasteTextWithInvalidIdTest() throws Exception {

        String request = generateRequest(10L, "Hello World 2", "NONE",
                "PUBLIC", "NEVER");

        mvc.perform(MockMvcRequestBuilders.put("/api/v1/pasteText")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("Text with Id does not exist"));
    }

    @WithMockUser(username = "user1@law.com")
    @Test
    void createAndUpdatePasteTextTest() {
        assertAll(
                () -> {

                    String request = generateRequest(null, "Hello World", "NONE",
                            "PUBLIC", "NEVER");

                    mvc.perform(MockMvcRequestBuilders.post("/api/v1/pasteText")
                                    .content(request)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().is2xxSuccessful())
                            .andExpect(MockMvcResultMatchers.status().isCreated())
                            .andExpect(MockMvcResultMatchers.content().string(containsString("/api/v1/pasteText/")));
                },
                () -> mvc.perform(MockMvcRequestBuilders.get("/api/v1/pasteText/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                                .value("Hello World"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.exposure")
                                .value("Public"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.category")
                                .value("None"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.expiration")
                                .value("Never")),

                () -> {
                    String request = generateRequest(1L, "Hello World 2", "CORPORATE",
                            "PRIVATE", "Hour_1");

                    mvc.perform(MockMvcRequestBuilders.put("/api/v1/pasteText")
                                    .content(request)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON))
                            .andExpect(status().is2xxSuccessful())
                            .andExpect(status().isOk());
                },
                () -> mvc.perform(MockMvcRequestBuilders.get("/api/v1/pasteText/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                                .value("Hello World 2"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.exposure")
                                .value("Private")
                        ).andExpect(MockMvcResultMatchers.jsonPath("$.category")
                                .value("Corporate"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.expiration")
                                .value("1 Hour")));
    }

    private String generateRequest(Long id, String text, String category, String exposure, String expiration) {
        JSONObject requestObject = new JSONObject();
        return requestObject
                .put("id", id)
                .put("text", text)
                .put("category", category)
                .put("exposure", exposure)
                .put("expiration", expiration)
                .toString();

    }

}