package swt6.weather.sensors;

public interface SensorProvider {
    double accuracy();
    String type();
    Sensor getSensor();
}
