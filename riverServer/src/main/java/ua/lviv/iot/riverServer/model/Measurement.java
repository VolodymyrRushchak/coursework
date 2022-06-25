package ua.lviv.iot.riverServer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Measurement extends Entity {

    private String date;
    private Double waterLevel;
    private String stationName;
    private Long stationId;

    public Measurement(final Long id, final String date, final Double waterLevel, final String stationName, final Long stationId) {
        super(id);
        this.date = date;
        this.waterLevel = waterLevel;
        this.stationName = stationName;
        this.stationId = stationId;
    }

    public Measurement(final String record) {
        String[] params = record.split(",");
        this.setId(Long.valueOf(params[0]));
        this.setDate(params[1]);
        this.setWaterLevel(Double.valueOf(params[2]));
        this.setStationName(params[3]);
        this.setStationId(Long.valueOf(params[4]));
    }

    @Transient
    public String getHeaders() {
        return super.getHeaders() + ",date,waterLevel,stationName,stationId";
    }

    public String toCSV() {
        return super.toCSV() + "," + date + "," + waterLevel + "," + stationName + "," + stationId;
    }

}
