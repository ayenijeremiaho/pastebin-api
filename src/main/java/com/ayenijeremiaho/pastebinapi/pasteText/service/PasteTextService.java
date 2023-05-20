package com.ayenijeremiaho.pastebinapi.pasteText.service;

import com.ayenijeremiaho.pastebinapi.pasteText.dto.category.CategoryDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.category.CategoryListDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.expiration.ExpirationDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.expiration.ExpirationListDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.exposure.ExposureDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.exposure.ExposureListDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.CreatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.PasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.PasteTextDTOList;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.UpdatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.CategoryEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExpirationEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;

import java.util.Arrays;
import java.util.List;

public interface PasteTextService {

    String createPasteText(CreatePasteTextDTO request, String author);

    String updatePasteText(UpdatePasteTextDTO request, String author);

    void deletePasteText(Long id, String author);

    PasteTextDTO viewPasteText(Long id, String requester);

    PasteTextDTOList getAllPasteTexts(int size, int page, String requester);

    PasteTextDTOList getMyPasteTexts(int size, int page, String requester);

    default CategoryListDTO getCategories() {
        List<CategoryDTO> categoryList = Arrays.stream(CategoryEnum.values())
                .map(categoryEnum -> CategoryDTO.builder()
                        .category(categoryEnum).description(categoryEnum.description).build())
                .toList();
        return CategoryListDTO.builder().categoryList(categoryList).build();
    }

    default ExpirationListDTO getExpirationList() {
        List<ExpirationDTO> expirationList = Arrays.stream(ExpirationEnum.values())
                .map(expirationEnum -> ExpirationDTO.builder()
                        .expiration(expirationEnum).description(expirationEnum.description).build())
                .toList();
        return ExpirationListDTO.builder().expirationList(expirationList).build();
    }

    default ExposureListDTO getExposureList() {
        List<ExposureDTO> exposureList = Arrays.stream(ExposureEnum.values())
                .map(exposureEnum -> ExposureDTO.builder()
                        .exposure(exposureEnum).description(exposureEnum.description).build())
                .toList();
        return ExposureListDTO.builder().exposureList(exposureList).build();
    }
}
