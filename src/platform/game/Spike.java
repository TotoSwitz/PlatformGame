package platform.game;

import platform.util.*;

/**
 * Solid actor
 */

public class Spike extends Actor {
    private Vector position;
    private final double SIZE_SPIKE=1;

    public Spike(Vector position) {
        this.position=position;
    }

    @Override
    public void interact(Actor other) {
        if (other.getBox().isColliding(getBox())) {
            if (other.hurt(this, Damage.VOID, 1.0, Vector.ZERO)) {
            }
        }
    }

    @Override
    public Box getBox() {
        return new Box(position,SIZE_SPIKE,SIZE_SPIKE/2);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void draw(Input input, Output output) {
        output.drawSprite(getWorld().getLoader().getSprite("spikes"), getBox());
    }

    @Override
    public int getPriority() {
        return 150;
    }
}
