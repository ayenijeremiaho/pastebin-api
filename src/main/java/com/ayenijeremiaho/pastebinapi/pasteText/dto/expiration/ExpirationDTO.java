package com.ayenijeremiaho.pastebinapi.pasteText.dto.expiration;

import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExpirationEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpirationDTO {
    private ExpirationEnum expiration;
    private String description;
}
