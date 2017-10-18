package platform.game;

import platform.util.Box;
import platform.util.Vector;

/**
 * Area outside of which, all Actors are dealt fatal damage
 */

public class Limits extends Actor{
    private Box box;

    public Limits(Box box) {
        if (box == null) throw new NullPointerException();
        this.box = box;
    }

    @Override
    public int getPriority() {
        return 100000;
    }

    @Override
    public Box getBox() {
        return box;
    }

    @Override
    public void interact(Actor other) {
        if (!(getBox().isColliding(other.getBox()))) {
            if (other.hurt(this, Damage.VOID, Double.POSITIVE_INFINITY, Vector.ZERO)){
            }
        }

    }
}
