package platform.game;

import platform.util.*;

/**
 *
 */

public class Overlay extends Actor {
    private Player player;
    private Box box;

    public Overlay(Player player) {
        box = player.getBox();
        this.player = player;
    }

    @Override
    public void draw(Input input, Output output) {
        double health = 5.0 * player.getHealth() / player.getMaxHealth();
        for (int i = 1; i <= 5; ++i) {
            String name;
            if (health >= i)
                name = "heart.full";
            else if (health >= i - 0.5) name = "heart.half";
            else
                name = "heart.empty";

            // trouver le Sprite associé à name
            // dessiner ce Sprite en desssus de Player.
            output.drawSprite(getWorld().getLoader().getSprite(name),
                    new Box(player.getPosition().add(new Vector(-0.60 + i / 5.0, 0.45)), 0.18, 0.18));
        }

    }

    @Override
    public Box getBox() {
        return box;
    }

    @Override
    public int getPriority() {
        return 200;
    }
}
