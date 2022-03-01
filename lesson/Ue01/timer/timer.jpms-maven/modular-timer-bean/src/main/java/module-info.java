import swt6.modular.beans.TimerProvider;
import swt6.modular.beans.impl.SimpleTimerProvider;

module swt.modular.beans {
    exports swt6.modular.beans;
    provides TimerProvider with SimpleTimerProvider;
    opens swt6.modular.beans.impl;
}