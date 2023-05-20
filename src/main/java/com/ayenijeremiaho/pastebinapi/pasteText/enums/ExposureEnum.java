package com.ayenijeremiaho.pastebinapi.pasteText.enums;

public enum ExposureEnum {
    PUBLIC("Public"), PRIVATE("Private");

    public final String description;

    ExposureEnum(String description) {
        this.description = description;
    }
}
