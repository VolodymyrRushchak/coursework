package ua.lviv.iot.riverServer.logic.impl;

import org.springframework.stereotype.Service;
import ua.lviv.iot.riverServer.dataaccess.file.CSVFileStorage;
import ua.lviv.iot.riverServer.logic.MeasurementService;
import ua.lviv.iot.riverServer.model.Measurement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MeasurementServiceImpl implements MeasurementService {

    private static Long measurementIdHolder = 0L;
    private static final HashMap<Long, Measurement> measurements = new LinkedHashMap<>();


    public static void initialize() {
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); ++i) {
            Path path = Path.of("measurement-" + LocalDate.now().format(DateTimeFormatter.ofPattern("uuuu-MM-"))
                    + String.format("%02d", i) + ".csv");
            if (Files.exists(path)) {
                try {
                    Scanner scanner = new Scanner(path);
                    scanner.useDelimiter(",|(\\r\\n)");
                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        Measurement measurement = new Measurement();
                        measurement.setId(Long.valueOf(scanner.next()));
                        if (measurement.getId() > measurementIdHolder) {
                            measurementIdHolder = measurement.getId();
                        }
                        measurement.setDate(scanner.next());
                        measurement.setWaterLevel(Double.valueOf(scanner.next()));
                        measurement.setStationName(scanner.next());
                        measurement.setStationId(Long.valueOf(scanner.next()));
                        measurements.put(measurement.getId(), measurement);
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    public void create(final Measurement measurement) throws IOException {
        long measurementId = ++measurementIdHolder;
        measurement.setId(measurementId);
        CSVFileStorage.writeToFile(measurement.getHeaders(), measurement.toCSV(), "measurement");
        measurements.put(measurement.getId(), measurement);
    }

    public List<Measurement> readAll() {
        return new ArrayList<>(measurements.values());
    }

    public Measurement read(final Long id) {
        return measurements.get(id);
    }

    public Boolean update(final Measurement measurement, final Long id) throws IOException {
        CSVFileStorage.deleteFromFile(id, "measurement");
        measurement.setId(id);
        CSVFileStorage.writeToFile(measurement.getHeaders(), measurement.toCSV(), "measurement");
        if (measurements.containsKey(id)) {
            measurements.put(id, measurement);
            return true;
        }
        return false;
    }

    public Boolean delete(final Long id) throws IOException {
        CSVFileStorage.deleteFromFile(id, "measurement");
        return measurements.remove(id) != null;
    }

}
