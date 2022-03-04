package swt6.weather.management;

public interface Station extends AutoCloseable {
    double getLatestMeasurement();
    double getAverageMeasurement();
    void start();
    void stop();
}
