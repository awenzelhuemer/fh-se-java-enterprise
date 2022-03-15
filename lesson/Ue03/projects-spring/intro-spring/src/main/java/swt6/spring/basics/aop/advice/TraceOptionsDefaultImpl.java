package swt6.spring.basics.aop.advice;

public class TraceOptionsDefaultImpl implements TraceOptions {

    private boolean isTracingEnabled = false;

    @Override
    public boolean isTracingEnabled() {
        return this.isTracingEnabled;
    }

    @Override
    public void enableTracing() {
        this.isTracingEnabled = true;
    }

    @Override
    public void disableTracing() {
        this.isTracingEnabled = false;
    }
}
