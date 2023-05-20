package com.ayenijeremiaho.pastebinapi.pasteText.dto.other;

import lombok.Data;

@Data
public class PageableResponseDTO {
    private boolean hasNextRecord;
    private int totalCount;
}
