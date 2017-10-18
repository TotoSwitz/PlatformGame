package platform.game;

/**
 * Simluates logical function NOT
 */
public class Not implements Signal {
    private final Signal signal;

    public Not(Signal signal) {
        if(signal==null)
            throw new NullPointerException();
        this.signal = signal;
    }

    @Override
    public boolean isActive() {
        return !signal.isActive();
    }
}
