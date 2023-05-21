package com.ayenijeremiaho.pastebinapi.pasteText.service.implementation;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.employee.repository.EmployeeRepository;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.CreatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.CategoryEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExpirationEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.model.PasteText;
import com.ayenijeremiaho.pastebinapi.pasteText.model.PasteTextTag;
import com.ayenijeremiaho.pastebinapi.pasteText.repository.PasteTextRepository;
import com.ayenijeremiaho.pastebinapi.pasteText.repository.PasteTextTagRepository;
import com.ayenijeremiaho.pastebinapi.pasteText.service.PasteTextService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@SpringBootTest
class PasteTextServiceImplTest {

    public static final String EMAIL = "user1@law.com";
    public static final String PASSWORD = "password";

    @Autowired
    private PasteTextService pasteTextService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private PasteTextRepository pasteTextRepository;

    @MockBean
    private PasteTextTagRepository pasteTextTagRepository;

    @Test
    @DisplayName("Generate URL based on the ID column in db")
    void createPasteTextURLTest() {
        Employee employee = Employee.builder().email(EMAIL).password(PASSWORD).build();

        CreatePasteTextDTO dto = getCreatePasteTextDTO(null);

        PasteText pasteText = getPasteText(dto, null);

        when(employeeRepository.findByEmail(EMAIL)).thenReturn(Optional.of(employee));
        when(pasteTextRepository.save(any())).thenReturn(pasteText);

        String url = pasteTextService.createPasteTextURL(dto, EMAIL);

        assertEquals("http://localhost/1", url);
    }

    @Test
    void createPasteTextWithoutTagTest() {
        Employee employee = Employee.builder().email(EMAIL).password(PASSWORD).build();

        CreatePasteTextDTO dto = getCreatePasteTextDTO(null);

        PasteText expectedPasteText = getPasteText(dto, null);

        when(employeeRepository.findByEmail(EMAIL)).thenReturn(Optional.of(employee));
        when(pasteTextRepository.save(any())).thenReturn(expectedPasteText);

        PasteText actualPasteText = pasteTextService.createPasteTextURL(dto, employee);

        verify(pasteTextTagRepository, times(0)).findByTag(any());
        verify(pasteTextTagRepository, times(0)).save(any());
        verify(pasteTextRepository, times(1)).save(any());

        assertEquals(expectedPasteText.getText(), actualPasteText.getText());
        assertEquals(expectedPasteText.getAuthor(), actualPasteText.getAuthor());
        assertEquals(expectedPasteText.getCategory(), actualPasteText.getCategory());
        assertEquals(expectedPasteText.getExposure(), actualPasteText.getExposure());

        assertNull(actualPasteText.getTags(), "Should be null");
    }

    @Test
    void createPasteTextWithTagTest() {
        Employee employee = Employee.builder().email(EMAIL).password(PASSWORD).build();

        String demo = "Demo";
        String test = "Test";

        List<String> tags = List.of(demo, test);
        CreatePasteTextDTO dto = getCreatePasteTextDTO(tags);

        PasteTextTag demoTextTag = PasteTextTag.builder().id(1L).tag(demo).build();
        PasteTextTag testTextTag = PasteTextTag.builder().id(2L).tag(test).build();
        List<PasteTextTag> pasteTextTags = List.of(demoTextTag, testTextTag);

        PasteText expectedPasteText = getPasteText(dto, pasteTextTags);

        when(employeeRepository.findByEmail(EMAIL)).thenReturn(Optional.of(employee));
        when(pasteTextRepository.save(any())).thenReturn(expectedPasteText);
        when(pasteTextTagRepository.findByTag(demo)).thenReturn(Optional.of(demoTextTag));
        when(pasteTextTagRepository.findByTag(test)).thenReturn(Optional.of(testTextTag));

        PasteText actualPasteText = pasteTextService.createPasteTextURL(dto, employee);

        int tagsCount = tags.size();
        verify(pasteTextTagRepository, times(tagsCount)).findByTag(any());
        verify(pasteTextTagRepository, times(0)).save(any());
        verify(pasteTextRepository, times(1)).save(any());

        assertEquals(expectedPasteText.getText(), actualPasteText.getText());
        assertEquals(expectedPasteText.getAuthor(), actualPasteText.getAuthor());
        assertEquals(expectedPasteText.getCategory(), actualPasteText.getCategory());
        assertEquals(expectedPasteText.getExposure(), actualPasteText.getExposure());

        assertSame(expectedPasteText.getTags(), actualPasteText.getTags());
    }

    @Test
    void createPasteTextWithDuplicateTagTest() {
        Employee employee = Employee.builder().email(EMAIL).password(PASSWORD).build();

        String demo = "Demo";

        List<String> tags = List.of(demo, demo, demo);
        CreatePasteTextDTO dto = getCreatePasteTextDTO(tags);

        PasteTextTag demoTextTag = PasteTextTag.builder().id(1L).tag(demo).build();
        List<PasteTextTag> pasteTextTags = Collections.singletonList(demoTextTag);

        PasteText expectedPasteText = getPasteText(dto, pasteTextTags);

        when(employeeRepository.findByEmail(EMAIL)).thenReturn(Optional.of(employee));
        when(pasteTextRepository.save(any())).thenReturn(expectedPasteText);
        when(pasteTextTagRepository.findByTag(demo)).thenReturn(Optional.ofNullable(demoTextTag));

        PasteText actualPasteText = pasteTextService.createPasteTextURL(dto, employee);

        verify(pasteTextTagRepository, times(1)).findByTag(any());
        verify(pasteTextTagRepository, times(0)).save(any());
        verify(pasteTextRepository, times(1)).save(any());

        assertEquals(1, actualPasteText.getTags().size(), "Skip duplicated item");
    }
    

    private static PasteText getPasteText(CreatePasteTextDTO dto, List<PasteTextTag> tags) {
        PasteText pasteText = new PasteText();
        pasteText.setId(1L);
        pasteText.setTags(tags);
        pasteText.setText(dto.getText());
        pasteText.setCategory(dto.getCategory());
        pasteText.setExpiration(dto.getExpiration());
        pasteText.setExposure(dto.getExposure());
        return pasteText;
    }

    @Test
    void updatePasteText() {
    }

    @Test
    void deletePasteText() {
    }

    @Test
    void viewPasteText() {
    }

    @Test
    void getAllPasteTexts() {
    }

    @Test
    void getMyPasteTexts() {
    }

    private static CreatePasteTextDTO getCreatePasteTextDTO(List<String> pasteTextTags) {
        CreatePasteTextDTO dto = new CreatePasteTextDTO();
        dto.setText("Hello World");
        dto.setTags(pasteTextTags);
        dto.setCategory(CategoryEnum.ADMINISTRATIVE);
        dto.setExpiration(ExpirationEnum.Day_1);
        dto.setExposure(ExposureEnum.PUBLIC);
        return dto;
    }
}