package platform.game;

import platform.game.level.Level;
import platform.util.Box;
import platform.util.Loader;
import platform.util.Vector;

/**
 * Represents an environment populated by actors.
 */
public interface World {

    /** @return associated loader, not null */
    public Loader getLoader();

    /**
     * Set viewport location and size.
     * @param center viewport center, not null * @param radius viewport radius, positive */
    public void setView(Vector center, double radius);

    /**
     *  Add/remove an actor to/from the environment
     * @param actor not null
     */
    public void register(Actor actor);
    public void unregister(Actor actor);

    /** @return gravity vector, world specific */
    public Vector getGravity();

    /**
     * Launch attached level
     */
    public void nextLevel();

    /**@param level following level, not null */
    public void setNextLevel(Level level);

    /**
     * Method hurt, applied to an area
     * @param area where the damage is applied, not null
     * @param instigator not null
     * @param type of damage, not null
     * @param amount not null
     * @param location not null
     * @return int victims
     */
    public int hurt(Box area, Actor instigator, Damage type, double amount, Vector location);



}
