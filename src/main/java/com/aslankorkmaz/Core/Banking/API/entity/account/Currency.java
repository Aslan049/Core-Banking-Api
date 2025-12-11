package com.aslankorkmaz.Core.Banking.API.entity.account;

public enum Currency {
    TRY,
    USD,
    EUR;


    public static Currency fromString(String value) {
        if(value == null || value.isEmpty()){
            return TRY;
        }

        try {
            return Currency.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return TRY;
        }
    }

}
