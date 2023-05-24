package com.ayenijeremiaho.pastebinapi.pasteText.dto.text;

import com.ayenijeremiaho.pastebinapi.pasteText.enums.CategoryEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExpirationEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreatePasteTextDTO {

    @NotNull(message = "Text cannot be null")
    @Size(min = 5, message = "Text Should be a minimum of 5 characters")
    private String text;

    @NotNull(message = "Category cannot be empty, default should be NONE")
    private CategoryEnum category;

    @Size(max = 3, message = "Maximum of 3 tags allowed")
    private List<String> tags;

    @NotNull(message = "Exposure cannot be empty, should either be private or public")
    private ExposureEnum exposure;

    @NotNull(message = "Expiration cannot be empty, default should be NEVER")
    private ExpirationEnum expiration;

}
