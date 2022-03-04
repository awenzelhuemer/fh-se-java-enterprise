package swt6.weather.sensors;

public interface Sensor extends AutoCloseable {
    void addSensorListener(SensorListener listener);
    void removeSensorListener(SensorListener listener);
    void start();
    void stop();
}
