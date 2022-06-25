package ua.lviv.iot.riverServer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.beans.Transient;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity {
    private Long id;

    @Transient
    public String getHeaders() {
        return "id";
    }

    public String toCSV() {
        return id.toString();
    }

}
