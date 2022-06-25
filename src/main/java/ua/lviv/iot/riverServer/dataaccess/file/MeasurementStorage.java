package ua.lviv.iot.riverServer.dataaccess.file;

import org.springframework.stereotype.Component;
import ua.lviv.iot.riverServer.model.Measurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class MeasurementStorage extends EntityStorage {
    private final HashMap<Long, Measurement> measurements = new HashMap<>();

    public void create(final Measurement measurement) throws IOException {
        super.create(measurement);
        measurements.put(idHolder, measurement);
    }

    public List<Measurement> readAll() {
        return new ArrayList<>(measurements.values());
    }

    public Measurement read(final Long id) {
        return measurements.get(id);
    }

    public List<Measurement> readAllStationsMeasurements(final Long id) {
        return measurements.values().stream()
                .filter(measurement ->
                        Objects.equals(measurement.getStationId(), id)).collect(Collectors.toList());
    }

    public Boolean update(final Measurement measurement, final Long id) throws IOException {
        if (super.update(measurement, id)) {
            measurements.put(id, measurement);
            return true;
        }
        return false;
    }

    public Boolean delete(final Long id) throws IOException {
        measurements.remove(id);
        return super.delete(id);
    }

    @Override
    public String getType() {
        return "measurement";
    }

    @Override
    public void addToHash(final String record) {
        Measurement measurement = new Measurement(record);
        if (measurement.getId() > idHolder) {
            idHolder = measurement.getId();
        }
        measurements.put(measurement.getId(), measurement);
    }

}

