package swt6.modular.beans.impl;

import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerProvider;

public class SimpleTimerProvider implements TimerProvider {

    @Override
    public double timerResolution() {
        return 1 / 1000.0;
    }

    @Override
    public Timer getTimer(int noTicks, int interval) {
        return new SimpleTimer(noTicks, interval);
    }
}
