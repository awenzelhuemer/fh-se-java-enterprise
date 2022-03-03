package swt6.weather.sensors.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weather.sensors.Measurement;
import swt6.weather.sensors.Sensor;
import swt6.weather.sensors.SensorEvent;
import swt6.weather.sensors.SensorListener;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class TemperatureSensor implements Sensor {

    private final CopyOnWriteArrayList<SensorListener> listeners = new CopyOnWriteArrayList<>();
    private final AtomicReference<Timer> timer = new AtomicReference<>();
    private static final Logger logger = LoggerFactory.getLogger(TemperatureSensor.class);
    private final Random random = new Random();
    private static final int INTERVAL = 1500;

    public TemperatureSensor() {
        start();
    }

    private void start() {
        logger.info("Sensor measurement started.");
        timer.set(new Timer());
        timer.get().schedule(new TimerTask() {
            @Override
            public void run() {
                double randomValue = random.nextDouble(100.0) - 50.0;
                fireEvent(new SensorEvent(TemperatureSensor.this, new Measurement(randomValue, "°C", LocalDateTime.now())));
            }
        }, 0, INTERVAL);
    }

    private void fireEvent(SensorEvent event) {
        listeners.forEach(l -> l.measurement(event));
    }

    @Override
    public void addSensorListener(SensorListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeSensorListener(SensorListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void close() {
        logger.info("Sensor measurement stopped.");
        listeners.clear();
        timer.get().cancel();
    }
}
