module temperature.sensor {
    exports swt6.weather.sensors.impl;
    requires weather.model;
    requires org.slf4j;
    provides swt6.weather.sensors.SensorProvider with swt6.weather.sensors.impl.TemperatureSensorProvider;
}