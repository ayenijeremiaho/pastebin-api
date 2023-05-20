package com.ayenijeremiaho.pastebinapi.pasteText.dto.expiration;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExpirationListDTO {
    private List<ExpirationDTO> expirationList;
}
