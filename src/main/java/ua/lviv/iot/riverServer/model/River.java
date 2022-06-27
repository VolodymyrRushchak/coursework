package ua.lviv.iot.riverServer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class River extends Entity {
    private String name;
    private Double length;
    private Double flowRate;
    private Double basinArea;

    public River(final Long id, final String name, final Double length, final Double flowRate, final Double basinArea) {
        super(id);
        this.name = name;
        this.length = length;
        this.flowRate = flowRate;
        this.basinArea = basinArea;
    }

    public River(final String record) {
        String[] params = record.split(",");
        this.setId(Long.valueOf(params[0]));
        this.setName(params[1]);
        this.setLength(Double.valueOf(params[2]));
        this.setFlowRate(Double.valueOf(params[3]));
        this.setBasinArea(Double.valueOf(params[4]));
    }

    @Transient
    public String getHeaders() {
        return super.getHeaders() + ",name,length,flowRate,basinArea";
    }

    public String toCSV() {
        return super.toCSV() + "," + name + "," + length + "," + flowRate + "," + basinArea;
    }

}
