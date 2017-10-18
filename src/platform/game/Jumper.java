package platform.game;

import platform.util.*;

/**
 * A solid actor knocking back other actors on contact
 */

public class Jumper extends Actor {
    private Vector position;
    private double cooldown;
    private final double SIZE_JUMPER = 1;

    public Jumper(Vector position) {
        if (position == null) throw new NullPointerException();
        this.position = position;
    }

    @Override
    public void interact(Actor other) {
        super.interact(other);
        if (cooldown <= 0 && getBox().isColliding(other.getBox()) /*    */) {
            Vector below = new Vector(position.getX(), position.getY() - 1.0);
            if (other.hurt(this, Damage.AIR, 10, below))
                cooldown = 0.5;
        }

    }

    @Override
    public void draw(Input input, Output output) {
        if (cooldown > 0)
            output.drawSprite(getWorld().getLoader().getSprite("jumper.extended"), getBox());
        else output.drawSprite(getWorld().getLoader().getSprite("jumper.normal"), getBox());
    }

    @Override
    public void update(Input input) {
        super.update(input);
        cooldown -= input.getDeltaTime();
    }

    @Override
    public int getPriority() {
        return 54;
    }


    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Box getBox() {
        return new Box(position, SIZE_JUMPER, SIZE_JUMPER);
    }
}
