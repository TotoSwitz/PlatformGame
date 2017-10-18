package platform.game.level;

import platform.game.*;
import platform.util.Box;
import platform.util.Vector;

import static platform.game.Color.*;

/**
 * First Level of the game
 */

public class Level1 extends Level {

    @Override
    public void register(World world) {
        super.register(world);
        
        world.setNextLevel(new Level1());

        //Player
        Player player = new Player(new Vector(1, 1), 5);
        world.register(player);
        world.register(new Overlay(player));

        //Exit and Signals
        Torch torch1 = new Torch(new Vector(-9, 4), false);
        Torch torch2 = new Torch(new Vector(-5, 4), false);
        Torch torch3 = new Torch(new Vector(-9, 2), true);
        Torch torch4 = new Torch(new Vector(-5, 2), false);
        Torch torch5 = new Torch(new Vector(-0.25, 5), false);
        Torch torch6 = new Torch(new Vector(8.25, 5), false);
        world.register(torch1);
        world.register(torch2);
        world.register(torch3);
        world.register(torch4);
        world.register(torch5);
        world.register(torch6);
        world.register(new Exit(new Vector(-7, 3), new Level2(),
                new And(new And(new Not(torch1), torch2),
                        new And(new Not(torch3), new Not(torch4)))));

        //Entities
        Lever lever1 = new Lever(new Vector(3, 2.8),9);
        world.register(lever1);

        world.register(new Heart(new Vector(13,9),10));

        world.register(new Fireball(new Vector(12,2.5),new Vector(0,0),new BasicLevel(),Double.MAX_VALUE));
        world.register(new Fireball(new Vector(14,2.5),new Vector(0,0),new BasicLevel(),Double.MAX_VALUE));

        Key keyBlue = new Key(new Vector(-6, 9), blue);
        world.register(keyBlue);
        Key keyRed = new Key(new Vector(13, 0), red);
        world.register(keyRed);
        Key keyGreen = new Key(new Vector(12,9), green);
        world.register(keyGreen);

        //Limits
        world.register(new Limits(new Box(new Vector(0, 0), 60, 30)));

        //Blocks
        world.register(new TileMap(new Vector(-10, 0), 13, 3));
        world.register(new TileMap(new Vector(3, -1), 6, 2));
        world.register(new TileMap(new Vector(11, -1), 4, 2));
        world.register(new TileMap(new Vector(-3, 8), 3, 7));
        world.register(new TileMap(new Vector(-10, 8), 7, 2));
        world.register(new TileMap(new Vector(11, 8), 4, 2));
        world.register(new TileMap(new Vector(9, 9), 2, 8));
        world.register(new TileMap(new Vector(-12, 8), 2, 11));

        world.register(new Door(new Vector(-1, 1), blue, keyBlue));
        world.register(new Door(new Vector(-2, 1), red, keyRed));
        world.register(new Door(new Vector(-3, 1), green, keyGreen));

        world.register(new Door(new Vector(-1, 9), green, keyGreen));
        world.register(new Door(new Vector(-1, 10), green, keyGreen));
        world.register(new Door(new Vector(-1, 11), green, keyGreen));

        world.register(new Mover(new Box(new Vector(9.5, 0.5), 2, 2), new Vector(9.5, -1.5), "stone.broken.4", lever1));
        world.register(new Mover(new Box(new Vector(15.5, -3.5), 2, 2), new Vector(15.5, 7.5), "stone.broken.4", keyRed));
        world.register(new Mover(new Box(new Vector(-2,10),1,3),new Vector(-2,13),"stone.broken.8", lever1));

        //Platforms
        world.register(new Platform(new Vector(2.5, 2), 2, new Tautology()));
        world.register(new Platform(new Vector(5, 4), 2, new And(new And(torch5, torch6),keyRed)));
        world.register(new Platform(new Vector(1, 6), 2, new And(new And(torch5, torch6),keyRed)));
        world.register(new Platform(new Vector(5, 8), 2, new And(new And(torch5, torch6),keyRed)));
        world.register(new Platform(new Vector(-8, 2), 3, new Tautology()));

        //Obstacles
        world.register(new Spike(new Vector(3, -0.25)));
        world.register(new Spike(new Vector(4, -0.25)));
        world.register(new Spike(new Vector(5, -0.25)));
        world.register(new Spike(new Vector(6, -0.25)));
        world.register(new Spike(new Vector(7, -0.25)));
        world.register(new Spike(new Vector(8, -0.25)));

    }
}
