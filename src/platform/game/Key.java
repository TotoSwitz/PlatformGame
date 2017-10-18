package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 *
 */
public class Key extends Actor implements Signal {
    private boolean taken;
    private Vector position;
    private final double SIZE_KEYS = 0.75;
    private String color;

    private double scl;
    private boolean up;

    public Key(Vector position, Color color) {
        taken = false;
        this.position = position;
        this.color = color.toString();
        up=false;
        scl=0;
    }

    @Override
    public void interact(Actor other) {
        super.interact(other);
        if (getBox().isColliding(other.getBox())) {
            if(other.hurt(this,Damage.ACTIVATION,0,Vector.ZERO)) {
                taken = true;
            }
        }
    }

    @Override
    public void update(Input input) {
        super.update(input);
        scl+=(up? 1:-1)*input.getDeltaTime();
        if (scl<-0.5) up = true;
        if (scl>0.5) up = false;
    }

    @Override
    public Box getBox() {
        return new Box(position.add(new Vector(0,0.3).mul(scl)), SIZE_KEYS, SIZE_KEYS);
    }

    @Override
    public void draw(Input input, Output output) {
        if (!taken) output.drawSprite(getWorld().getLoader().getSprite("key." + color), getBox());
    }

    @Override
    public int getPriority() {
        return 45;
    }

    @Override
    public boolean isActive() {
        return taken;
    }
}
