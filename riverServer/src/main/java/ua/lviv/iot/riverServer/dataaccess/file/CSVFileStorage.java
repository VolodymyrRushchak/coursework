package ua.lviv.iot.riverServer.dataaccess.file;

import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Component
public final class CSVFileStorage {

    public void writeToFile(final String headers, final  String toCSV, final  String type) throws IOException {
        String path = type + "-" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".csv";
        if (!Files.exists(Path.of(path))) {
            try (FileWriter fileWriter = new FileWriter(path, StandardCharsets.UTF_8)) {
                fileWriter.write(headers + "\r\n");
                fileWriter.write(toCSV + "\r\n");
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(path, StandardCharsets.UTF_8, true)) {
                fileWriter.write(toCSV + "\r\n");
            }
        }
    }

    public void deleteFromFile(final Long id, final String type) throws IOException {
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {
            Path path = Path.of(type + "-" + LocalDate.now().format(DateTimeFormatter.ofPattern("uuuu-MM-"))
                    + String.format("%02d", i) + ".csv");
            if (Files.exists(path)) {
                List<String> result = Files.lines(path)
                        .filter(line -> !line.startsWith(String.valueOf(id)))
                        .collect(Collectors.toList());
                Files.write(path, result);
            }
        }
    }

}
