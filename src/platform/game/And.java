package platform.game;

/**
 * Simulates logical function AND
 */

public class And implements Signal {
    private final Signal left;
    private final Signal right;

    public And(Signal left, Signal right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean isActive() {
        return left.isActive() && right.isActive();
    }
}
