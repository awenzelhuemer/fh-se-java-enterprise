package swt6.modular.beans;

public interface Timer {
    void start();

    void stop();

    void addTimerListener(TimerListener listener);

    void removeTimerListener(TimerListener listener);
}
