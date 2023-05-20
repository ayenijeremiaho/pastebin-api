package com.ayenijeremiaho.pastebinapi.pasteText.dto.category;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryListDTO {
    public List<CategoryDTO> categoryList;
}
