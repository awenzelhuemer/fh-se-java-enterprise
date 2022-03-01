package swt6.nonmodular.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SimpleTimer {

    private int noTicks = 1;
    private int tickInterval = 100;
    private final AtomicBoolean stopTimer = new AtomicBoolean(false);
    private final AtomicReference<Thread> tickerThread = new AtomicReference<>(null);

    private final CopyOnWriteArrayList<TimerListener> listeners = new CopyOnWriteArrayList<>();

    public SimpleTimer(int noTicks, int tickInterval) {
        this.noTicks = noTicks;
        this.tickInterval = tickInterval;
    }

    public void start() {
        if (isRunning()) {
            throw new IllegalArgumentException("Cannot start: Timer is already running.");
        }
        int interval = getInterval();
        int noTicks = getNoTicks();

        tickerThread.set(new Thread(() -> {
            int tickCount = 0;
            while (!stopTimer.get() && tickCount < noTicks) {
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException ignored) {
                }
                if (!stopTimer.get()) {
                    tickCount++;
                    fireEvent(new TimerEvent(SimpleTimer.this, noTicks, tickCount));
                }
            }

            stopTimer.set(false);
            tickerThread.set(null);

        }));

        tickerThread.get().start();
    }

    public void stop() {
        this.stopTimer.set(true);
    }

    private void fireEvent(TimerEvent event) {
        listeners.forEach(l -> l.expired(event));
    }

    public boolean isRunning() {
        return tickerThread.get() != null;
    }

    public int getInterval() {
        return tickInterval;
    }

    public void setInterval(int tickInterval) {
        this.tickInterval = tickInterval;
    }

    public int getNoTicks() {
        return noTicks;
    }

    private void setNoTicks(int noTicks) {
        this.noTicks = noTicks;
    }

    public void addTimerListener(TimerListener listener) {
        listeners.add(listener);
    }

    public void removeTimerListener(TimerListener listener) {
        listeners.remove(listener);
    }

}