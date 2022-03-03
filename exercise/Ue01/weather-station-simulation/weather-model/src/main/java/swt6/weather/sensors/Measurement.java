package swt6.weather.sensors;

import java.time.LocalDateTime;

public class Measurement {

    private final double value;

    private final String unit;

    private final LocalDateTime timestamp;

    public Measurement(double value, String unit, LocalDateTime timestamp) {
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "%.2f %s (%s)".formatted(value, unit, timestamp);
    }
}
