package swt6.modular.beans;

import java.util.EventObject;

public class TimerEvent extends EventObject {

    protected int noTicks = 0;
    protected int tickCount = 0;

    public TimerEvent(Object source, int noTicks, int tickCount) {
        super(source);
        this.noTicks = noTicks;
        this.tickCount = tickCount;
    }

    public int getNoTicks() {
        return noTicks;
    }

    public int getTickCount() {
        return tickCount;
    }
}
