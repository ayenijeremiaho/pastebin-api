package com.ayenijeremiaho.pastebinapi.pasteText.dto.exposure;

import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExposureDTO {
    private ExposureEnum exposure;
    private String description;
}
