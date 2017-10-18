package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * A non-solid Actor, used for decoration or as a logical signal
 */
public class Torch extends Actor implements Signal {

    private Vector position;
    private boolean lit;
    private final double SIZE_TORCH = 0.8;

    private double variation;

    public Torch(Vector position, boolean lit) {
        if (position == null) throw new NullPointerException();
        this.position = position;
        variation = 0;
        this.lit = lit;
    }

    @Override
    public void update(Input input) {
        super.update(input);
        variation -= input.getDeltaTime();
        if (variation < 0.0)
            variation = 0.6;

    }

    @Override
    public void draw(Input input, Output output) {
        String name ="torch";
        if (lit) {
            name = "torch.lit.1";
            if (variation < 0.3)
                name = "torch.lit.2";
        }
        output.drawSprite(getWorld().getLoader().getSprite(name), getBox());
    }

    @Override
    public boolean hurt(Actor investigator, Damage type, double amount, Vector location) {
        switch(type) {

            case FIRE:
                if (!lit){
                    lit=true;
                    return true;
                }
                else return false;

            case AIR:
                    lit=false;
                return true;

            default:
            return super.hurt(investigator, type, amount, location);
        }
    }

    @Override
    public Box getBox() {
        return new Box(position, SIZE_TORCH, SIZE_TORCH);
    }

    @Override
    public int getPriority() {
        return 50;
    }


    @Override
    public boolean isActive() {
        return lit;
    }
}
