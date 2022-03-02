package swt6.weather.beans;

public interface Sensor {
    void addSensorListener(SensorListener listener);
    void removeSensorListener(SensorListener listener);
}
