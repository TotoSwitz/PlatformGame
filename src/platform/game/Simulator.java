package platform.game;

import platform.game.level.Level;
import platform.util.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Basic implementation of world, managing a complete collection of actors.
 */

public class Simulator implements World {
    private Loader loader;

    private Vector currentCenter;
    private double currentRadius;
    private Vector expectedCenter;
    private double expectedRadius;

    private SortedCollection<Actor> actors;
    private List<Actor> registered;
    private List<Actor> unregistered;

    private Level next;
    private boolean transition;

    /**
     * Create a new simulator.
     *
     * @param loader associated loader, not null
     * @param args   level arguments, not null
     */
    public Simulator(Loader loader, String[] args) {
        if (loader == null)
            throw new NullPointerException();
        this.loader = loader;

        currentCenter = Vector.ZERO;
        currentRadius = 1000.0;

        expectedCenter = Vector.ZERO;
        expectedRadius = 1000.0;

        actors = new SortedCollection<>();
        registered = new ArrayList<>();
        unregistered = new ArrayList<>();

        next = Level.createDefaultLevel();
        transition = true;
    }

    @Override
    public void setNextLevel(Level level) {
        next = level;
    }

    @Override
    public void nextLevel() {
        transition = true;
    }

    @Override
    public void setView(Vector center, double radius) {
        if (center == null)
            throw new NullPointerException();
        if (radius <= 0.0)
            throw new IllegalArgumentException("radius must be positive");
        expectedCenter = center;
        expectedRadius = radius;
    }

    /**
     * Simulate a single step of the simulation.
     *
     * @param input  input object to use, not null
     * @param output output object to use, not null
     */
    public void update(Input input, Output output) {


        // Creating View with center and radius
        View view = new View(input, output);

        view.setTarget(currentCenter, currentRadius);

        //Smooth transition of the camera -'- Capping camera's speed
        double factor = 0.05;
        currentCenter = currentCenter.mul(1.0 - factor).add(expectedCenter.mul(factor));
        currentRadius = currentRadius * (1.0 - factor) + expectedRadius * factor;

        // Add registered actors
        for (int i = 0; i < registered.size(); ++i) {
            Actor actor = registered.get(i);
            if (!actors.contains(actor)) {
                actor.register(this);
                actors.add(actor);
            }
        }
        registered.clear();

        // Remove unregistered actors
        for (int i = 0; i < unregistered.size(); ++i) {
            Actor actor = unregistered.get(i);
            actor.unregister();
            actors.remove(actor);
        }
        unregistered.clear();

        // Draw background
        //view.drawSprite(loader.getSprite("bg_grasslands"),new Box(currentCenter,currentRadius*2.75,currentRadius*2.1));

        // Apply preUpdate on actors
        for (Actor a : actors)
            a.preUpdate();

        //Make actors interact in the right order
        for (Actor actor : actors)
            for (Actor other : actors)
                if (actor.getPriority() > other.getPriority())
                    actor.interact(other);


        // Apply update on actors
        for (Actor a : actors)
            a.update(view);


        // Draw everything
        for (Actor a : actors.descending())
            a.draw(view, view);

        // Apply postUpdate on actors
        for (Actor a : actors)
            a.postUpdate();

        // si un acteur a mis transition à true pour demander le passage
        // à un autre niveau :
        if (transition) {
            if (next == null) {
                next = Level.createDefaultLevel();
            }
            // si un acteur a appelé setNextLevel, next ne sera pas null :
            Level level = next;
            transition = false;
            next = null;
            actors.clear();
            registered.clear();
            // tous les anciens acteurs sont désenregistrés
            // y compris le Level précédent : unregistered.clear();
            register(level);
        }
    }

    @Override
    public int hurt(Box area, Actor instigator, Damage type, double amount, Vector location) {
        int victims = 0;
        for (Actor actor : actors)
            if (area.isColliding(actor.getBox()))
                if (actor.hurt(instigator, type, amount, location))
                    ++victims;
        return victims;
    }

    @Override
    public Vector getGravity() {
        return new Vector(0.0, -9.81);
    }

    @Override
    public void register(Actor actor) {
        registered.add(actor);
    }

    @Override
    public void unregister(Actor actor) {
        unregistered.add(actor);
    }

    @Override
    public Loader getLoader() {
        return loader;
    }


}
