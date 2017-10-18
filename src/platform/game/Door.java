package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * Actor solid depending on logical signal
 */

public class Door extends Actor {
    private Vector position;
    private boolean closed;
    private final double SIZE_DOOR = 1;
    private Signal signal;
    private String color;

    public Door(Vector position, Color color, Signal signal) {
        if (position == null) throw new NullPointerException();
        closed = true;
        this.position = position;
        this.color = color.toString();
        this.signal = signal;
    }

    @Override
    public void draw(Input input, Output output) {
        // if !closed, then transparent
        output.drawSprite(getWorld().getLoader().getSprite("Lock." + color), getBox(), 0, (closed ? 1 : 0.25));

    }

    @Override
    public boolean isSolid() {
        return closed;
    }

    @Override
    public Box getBox() {
        return new Box(position, SIZE_DOOR, SIZE_DOOR);
    }

    @Override
    public int getPriority() {
        return 32;
    }

    @Override
    public void update(Input input) {
        closed = (!signal.isActive());
    }
}

