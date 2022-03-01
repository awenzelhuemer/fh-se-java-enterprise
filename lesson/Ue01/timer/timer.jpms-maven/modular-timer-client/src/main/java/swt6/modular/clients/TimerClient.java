package swt6.modular.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import swt6.modular.beans.Timer;
import swt6.modular.beans.TimerFactory;
import swt6.modular.beans.TimerProvider;

import java.util.ServiceLoader;

public class TimerClient {

    private static final Logger logger = LoggerFactory.getLogger(TimerClient.class);

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static void main(String[] args) {
        System.out.println("===== testTimer =====");
        testTimer();

        System.out.println("===== testTimerProvider =====");
        testTimerProvider();

        System.out.println("===== testReflection =====");
        testReflection();
    }

    private static void testTimerProvider() {
        Timer timer = getBestTimer(10, 100);
        if (timer == null) {
            return;
        }
        timer.addTimerListener(event -> logger.info("timer expired: %d/%d%n".formatted(event.getTickCount(), event.getNoTicks())));
        timer.start();
        System.out.println("Timer started.");
        sleep(2000);
        timer.stop();
        System.out.println("Timer stopped.");
        sleep(2000);
        timer.start();
        System.out.println("Timer started.");
    }

    private static void testTimer() {
        Timer timer = TimerFactory.getTimer(10, 500);
        timer.addTimerListener(event -> logger.info("timer expired: %d/%d%n".formatted(event.getTickCount(), event.getNoTicks())));
        timer.start();
        System.out.println("Timer started.");
        sleep(2000);
        timer.stop();
        System.out.println("Timer stopped.");
        sleep(2000);
        timer.start();
        System.out.println("Timer started.");
    }

    private static Timer getBestTimer(int noTicks, int interval) {
        ServiceLoader<TimerProvider> serviceLoader = ServiceLoader.load(TimerProvider.class);
        double minResolution = Double.MAX_VALUE;
        TimerProvider minProvider = null;

        for (TimerProvider provider : serviceLoader) {
            if (provider.timerResolution() < minResolution) {
                minProvider = provider;
                minResolution = minProvider.timerResolution();
            }
        }
        return minProvider == null ? null : minProvider.getTimer(noTicks, interval);
    }

    private static void testReflection() {
        Timer timer = getBestTimer(10, 100);
        if (timer == null) {
            return;
        }

        try {
            System.out.printf("Fields of %s object %n", timer.getClass().getName());
            var fields = timer.getClass().getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);
                Object value = field.get(timer);
                System.out.printf("  %s -> %s%n", field.getName(), value);
            }
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }
}
