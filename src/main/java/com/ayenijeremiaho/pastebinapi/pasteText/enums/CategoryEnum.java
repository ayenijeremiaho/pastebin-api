package com.ayenijeremiaho.pastebinapi.pasteText.enums;

public enum CategoryEnum {
    NONE("None"), FAMILY("Family"), CRIMINAL("Criminal"),
    PROPERTY("Property"), LABOUR("Labour"), CORPORATE("Corporate"),
    COMMON("Common"), ADMINISTRATIVE("Administrative"), CONSTITUTIONAL("Constitutional"),
    INTELLECTUAL("Intellectual"), SOFTWARE("Software");

    public final String description;

    CategoryEnum(String description) {
        this.description = description;
    }
}
