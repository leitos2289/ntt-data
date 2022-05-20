package com.sistema.nttdata.common;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public final class ApplicationDates {
    public static final Timestamp DEFAULT_EXPIRY_TIMESTAMP;

    private ApplicationDates() {
    }

    public static Timestamp getDBTimestamp() {

        return new Timestamp((new java.util.Date()).getTime());
    }

    static {
        try {
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date a = fd.parse("2999-12-31");
            DEFAULT_EXPIRY_TIMESTAMP = new Timestamp(a.getTime());
        } catch (ParseException var2) {
            throw new RuntimeException(var2);
        }
    }
}
