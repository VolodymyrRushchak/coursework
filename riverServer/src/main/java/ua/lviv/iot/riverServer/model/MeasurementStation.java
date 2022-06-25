package ua.lviv.iot.riverServer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.beans.Transient;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MeasurementStation extends Entity {

    private String name;
    private String gpsCoordinates;
    private Long riverId;

    public MeasurementStation(final Long id, final String name, final String gpsCoordinates, final Long riverId) {
        super(id);
        this.name = name;
        this.gpsCoordinates = gpsCoordinates;
        this.riverId = riverId;
    }

    public MeasurementStation(final String record) {
        String[] params = record.split(",");
        this.setId(Long.valueOf(params[0]));
        this.setName(params[1]);
        this.setGpsCoordinates(params[2]);
        this.setRiverId(Long.valueOf(params[3]));
    }

    @Transient
    public String getHeaders() {
        return super.getHeaders() + ",name,gpsCoordinates,riverId";
    }

    public String toCSV() {
        return super.toCSV() + "," + name + "," + gpsCoordinates + "," + riverId;
    }

}
