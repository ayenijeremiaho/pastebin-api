package com.ayenijeremiaho.pastebinapi.pasteText.repository;

import com.ayenijeremiaho.pastebinapi.pasteText.model.PasteTextTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasteTextTagRepository extends JpaRepository<PasteTextTag, Long> {
    Optional<PasteTextTag> findByTag(String tag);
}
