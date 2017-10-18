package platform.game.level;

import platform.game.*;
import platform.util.Box;
import platform.util.Vector;

import static platform.game.Color.*;

/**
 * Second level of the game
 */

public class Level2 extends Level {

    @Override
    public void register(World world) {
        super.register(world);
        
        world.setNextLevel(new Level2());

        //Player + Overlay
        Player player = new Player(new Vector(0, 0), 5);
        world.register(player);
        world.register(new Overlay(player));

        //Entites
        world.register(new Jumper(new Vector(-3.5, -6)));
        world.register(new Jumper(new Vector(1, 0)));
        Lever lever1 = new Lever(new Vector(-1.5, 3.8), 10);
        //world.register(lever1);
        Torch torch1 = new Torch(new Vector(0, 1.5), true);
        Torch torch2 = new Torch(new Vector(1, -4), false);
        world.register(torch1);
        world.register(torch2);
        Key keyGreen = new Key(new Vector(-3.5, -0.5), green);
        Key keyRed = new Key(new Vector(6, -1), red);
        world.register(keyRed);
        world.register(keyGreen);

        //Obstacles
        world.register(new Spike(new Vector(2, -1.3)));
        world.register(new Spike(new Vector(3, -1.3)));
        world.register(new Spike(new Vector(-3, 3.75)));
        world.register(new Spike(new Vector(-2, 3.75)));
        world.register(new Spike(new Vector(-1, 3.75)));
        world.register(new Spike(new Vector(-4, 3.75)));

        //Limits
        world.register(new Limits(new Box(new Vector(0, 0), 30, 30)));

        //Blocks
        world.register(new TileMap(new Vector(0, -1), 2, 3));
        world.register(new TileMap(new Vector(1, -2), 3, 2));
        world.register(new TileMap(new Vector(-2, 3), 2, 7));
        world.register(new TileMap(new Vector(-4, 3), 2, 2));

        world.register(new Mover(new Box(new Vector(0, -2), 2, 1), new Vector(1, -7), "stone.2", new Not(torch1)));
        world.register(new Mover(new Box(new Vector(5.5, -7), 2, 1), new Vector(4.5, 0), "stone.2", torch2));

        world.register(new Door(new Vector(2, 3), green, keyGreen));
        world.register(new Door(new Vector(1, 3), green, keyGreen));
        world.register(new Door(new Vector(3, 3), green, keyGreen));
        world.register(new Door(new Vector(0, 3), green, keyGreen));
        world.register(new Door(new Vector(4, 3), green, keyGreen));
        world.register(new Door(new Vector(-4.5, 1), green, keyGreen));
        world.register(new Door(new Vector(0, -4), red, keyRed));
        world.register(new Door(new Vector(0, -5), red, keyRed));

        world.register(new Platform(new Vector(-4, 0), 2, new Tautology()));
        world.register(new Platform(new Vector(-4, -7), 2, new Tautology()));

        world.register(new Exit(new Vector(-3.5, 1), new Level1(), new Tautology()));


    }
}
