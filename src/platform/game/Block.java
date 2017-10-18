package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;

/**
 * Simple solid actor that does nothing.
 */

public class Block extends Actor {
    private String sprite;
    private Box box;

    public Block(Box box, String sprite) {
        if (box == null) throw new NullPointerException();
        this.box = box;
        this.sprite = sprite;
    }


    @Override
    public void update(Input input) {
        super.update(input);
    }


    @Override
    public void draw(Input input, Output output) {
        output.drawSprite(getWorld().getLoader().getSprite(sprite), getBox());
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Box getBox() {
        return box;
    }


}
