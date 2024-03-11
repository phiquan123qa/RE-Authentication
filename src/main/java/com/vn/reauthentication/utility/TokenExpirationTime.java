package com.vn.reauthentication.utility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class TokenExpirationTime {
    private static final int EXPIRATION_TIME = 10;

    public static LocalDate getExpirationTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentTime.plusMinutes(EXPIRATION_TIME);
        return expirationDateTime.toLocalDate();
    }
}
