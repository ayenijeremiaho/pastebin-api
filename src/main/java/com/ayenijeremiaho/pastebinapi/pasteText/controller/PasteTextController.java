package com.ayenijeremiaho.pastebinapi.pasteText.controller;

import com.ayenijeremiaho.pastebinapi.pasteText.dto.category.CategoryListDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.expiration.ExpirationListDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.exposure.ExposureListDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.CreatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.PasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.PasteTextDTOList;
import com.ayenijeremiaho.pastebinapi.pasteText.dto.text.UpdatePasteTextDTO;
import com.ayenijeremiaho.pastebinapi.pasteText.service.PasteTextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;

@Slf4j
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pasteText")
public class PasteTextController {

    private final PasteTextService pasteTextService;

    @PostMapping()
    public ResponseEntity<String> createPasteText(Principal principal,
                                                  @Valid @RequestBody CreatePasteTextDTO requestDTO) {

        String url = pasteTextService.createPasteText(requestDTO, principal.getName());
        return ResponseEntity.created(URI.create(url)).build();
    }

    @PutMapping()
    public ResponseEntity<String> updatePasteText(Principal principal,
                                                  @Valid @RequestBody UpdatePasteTextDTO requestDTO) {

        String url = pasteTextService.updatePasteText(requestDTO, principal.getName());
        return ResponseEntity.ok(url);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePasteText(Principal principal, @PathVariable Long id) {

        pasteTextService.deletePasteText(id, principal.getName());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewPasteText(Principal principal, @PathVariable Long id) {

        PasteTextDTO textDTO = pasteTextService.viewPasteText(id, principal.getName());
        return ResponseEntity.ok(textDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<?> allPasteText(Principal principal, @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {

        PasteTextDTOList textDTOList = pasteTextService.getAllPasteTexts(size, page, principal.getName());
        return ResponseEntity.ok(textDTOList);
    }

    @GetMapping("/personal")
    public ResponseEntity<?> myPasteText(Principal principal, @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {

        PasteTextDTOList textDTOList = pasteTextService.getMyPasteTexts(size, page, principal.getName());
        return ResponseEntity.ok(textDTOList);
    }

    @GetMapping("/categories")
    public ResponseEntity<CategoryListDTO> getCategories(Principal principal) {
        log.info("Getting all categories by {}", principal.getName());
        CategoryListDTO categories = pasteTextService.getCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/expirationTypes")
    public ResponseEntity<ExpirationListDTO> getExpirationTypes(Principal principal) {
        log.info("Getting all expiration types by {}", principal.getName());
        ExpirationListDTO expirationList = pasteTextService.getExpirationList();
        return ResponseEntity.ok(expirationList);
    }

    @GetMapping("/exposureTypes")
    public ResponseEntity<ExposureListDTO> getExposureTypes(Principal principal) {
        log.info("Getting all exposure types by {}", principal.getName());
        ExposureListDTO exposureList = pasteTextService.getExposureList();
        return ResponseEntity.ok(exposureList);
    }
}
