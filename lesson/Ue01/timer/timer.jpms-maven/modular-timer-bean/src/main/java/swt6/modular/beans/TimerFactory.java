package swt6.modular.beans;

import swt6.modular.beans.impl.SimpleTimer;

public class TimerFactory {
    public static Timer getTimer(int noTicks, int interval) {
        return new SimpleTimer(noTicks, interval);
    }
}
