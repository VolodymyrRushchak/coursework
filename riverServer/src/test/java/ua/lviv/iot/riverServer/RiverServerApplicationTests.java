package ua.lviv.iot.riverServer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.lviv.iot.riverServer.dataaccess.file.CSVFileStorage;
import ua.lviv.iot.riverServer.logic.MeasurementService;
import ua.lviv.iot.riverServer.logic.MeasurementStationService;
import ua.lviv.iot.riverServer.logic.RiverService;
import ua.lviv.iot.riverServer.model.Measurement;
import ua.lviv.iot.riverServer.model.MeasurementStation;
import ua.lviv.iot.riverServer.model.River;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class RiverServerApplicationTests {

    @Autowired
    private RiverService riverService;
    @Autowired
    private MeasurementStationService measurementStationService;
    @Autowired
    private MeasurementService measurementService;
    
    @Test
    void testWritingToCsvNoElementsCase() throws IOException {
        Path path = Path.of("test-" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".csv");
        new CSVFileStorage().writeToFile("", "", "test");
        Assertions.assertTrue(Files.exists(path));
        Assertions.assertEquals("\r\n\r\n", Files.readString(path));
        Files.delete(path);
    }

    @Test
    void testWritingToCsvOneElementCase() throws IOException {
        Path path = Path.of("test-" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".csv");
        new CSVFileStorage().writeToFile("firstField,secondField", "Dnipro,2201", "test");
        Assertions.assertTrue(Files.exists(path));
        Assertions.assertEquals("firstField,secondField\r\nDnipro,2201\r\n", Files.readString(path));
        Files.delete(path);
    }

    @Test
    void testWritingToCsvRegularCase() throws IOException {
        Path path = Path.of("test-" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) + ".csv");
        new CSVFileStorage().writeToFile("firstField,secondField", "Dnipro,2201", "test");
        new CSVFileStorage().writeToFile("firstField,secondField", "Dnister,1362", "test");
        Assertions.assertTrue(Files.exists(path));
        Assertions.assertEquals("firstField,secondField\r\nDnipro,2201\r\nDnister,1362\r\n", Files.readString(path));
        Files.delete(path);
    }

    @Test
    void testReadingRiverFromCSV() throws IOException {
        River river = new River();
        river.setName("Dnipro");
        river.setLength(2201.0);
        river.setFlowRate(134.7);
        river.setBasinArea(14688.2);
        riverService.create(river);
        River[] rivers = riverService.readAll().toArray(River[]::new);
        Long id = rivers[rivers.length - 1].getId();
        Assertions.assertEquals("Dnipro", riverService.read(id).getName());
        Assertions.assertEquals(2201.0, riverService.read(id).getLength());
        Assertions.assertEquals(134.7, riverService.read(id).getFlowRate());
        Assertions.assertEquals(14688.2, riverService.read(id).getBasinArea());
        Assertions.assertEquals(true, riverService.delete(id));
    }

    @Test
    void testUpdatingRiverInCSV() throws IOException {
        River river = new River();
        river.setName("Dnipro");
        river.setLength(2201.0);
        river.setFlowRate(134.7);
        river.setBasinArea(14688.2);
        riverService.create(river);
        River[] rivers = riverService.readAll().toArray(River[]::new);
        Long id = rivers[rivers.length - 1].getId();
        riverService.update(new River(null, "Dnister", 1362.9, 70.7, 1225.0), id);
        Assertions.assertEquals("Dnister", riverService.read(id).getName());
        Assertions.assertEquals(1362.9, riverService.read(id).getLength());
        Assertions.assertEquals(70.7, riverService.read(id).getFlowRate());
        Assertions.assertEquals(1225.0, riverService.read(id).getBasinArea());
        Assertions.assertEquals(true, riverService.delete(id));
    }

    @Test
    void testReadingMeasurementStationFromCSV() throws IOException {
        MeasurementStation measurementStation = new MeasurementStation();
        measurementStation.setName("Dnipro");
        measurementStation.setGpsCoordinates("-49.267136;50.123567");
        measurementStation.setRiverId(1L);
        measurementStationService.create(measurementStation);
        MeasurementStation[] measurementStations =measurementStationService.readAll().toArray(MeasurementStation[]::new);
        Long id = measurementStations[measurementStations.length - 1].getId();
        Assertions.assertEquals("Dnipro", measurementStationService.read(id).getName());
        Assertions.assertEquals("-49.267136;50.123567", measurementStationService.read(id).getGpsCoordinates());
        Assertions.assertEquals(1L, measurementStationService.read(id).getRiverId());
        Assertions.assertEquals(true, measurementStationService.delete(id));
    }

    @Test
    void testUpdatingMeasurementStationInCSV() throws IOException {
        MeasurementStation measurementStation = new MeasurementStation();
        measurementStation.setName("Dnipro");
        measurementStation.setGpsCoordinates("-49.267136;50.123567");
        measurementStation.setRiverId(1L);
        measurementStationService.create(measurementStation);
        MeasurementStation[] measurementStations =measurementStationService.readAll().toArray(MeasurementStation[]::new);
        Long id = measurementStations[measurementStations.length - 1].getId();
        measurementStationService.update(new MeasurementStation(null, "Dnister", "-49.237136;49.073567", 2L), id);
        Assertions.assertEquals("Dnister", measurementStationService.read(id).getName());
        Assertions.assertEquals("-49.237136;49.073567", measurementStationService.read(id).getGpsCoordinates());
        Assertions.assertEquals(2L, measurementStationService.read(id).getRiverId());
        Assertions.assertEquals(true, measurementStationService.delete(id));
    }

    @Test
    void testReadingMeasurementFromCSV() throws IOException {
        Measurement measurement = new Measurement();
        measurement.setDate("2022-06-18");
        measurement.setWaterLevel(15.5);
        measurement.setStationName("Kyiv");
        measurementService.create(measurement);
        Measurement[] measurements =measurementService.readAll().toArray(Measurement[]::new);
        Long id = measurements[measurements.length - 1].getId();
        Assertions.assertEquals("2022-06-18", measurementService.read(id).getDate());
        Assertions.assertEquals(15.5, measurementService.read(id).getWaterLevel());
        Assertions.assertEquals("Kyiv", measurementService.read(id).getStationName());
        Assertions.assertEquals(true, measurementService.delete(id));
    }

    @Test
    void testUpdatingMeasurementInCSV() throws IOException {
        Measurement measurement = new Measurement();
        measurement.setDate("2022-06-18");
        measurement.setWaterLevel(15.5);
        measurement.setStationName("Kyiv");
        measurementService.create(measurement);
        Measurement[] measurements = measurementService.readAll().toArray(Measurement[]::new);
        Long id = measurements[measurements.length - 1].getId();
        measurementService.update(new Measurement(null, "2022-06-17", 16.7, "Dnister", 5L), id);
        Assertions.assertEquals("2022-06-17", measurementService.read(id).getDate());
        Assertions.assertEquals(16.7, measurementService.read(id).getWaterLevel());
        Assertions.assertEquals("Dnister", measurementService.read(id).getStationName());
        Assertions.assertEquals(5L, measurementService.read(id).getStationId());
        Assertions.assertEquals(true, measurementService.delete(id));
    }

}
