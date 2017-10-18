package platform.game;

/**
 * Simulates logical function OR
 */
public class Or implements Signal {
    private final Signal left;
    private final Signal right;

    public Or(Signal left, Signal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isActive() {
        return left.isActive() || right.isActive();
    }
}
