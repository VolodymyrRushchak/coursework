package ua.lviv.iot.riverServer.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.riverServer.dataaccess.file.EntityStorage;
import ua.lviv.iot.riverServer.dataaccess.file.MeasurementStorage;
import ua.lviv.iot.riverServer.logic.MeasurementService;
import ua.lviv.iot.riverServer.model.Measurement;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class MeasurementServiceImpl extends EntityService implements MeasurementService {

    @Autowired
    private MeasurementStorage measurementStorage;

    @PostConstruct
    protected void init() {
        super.init();
    }

    @Override
    protected String[] getDirs() {
        return new String[]{"csvfiles", "measurements"};
    }

    @Override
    protected EntityStorage getStorage() {
        return measurementStorage;
    }

    public void create(final Measurement measurement) throws IOException {
        measurementStorage.create(measurement);
    }

    public List<Measurement> readAll() {
        return measurementStorage.readAll();
    }

    public Measurement read(final Long id) {
        return measurementStorage.read(id);
    }

    public List<Measurement> readAllStationsMeasurements(final Long id) {
        return measurementStorage.readAllStationsMeasurements(id);
    }

    public Boolean update(final Measurement measurement, final Long id) throws IOException {
        return measurementStorage.update(measurement, id);
    }

    public Boolean delete(final Long id) throws IOException {
        return measurementStorage.delete(id);
    }

}