package platform.game;

import platform.util.Box;
import platform.util.Input;
import platform.util.Output;
import platform.util.Vector;

/**
 * Grid of solid actors
 */

public class TileMap extends Actor {
    private Vector position;
    private int width;
    private int height;


    public TileMap(Vector position, int width, int height) {
        if (position == null || width == 0 || height == 0) throw new NullPointerException();

        this.position = position; // Vector position is located on far up-left corner of the grid
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public Box getBox() {
        // center of the box is the center of the grid (=not the vector position)
        return new Box(position.add(new Vector(0.5 * (width - 1), 0)).sub(new Vector(0, 0.5 * (height - 1))), width, height);
    }

    @Override
    public void draw(Input input, Output output) {
        // Grid is drawn from top-left corner to bottom right corner, columns first
        String sprite;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (j == 0) sprite = "choco";
                else if (i == 0 && j == height - 1) sprite = "choco.BottomLeft";
                else if (i == width - 1 && j == height - 1) sprite = "choco.BottomRight";
                else sprite = "choco.Center";
                output.drawSprite(getWorld().getLoader().getSprite(sprite), new Box(position.add(new Vector(i, 0).sub(new Vector(0, j))), 1, 1));
            }
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
