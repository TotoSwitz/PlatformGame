package platform.game.level;


import platform.game.*;
import platform.util.Box;
import platform.util.Vector;

import static platform.game.Color.green;

public class BasicLevel extends Level {

    @Override
    public void register(World world) {
        super.register(world);

        // Register a new instance, to restart level automatically
        world.setNextLevel(new BasicLevel());

        // Create blocks
        world.register(new Block(new Box(new Vector(0, 5), 4, 2), "stone.broken.2"));
        world.register(new Block(new Box(new Vector(-1.5, 6.5), 1, 1), "stone.broken.1"));
        world.register(new Block(new Box(new Vector(-4, 5), 1, 4), "stone.broken.7"));
        world.register(new Block(new Box(new Vector(-7,5), 4, 2), "stone.broken.2"));

        world.register(new Jumper(new Vector(1, 6.5)));
        world.register(new Limits(new Box(Vector.ZERO, 20, 30)));

        Player player = new Player(new Vector(-1, 8), 3);
        world.register(player);
        world.register(new Overlay(player));

        /*
        world.register(new Fireball(new Vector(0,10),Vector.ZERO,new Block(new Box(new Vector(0, 5), 4, 2), "stone.broken.2")));
        world.register(new Fireball(new Vector(-1.75,10),Vector.ZERO,new Block(new Box(new Vector(0, 5), 4, 2), "stone.broken.2")));
        world.register(new Fireball(new Vector(-4,10),Vector.ZERO,new Block(new Box(new Vector(0, 5), 4, 2), "stone.broken.2")));
        */

        world.register(new Spike(new Vector(5,5)));
     //   world.register(new Heart(new Vector(-2,7)));
        Torch torch=new Torch(new Vector(0,7), true);
        world.register(torch);
     //   world.register(new Background(player,world));


        Key key=new Key(new Vector(-2,8),green);
        world.register(key);
        world.register(new Door(new Vector(-5,8),green,key));
        Lever lever=new Lever(new Vector(-1,8),7);
        world.register(lever);
        //world.register(new Door(new Vector(2,8),"blue",lever));

        Mover mover=new Mover(new Box(new Vector(3,5),2,1), new Vector(3,8),"box.double", lever);
        world.register(mover);

        world.register(new Exit(new Vector(-7, 7), new Level1(), torch));

    }

}
