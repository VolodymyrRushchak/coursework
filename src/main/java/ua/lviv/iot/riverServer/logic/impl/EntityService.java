package ua.lviv.iot.riverServer.logic.impl;

import ua.lviv.iot.riverServer.dataaccess.file.EntityStorage;

import java.io.File;

public abstract class EntityService {

    protected void init() {
        String[] directoryElements = getDirs();
        String directory = String.join(File.separator, directoryElements);
        EntityStorage storage = getStorage();
        storage.setWorkingDirectory(directory);
        storage.initHashMap();
    }

    protected abstract String[] getDirs();

    protected abstract EntityStorage getStorage();

}
