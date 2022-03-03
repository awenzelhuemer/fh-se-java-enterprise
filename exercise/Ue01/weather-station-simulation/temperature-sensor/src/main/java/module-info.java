module temperature.sensor {
    requires weather.model;
    requires org.slf4j;
    provides swt6.weather.sensors.SensorProvider with swt6.weather.sensors.impl.TemperatureSensorProvider;
}