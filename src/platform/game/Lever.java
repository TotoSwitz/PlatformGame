package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * A non-solid actor used as a logical signal
 */
public class Lever extends Actor implements Signal {
    private Vector position;
    private boolean on;
    private double cooldown;
    private double cooldownMax;
    private final double SIZE_LEVER = 0.75;

    public Lever(Vector position, double cooldownMax) {
        on = false;
        this.position = position;
        this.cooldownMax = cooldownMax;
    }

    @Override
    public void update(Input input) {
        super.update(input);
        cooldown -= input.getDeltaTime();
        if (cooldown < 0) {
            on = false;
        }
    }

    @Override
    public void draw(Input input, Output output) {
        String name;
        if (on) name = "lever.right";
        else name = "lever.left";
        output.drawSprite(getWorld().getLoader().getSprite(name), getBox());
    }

    @Override
    public boolean hurt(Actor investigator, Damage type, double amount, Vector location) {
        switch (type) {
            case ACTIVATION:
                cooldown = cooldownMax;
                on = !on;
                return true;

            default:
                return super.hurt(investigator, type, amount, location);
        }
    }

    @Override
    public Box getBox() {
        return new Box((position), SIZE_LEVER, SIZE_LEVER);
    }

    @Override
    public boolean isActive() {
        return on;
    }

    @Override
    public int getPriority() {
        return 33;
    }
}
