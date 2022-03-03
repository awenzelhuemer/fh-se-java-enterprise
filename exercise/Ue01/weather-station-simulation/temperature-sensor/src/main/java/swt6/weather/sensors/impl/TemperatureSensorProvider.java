package swt6.weather.sensors.impl;

import swt6.weather.sensors.Sensor;
import swt6.weather.sensors.SensorProvider;

public class TemperatureSensorProvider implements SensorProvider {

    @Override
    public double accuracy() {
        return 0.994;
    }

    @Override
    public String type() {
        return "temperature";
    }

    @Override
    public Sensor getSensor() {
        return new TemperatureSensor();
    }
}
