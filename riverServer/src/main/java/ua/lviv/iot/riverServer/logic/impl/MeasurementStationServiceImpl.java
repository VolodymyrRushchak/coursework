package ua.lviv.iot.riverServer.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.riverServer.dataaccess.file.EntityStorage;
import ua.lviv.iot.riverServer.dataaccess.file.MeasurementStationStorage;
import ua.lviv.iot.riverServer.logic.MeasurementStationService;
import ua.lviv.iot.riverServer.model.MeasurementStation;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class MeasurementStationServiceImpl extends EntityService implements MeasurementStationService {

    @Autowired
    private MeasurementStationStorage measurementStationStorage;

    @PostConstruct
    protected void init() {
        super.init();
    }

    @Override
    protected String[] getDirs() {
        return new String[]{"csvfiles", "measurementStations"};
    }

    @Override
    protected EntityStorage getStorage() {
        return measurementStationStorage;
    }

    public void create(final MeasurementStation measurementStation) throws IOException {
        measurementStationStorage.create(measurementStation);
    }

    public List<MeasurementStation> readAll() {
        return measurementStationStorage.readAll();
    }

    public MeasurementStation read(final Long id) {
        return measurementStationStorage.read(id);
    }

    public List<MeasurementStation> readAllRiversMeasurementStations(final Long id) {
        return measurementStationStorage.readAllRiversMeasurementStations(id);
    }

    public Boolean update(final MeasurementStation measurementStation, final Long id) throws IOException {
        return measurementStationStorage.update(measurementStation, id);
    }

    public Boolean delete(final Long id) throws IOException {
        return measurementStationStorage.delete(id);
    }

}