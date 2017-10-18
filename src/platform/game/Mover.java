package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Vector;

/**
 * A solid Actor moving, depending on a signal
 */

public class Mover extends Block {
    private Vector on;
    private Vector off;
    private Signal signal;
    private double current;

    public Mover(Box box, Vector on, String sprite, Signal signal) {
        super(box, sprite);
        if (box == null || on == null) throw new NullPointerException();
        off = box.getCenter();
        this.signal = signal;
        this.on = on;
    }

    @Override
    public void update(Input input) {
        super.update(input);

        if (signal.isActive()) {
            current += input.getDeltaTime() / 2;
            if (current > 1.0)
                current = 1.0;
        } else {
            current -= input.getDeltaTime() / 2;
            if (current < 0)
                current = 0;
        }
    }

    @Override
    public Box getBox() {
        return new Box(off.add(on.sub(off).mul(current)), super.getBox().getWidth(), super.getBox().getHeight());
    }
}
