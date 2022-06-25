package ua.lviv.iot.riverServer.dataaccess.file;

import lombok.Data;
import ua.lviv.iot.riverServer.model.Entity;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Data
public abstract class EntityStorage {
    protected Long idHolder = 0L;
    protected String workingDirectory;

    public void initHashMap() {
        iterateOverCurrentMonth();
    }

    public void create(final Entity entity) throws IOException {
        entity.setId(++idHolder);
        String path = filePath(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        File file = new File(path);
        if (file.exists()) {
            try (FileWriter fileWriter = new FileWriter(path, StandardCharsets.UTF_8, true)) {
                fileWriter.write(entity.toCSV() + System.lineSeparator());
            }
        } else {
            try (FileWriter fileWriter = new FileWriter(path, StandardCharsets.UTF_8)) {
                fileWriter.write(entity.getHeaders() + System.lineSeparator());
                fileWriter.write(entity.toCSV() + System.lineSeparator());
            }
        }
    }

    public Boolean update(final Entity entity, final Long id) throws IOException {
        return changeFile(entity, id, true);
    }

    public Boolean delete(final Long id) throws IOException {
        return changeFile(null, id, false);
    }

    public void setWorkingDirectory(final String dir) {
        idHolder = 0L;
        workingDirectory = dir;
    }

    protected abstract String getType();

    protected abstract void addToHash(String record);

    private void iterateOverCurrentMonth() {
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); ++i) {
            String path = filePath(i);
            File file = new File(path);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                    List<String> lines = reader.lines().toList();
                    for (int j = 1; j < lines.size(); j++) {
                        addToHash(lines.get(j));
                    }
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        }
    }

    private Boolean changeFile(final Entity entity, final Long id, final Boolean update) throws IOException {
        if (update) {
            entity.setId(id);
        }
        for (int i = 1; i <= LocalDate.now().getDayOfMonth(); i++) {
            String path = filePath(i);
            File file = new File(path);
            if (file.exists()) {
                AtomicBoolean changed = new AtomicBoolean(false);
                try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
                    StringBuffer result = new StringBuffer();
                    reader.lines().forEach(line -> {
                        if (line.split(",")[0].equals(id.toString())) {
                            changed.set(true);
                            if (update) {
                                result.append(entity.toCSV()).append(System.lineSeparator());
                            }
                        } else {
                            result.append(line).append(System.lineSeparator());
                        }
                    });
                    if (changed.get()) {
                        try (FileWriter fileWriter = new FileWriter(path, StandardCharsets.UTF_8)) {
                            fileWriter.write(result.toString());
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String filePath(final int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String stringDate = dateFormat.format(calendar.getTime());
        return workingDirectory + File.separator + getType() + "-" + stringDate + ".csv";
    }

}
