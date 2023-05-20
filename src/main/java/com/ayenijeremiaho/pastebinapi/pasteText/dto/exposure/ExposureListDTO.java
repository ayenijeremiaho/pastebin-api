package com.ayenijeremiaho.pastebinapi.pasteText.dto.exposure;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExposureListDTO {
    private List<ExposureDTO> exposureList;
}
