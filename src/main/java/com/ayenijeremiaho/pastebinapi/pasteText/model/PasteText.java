package com.ayenijeremiaho.pastebinapi.pasteText.model;

import com.ayenijeremiaho.pastebinapi.employee.model.Employee;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.CategoryEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExpirationEnum;
import com.ayenijeremiaho.pastebinapi.pasteText.enums.ExposureEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasteText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne(optional = false)
    private Employee author;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @ManyToMany
    private List<PasteTextTag> tags;

    @Enumerated(EnumType.STRING)
    private ExposureEnum exposure;

    @Enumerated(EnumType.STRING)
    private ExpirationEnum expiration;

    private LocalDateTime expirationDate;

    private LocalDateTime creationDate;

    private LocalDateTime updatedDate;

    private boolean deleted = false;

    private LocalDateTime deletedDate;
}
