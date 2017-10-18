package platform.game;

/**
 * Always true signal
 */
public class Tautology implements Signal {

    @Override
    public boolean isActive() {
        return true;
    }
}
