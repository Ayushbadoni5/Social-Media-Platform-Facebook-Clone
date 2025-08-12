package com.facebook.Enums;

public enum Gender {
    MALE,
    FEMALE,
    OTHERS;

    public static Gender fromString(String value){
        try {
            return Gender.valueOf(value.trim().toUpperCase());
        }catch (Exception e){
            throw  new IllegalArgumentException("Invalid gender: " + value + ". Please enter a valid gender(MALE/FEMALE/OTHER)." );
        }
    }
}
