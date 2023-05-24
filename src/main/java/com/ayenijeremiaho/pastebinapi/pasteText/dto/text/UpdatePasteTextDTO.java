package com.ayenijeremiaho.pastebinapi.pasteText.dto.text;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatePasteTextDTO extends CreatePasteTextDTO {

    @NotNull(message = "No selected PasteText to edit")
    private Long id;
}
