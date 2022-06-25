package ua.lviv.iot.riverServer.dataaccess.file;

import org.springframework.stereotype.Component;
import ua.lviv.iot.riverServer.model.River;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class RiverStorage extends EntityStorage {
    private final HashMap<Long, River> rivers = new HashMap<>();

    public void create(final River river) throws IOException {
        super.create(river);
        rivers.put(idHolder, river);
    }

    public List<River> readAll() {
        return new ArrayList<>(rivers.values());
    }

    public River read(final Long id) {
        return rivers.get(id);
    }

    public Boolean update(final River river, final Long id) throws IOException {
        if (super.update(river, id)) {
            rivers.put(id, river);
            return true;
        }
        return false;
    }

    public Boolean delete(final Long id) throws IOException {
        rivers.remove(id);
        return super.delete(id);
    }

    @Override
    public String getType() {
        return "river";
    }

    @Override
    public void addToHash(final String record) {
        River river = new River(record);
        if (river.getId() > idHolder) {
            idHolder = river.getId();
        }
        rivers.put(river.getId(), river);
    }

}

