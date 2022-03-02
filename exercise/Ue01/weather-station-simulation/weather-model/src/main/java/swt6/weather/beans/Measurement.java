package swt6.weather.beans;

import java.time.LocalDateTime;

public class Measurement {

    private int value;

    private String unit;

    public Measurement(int value, String unit, LocalDateTime timestamp) {
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    private LocalDateTime timestamp;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
