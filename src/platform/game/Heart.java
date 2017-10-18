package platform.game;

import platform.util.*;

/**
 * Actor that makes Player regain life when taken
 */

public class Heart extends Actor {
    private Box box;
    private final double SIZE_HEART = 0.5;
    private double cooldown;
    private double cooldownMax; //cooldown of respawn of the heart

    public Heart(Vector position, double cooldownMax) {
        if (position == null) throw new NullPointerException();
        box = new Box(position, SIZE_HEART, SIZE_HEART);
        this.cooldownMax = cooldownMax;
    }

    @Override
    public void update(Input input) {
        super.update(input);
        cooldown -= input.getDeltaTime();

    }

    @Override
    public void interact(Actor other) {
        super.interact(other);
        if (getBox().isColliding(other.getBox())) {
            if (other.hurt(this, Damage.HEAL, 1.0, getPosition())) {
                cooldown = cooldownMax;
            }
        }
    }

    @Override
    public void draw(Input input, Output output) {
        super.draw(input, output);
        output.drawSprite(getWorld().getLoader().getSprite("heart.full"), getBox(), 0, (cooldown < 0 ? 1 : 0.2));
    }

    @Override
    public Box getBox() {
        return box;
    }

    @Override
    public int getPriority() {
        return 100;
    }
}
