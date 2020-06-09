package com.tuncerergin.covid19.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class CoronaVirus {
    private Location location;
    private String updatedDateTime;
    private Stats stats;

    public String getUpdatedDateTime() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy'/'HH:mm:ss");
        LocalDateTime date = LocalDateTime.parse(updatedDateTime, inputFormatter);
        return outputFormatter.format(date);
    }
}
