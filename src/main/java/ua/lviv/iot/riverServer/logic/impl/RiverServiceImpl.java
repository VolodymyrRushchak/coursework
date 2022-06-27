package ua.lviv.iot.riverServer.logic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.riverServer.dataaccess.file.EntityStorage;
import ua.lviv.iot.riverServer.dataaccess.file.RiverStorage;
import ua.lviv.iot.riverServer.logic.RiverService;
import ua.lviv.iot.riverServer.model.River;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Service
public class RiverServiceImpl extends EntityService implements RiverService {

    @Autowired
    private RiverStorage riverStorage;

    @PostConstruct
    protected void init() {
        super.init();
    }

    @Override
    protected String[] getDirs() {
        return new String[]{"csvfiles", "rivers"};
    }

    @Override
    protected EntityStorage getStorage() {
        return riverStorage;
    }

    public void create(final River river) throws IOException {
        riverStorage.create(river);
    }

    public List<River> readAll() {
        return riverStorage.readAll();
    }

    public River read(final Long id) {
        return riverStorage.read(id);
    }

    public Boolean update(final River river, final Long id) throws IOException {
        return riverStorage.update(river, id);
    }

    public Boolean delete(final Long id) throws IOException {
        return riverStorage.delete(id);
    }

}