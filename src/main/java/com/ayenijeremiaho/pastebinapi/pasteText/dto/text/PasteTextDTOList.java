package com.ayenijeremiaho.pastebinapi.pasteText.dto.text;

import com.ayenijeremiaho.pastebinapi.pasteText.dto.other.PageableResponseDTO;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PasteTextDTOList extends PageableResponseDTO {
    private List<PasteTextDTO> pasteTextList;
}
