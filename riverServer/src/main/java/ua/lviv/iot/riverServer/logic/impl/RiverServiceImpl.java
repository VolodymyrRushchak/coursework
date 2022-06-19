package ua.lviv.iot.riverServer.logic.impl;

import org.springframework.stereotype.Service;
import ua.lviv.iot.riverServer.dataaccess.file.CSVFileStorage;
import ua.lviv.iot.riverServer.logic.RiverService;
import ua.lviv.iot.riverServer.model.River;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RiverServiceImpl implements RiverService {

    private Long riverIdHolder = 0L;
    private final HashMap<Long, River> rivers = new LinkedHashMap<>();

    public RiverServiceImpl(){
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); ++i) {
            Path path = Path.of("river-" + LocalDate.now().format(DateTimeFormatter.ofPattern("uuuu-MM-"))
                    + String.format("%02d", i) + ".csv");
            if (Files.exists(path)) {
                try {
                    Scanner scanner = new Scanner(path);
                    scanner.useDelimiter("[,\\n]");
                    scanner.nextLine();
                    while (scanner.hasNext()) {
                        River river = new River();
                        river.setId(Long.valueOf(scanner.next()));
                        if (river.getId() > riverIdHolder) {
                            riverIdHolder = river.getId();
                        }
                        river.setName(scanner.next());
                        river.setLength(Double.valueOf(scanner.next()));
                        river.setFlowRate(Double.valueOf(scanner.next()));
                        river.setBasinArea(Double.valueOf(scanner.next()));
                        rivers.put(river.getId(), river);
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    public void create(final River river) throws IOException {
        long riverId = ++riverIdHolder;
        river.setId(riverId);
        CSVFileStorage.writeToFile(river.getHeaders(), river.toCSV(), "river");
        rivers.put(river.getId(), river);
    }

    public List<River> readAll() {
        return new ArrayList<>(rivers.values());
    }

    public River read(final Long id) {
        return rivers.get(id);
    }

    public Boolean update(final River river, final Long id) throws IOException {
        CSVFileStorage.deleteFromFile(id, "river");
        river.setId(id);
        CSVFileStorage.writeToFile(river.getHeaders(), river.toCSV(), "river");
        if (rivers.containsKey(id)) {
            rivers.put(id, river);
            return true;
        }
        return false;
    }

    public Boolean delete(final Long id) throws IOException {
        CSVFileStorage.deleteFromFile(id, "river");
        return rivers.remove(id) != null;
    }

}
