module weather.client {
    requires weather.model;
    requires weather.station;
    uses swt6.weather.sensors.SensorProvider;
    requires org.slf4j;
}