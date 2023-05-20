package com.ayenijeremiaho.pastebinapi.pasteText.enums;

public enum ExpirationEnum {
    NEVER("Never"), Burn_after_read("Burn after read"),
    Minutes_10("10 Minutes"), Hour_1("1 Hour"), Day_1("1 Day"),
    Week_1("1 Week"), Week_2("2 Weeks"), Month_1("1 Month");

    public final String description;

    ExpirationEnum(String description) {
        this.description = description;
    }
}