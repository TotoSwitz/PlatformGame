package platform.game;

import platform.util.*;

/**
 * Base class of all simulated actors, attached to a world.
 */
public abstract class Actor implements Comparable<Actor> {
    private World world;

    /** @return the world attached */
    protected World getWorld() {
        return world;
    }

    /**
     * Update attributes before/after main update
     */
    public void preUpdate() {}
    public void postUpdate() {}

    /**
     * Update the attributes of the Actor depending on inputs
     * @param input object to use, not null
     */
    public void update(Input input) {}

    /**
     * Display the Actor on the screen. By default, every Actor is invisible
     *
     * @param input object to use, not null
     * @param output object to use, not null
     */
    public void draw(Input input, Output output) {}

    /**
     * Simulates interactions between Actors such as dealing damage and getting knocked back
     * @param investigator who is hurting this
     * @param type         of the Damage inflicted
     * @param amount       of Damage dealt. Sometimes irrelevant, for example with VOID and ACTIVATION
     * @param location     where the damage is dealt
     * @return true if conditions for hurting Actor are fullfilled, else return false
     */
    public boolean hurt(Actor investigator, Damage type, double amount, Vector location) {
        return false;
    }

    @Override
    public int compareTo(Actor other) {
        if (getPriority() > other.getPriority()) return -1;
        else if (getPriority() < other.getPriority()) return 1;
        else return 0;
    }

    /**
     * Every interaction with an actor
     *
     * @param other actor, not null
     */
    public void interact(Actor other) {}

    /** @return true if Actor is solid, else return false */
    public boolean isSolid() {
        return false;
    }

    /** @return the area where the Actor is */
    public Box getBox() {
        return null;
    }

    /** @return Vector position of the Actor */
    public Vector getPosition() {
        Box box = getBox();
        if (box == null)
            return null;
        return box.getCenter();
    }

    /**
     * Attach Actor to world
     * @param world in which the Actor has to be registered
     */
    public void register(World world) {
        this.world = world;
    }

    /**
     * Unregister Actor from every world
     */
    public void unregister() {
        world = null;
    }

    /**
     * Priority determines in which order Actors interact and are drawn
     * @return int priority
     */
    public abstract int getPriority();

}