package swt6.weather.beans;

import java.util.EventObject;

public class SensorEvent extends EventObject {

    private final Measurement measurement;

    public SensorEvent(Object source, Measurement measurement) {
        super(source);
        this.measurement = measurement;
    }

    public Measurement getMeasurement() {
        return measurement;
    }
}
