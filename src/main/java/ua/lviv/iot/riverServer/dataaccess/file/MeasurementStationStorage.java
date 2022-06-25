package ua.lviv.iot.riverServer.dataaccess.file;

import org.springframework.stereotype.Component;
import ua.lviv.iot.riverServer.model.MeasurementStation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Component
public class MeasurementStationStorage extends EntityStorage {
    private final HashMap<Long, MeasurementStation> measurementStations = new HashMap<>();

    public void create(final MeasurementStation measurementStation) throws IOException {
        super.create(measurementStation);
        measurementStations.put(idHolder, measurementStation);
    }

    public List<MeasurementStation> readAll() {
        return new ArrayList<>(measurementStations.values());
    }

    public MeasurementStation read(final Long id) {
        return measurementStations.get(id);
    }

    public List<MeasurementStation> readAllRiversMeasurementStations(final Long id) {
        return measurementStations.values().stream()
                .filter(measurementStation ->
                        Objects.equals(measurementStation.getRiverId(), id)).collect(Collectors.toList());
    }

    public Boolean update(final MeasurementStation measurementStation, final Long id) throws IOException {
        if (super.update(measurementStation, id)) {
            measurementStations.put(id, measurementStation);
            return true;
        }
        return false;
    }

    public Boolean delete(final Long id) throws IOException {
        measurementStations.remove(id);
        return super.delete(id);
    }

    @Override
    public String getType() {
        return "measurementStation";
    }

    @Override
    public void addToHash(final String record) {
        MeasurementStation measurementStation = new MeasurementStation(record);
        if (measurementStation.getId() > idHolder) {
            idHolder = measurementStation.getId();
        }
        measurementStations.put(measurementStation.getId(), measurementStation);
    }

}