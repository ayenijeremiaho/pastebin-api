package com.ayenijeremiaho.pastebinapi.pasteText.dto.category;

import com.ayenijeremiaho.pastebinapi.pasteText.enums.CategoryEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    private CategoryEnum category;
    private String description;
}
