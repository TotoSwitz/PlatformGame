package platform.game;

import platform.game.level.Level;
import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * Gates connecting Levels between each other
 */

public class Exit extends Actor {
    private Vector position;
    private Level level;
    private Signal signal;
    private final double SIZE_EXIT = 1;

    public Exit(Vector position, Level level, Signal signal) {
        if (position == null || level == null) throw new NullPointerException();
        this.position = position;
        this.level = level;
        this.signal = signal;
    }

    @Override
    public void draw(Input input, Output output) {
        super.draw(input, output);
        String name;
        if (signal.isActive()) name = "door.open";
        else name = "door.closed";
        output.drawSprite(getWorld().getLoader().getSprite(name), getBox());
    }

    @Override
    public boolean hurt(Actor investigator, Damage type, double amount, Vector location) {
        switch (type) {
            case ACTIVATION:
                // If Actor "hurt" the Exit and conditions are fullfilled (signal==true),
                // Player can access next Level
                if (signal.isActive()) {
                    getWorld().setNextLevel(level);
                    getWorld().nextLevel();
                }
                return true;

            default:
                return false;
        }
    }

    @Override
    public Box getBox() {
        return new Box(position, SIZE_EXIT, SIZE_EXIT);
    }

    @Override
    public int getPriority() {
        return 35;
    }


}
