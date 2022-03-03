module weather.station {
    requires weather.model;
    requires temperature.sensor;
    requires org.slf4j;
    exports swt6.weather.management;
}