package swt6.weather.management.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weather.sensors.Measurement;
import swt6.weather.sensors.Sensor;
import swt6.weather.management.Station;

import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleStation implements Station {

    private final Sensor sensor;
    private static final Logger logger = LoggerFactory.getLogger(SimpleStation.class);
    private final CopyOnWriteArrayList<Measurement> measurements = new CopyOnWriteArrayList<>();

    public SimpleStation(Sensor sensor) {
        if(sensor == null){
            throw new IllegalArgumentException("Sensor needs to be set.");
        }

        this.sensor = sensor;
        this.sensor.start();
        registerListener();
    }

    private void registerListener() {
        this.sensor.addSensorListener(event -> {
            logger.info("New measurement: %s".formatted(event.getMeasurement()));
            measurements.add(event.getMeasurement());
        });
    }

    public double getLatestMeasurement() {
        return measurements.isEmpty() ? 0.0 : measurements.get(measurements.size() - 1).getValue();
    }

    public double getAverageMeasurement() {
        return measurements.stream()
                .mapToDouble(Measurement::getValue)
                .average()
                .orElse(0.0);
    }

    @Override
    public void close() {
        sensor.stop();
    }
}
