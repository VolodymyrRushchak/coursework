package ua.lviv.iot.riverServer.logic.impl;

import org.springframework.stereotype.Service;
import ua.lviv.iot.riverServer.dataaccess.file.CSVFileStorage;
import ua.lviv.iot.riverServer.logic.MeasurementStationService;
import ua.lviv.iot.riverServer.model.MeasurementStation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MeasurementStationServiceImpl implements MeasurementStationService {

    private Long measurementStationIdHolder = 0L;
    private final HashMap<Long, MeasurementStation> measurementStations = new LinkedHashMap<>();

    public MeasurementStationServiceImpl(){
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); ++i) {
            Path path = Path.of("measurementStation-" + LocalDate.now().format(DateTimeFormatter.ofPattern("uuuu-MM-"))
                    + String.format("%02d", i) + ".csv");
            if (Files.exists(path)) {
                try {
                    Scanner scanner = new Scanner(path);
                    scanner.useDelimiter("[,\\n]");
                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        MeasurementStation measurementStation = new MeasurementStation();
                        measurementStation.setId(Long.valueOf(scanner.next()));
                        if (measurementStation.getId() > measurementStationIdHolder) {
                            measurementStationIdHolder = measurementStation.getId();
                        }
                        measurementStation.setName(scanner.next());
                        measurementStation.setGpsCoordinates(scanner.next());
                        measurementStation.setRiverId(Long.valueOf(scanner.next()));
                        measurementStations.put(measurementStation.getId(), measurementStation);
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    public void create(final MeasurementStation measurementStation) throws IOException {
        long measurementStationId = ++measurementStationIdHolder;
        measurementStation.setId(measurementStationId);
        CSVFileStorage.writeToFile(measurementStation.getHeaders(), measurementStation.toCSV(), "measurementStation");
        measurementStations.put(measurementStation.getId(), measurementStation);
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
        CSVFileStorage.deleteFromFile(id, "measurementStation");
        measurementStation.setId(id);
        CSVFileStorage.writeToFile(measurementStation.getHeaders(), measurementStation.toCSV(), "measurementStation");
        if (measurementStations.containsKey(id)) {
            measurementStations.put(id, measurementStation);
            return true;
        }
        return false;
    }

    public Boolean delete(final Long id) throws IOException {
        CSVFileStorage.deleteFromFile(id, "measurementStation");
        return measurementStations.remove(id) != null;
    }

}
