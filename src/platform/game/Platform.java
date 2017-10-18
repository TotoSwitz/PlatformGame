package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 *
 */

public class Platform extends Actor {
    private Vector position;
    private int width;
    private Signal signal;

    public Platform(Vector position, int width, Signal signal) {
        if (position == null || width == 0) throw new NullPointerException();
        this.position = position;
        this.width = width;
        this.signal = signal;
    }

    @Override
    public Box getBox() {
        return new Box(position.add(new Vector(0.5 * (width - 1), 0.20)), width, 0.55);
    }

    @Override
    public void draw(Input input, Output output) {
        if (signal.isActive()) {
            String sprite;
            for (int i = 0; i < width; i++) {
                if (i == 0) sprite = "chocoHalfLeft";
                else if (i == width - 1) sprite = "chocoHalfRight";
                else sprite = "chocoHalfMid";
                output.drawSprite(getWorld().getLoader().getSprite(sprite), new Box(position.add(new Vector(i, 0)), 1, 1));
            }
        }
    }

    @Override
    public boolean isSolid() {
        return signal.isActive();
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
