package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;

/**
 * Created by AntoineCrettenand on 02/12/2016.
 */
public class Props extends Actor {
    private String name;
    private Box box;

    public Props(String name, Box box) {
        this.name = name;
        this.box = box;
    }

    @Override
    public void draw(Input input, Output output) {
        output.drawSprite(getWorld().getLoader().getSprite(name),getBox());
    }

    @Override
    public int getPriority() {
        return 400;
    }

    @Override
    public Box getBox() {
        return box;
    }
}

