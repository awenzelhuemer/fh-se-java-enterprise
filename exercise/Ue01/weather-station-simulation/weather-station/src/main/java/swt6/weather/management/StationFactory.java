package swt6.weather.management;

import swt6.weather.sensors.Sensor;
import swt6.weather.management.impl.SimpleStation;

public class StationFactory {

    public Station getStation(Sensor sensor) {
        return new SimpleStation(sensor);
    }
}
