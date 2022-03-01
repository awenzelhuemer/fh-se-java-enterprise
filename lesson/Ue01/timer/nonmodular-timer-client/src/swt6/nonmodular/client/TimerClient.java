package swt6.nonmodular.client;

import swt6.nonmodular.beans.SimpleTimer;

public class TimerClient {

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        SimpleTimer timer = new SimpleTimer(10, 500);
        timer.addTimerListener(event -> System.out.printf("timer expired: %d/%d%n", event.getTickCount(), event.getNoTicks()));
        timer.start();
        System.out.println("Timer started.");
        sleep(2000);
        timer.stop();
        System.out.println("Timer stopped.");
        sleep(2000);
        timer.start();
        System.out.println("Timer started.");
    }
}
