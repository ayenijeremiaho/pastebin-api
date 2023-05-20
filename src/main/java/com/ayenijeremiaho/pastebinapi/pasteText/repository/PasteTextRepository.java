package com.ayenijeremiaho.pastebinapi.pasteText.repository;

import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.model.PasteText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasteTextRepository extends JpaRepository<PasteText, Long> {
    Optional<PasteText> findByIdAndDeletedIsFalse(Long aLong);

    Page<PasteText> findAllByExposureAndDeletedIsFalse(ExposureEnum exposure, Pageable pageable);

    Page<PasteText> findAllByAuthor_EmailAndDeletedIsFalse(String author_email, Pageable pageable);
}
