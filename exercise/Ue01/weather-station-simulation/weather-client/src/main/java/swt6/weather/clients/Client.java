package swt6.weather.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.weather.sensors.Sensor;
import swt6.weather.sensors.SensorProvider;
import swt6.weather.management.StationFactory;

import java.util.ServiceLoader;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    public static void main(String[] args) {
        System.out.println("=== Weather station started ===");
        var sensor = getBestSensor("temperature");

        try(var station = new StationFactory().getStation(sensor)) {

            System.out.printf("Current value: %.2f, Average: %.2f%n", station.getLatestMeasurement(), station.getAverageMeasurement());

            System.out.println("Wait 6 seconds ...");
            Thread.sleep(6000);
            System.out.printf("Current value: %.2f, Average: %.2f%n", station.getLatestMeasurement(), station.getAverageMeasurement());
        } catch (Exception e) {
            logger.error("Station crashed.", e);
        }
    }

    private static Sensor getBestSensor(String type) {
        ServiceLoader<SensorProvider> serviceLoader = ServiceLoader.load(SensorProvider.class);
        double minResolution = Double.MAX_VALUE;
        SensorProvider minProvider = null;

        for (SensorProvider provider : serviceLoader) {
            if (provider.type().equals(type)
                    && provider.accuracy() < minResolution) {
                minProvider = provider;
                minResolution = minProvider.accuracy();
            }
        }
        var sensor = minProvider == null ? null : minProvider.getSensor();

        if(sensor != null){
            logger.info("Sensor for %s found.".formatted(type));
        } else {
            logger.info("No sensor for %s found.".formatted(type));
        }
        return sensor;
    }
}
