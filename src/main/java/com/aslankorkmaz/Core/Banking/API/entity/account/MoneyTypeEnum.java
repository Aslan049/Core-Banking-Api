package com.aslankorkmaz.Core.Banking.API.entity.account;

public enum MoneyTypeEnum {
    TRY,
    USD,
    EUR;


    public static MoneyTypeEnum fromString(String value) {
        if(value == null || value.isEmpty()){
            return TRY;
        }

        try {
            return MoneyTypeEnum.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return TRY;
        }
    }

}
