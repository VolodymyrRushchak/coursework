package ua.lviv.iot.riverServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {

    private Long id;
    private String date;
    private Double waterLevel;
    private String stationName;

    @Transient
    public String getHeaders() {
        return "id,date,waterLevel,stationName";
    }

    public String toCSV() {
        return id + "," + date + "," + waterLevel + "," + stationName;
    }

}
