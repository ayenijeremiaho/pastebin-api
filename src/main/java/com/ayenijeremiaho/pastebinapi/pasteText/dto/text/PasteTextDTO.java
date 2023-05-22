package com.ayenijeremiaho.pastebinapi.pasteText.dto.text;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PasteTextDTO {

    private Long id;

    private String text;

    private String author;

    private String category;

    private List<String> tags;

    private LocalDateTime creationDate;

    private LocalDateTime updatedDate;

    private LocalDateTime expirationDate;

    private String exposure;

    private String expiration;
}
