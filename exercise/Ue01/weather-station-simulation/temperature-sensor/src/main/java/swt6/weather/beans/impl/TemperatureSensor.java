package swt6.weather.beans.impl;

import swt6.weather.beans.Measurement;
import swt6.weather.beans.Sensor;
import swt6.weather.beans.SensorEvent;
import swt6.weather.beans.SensorListener;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public class TemperatureSensor implements Sensor {

    private final CopyOnWriteArrayList<SensorListener> listeners = new CopyOnWriteArrayList<>();

    public TemperatureSensor() {
        startMeasurement();
    }

    private void startMeasurement() {
        new Thread(() -> {
            while (true) {
                try {
                    // TODO random values
                    fireEvent(new SensorEvent(TemperatureSensor.this, new Measurement(20, "∘C", LocalDateTime.now())));
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Log
                    e.printStackTrace();
                }
            }
        });
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
}
