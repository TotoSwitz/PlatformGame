package platform.game;

import platform.util.*;

/**
 * A simple Fireball that bounces of solid Actors and expires after a lapse of time
 */

public class Fireball extends Actor {
    private Vector position;
    private Vector velocity;
    private final double SIZE_FIREBALL = 0.3;
    private Actor owner;
    private double lifeTime; //time of existence

    public Fireball(Vector position, Vector velocity, Actor owner, double lifeTime) {
        if (position == null || velocity == null) throw new NullPointerException();
        this.lifeTime = lifeTime;
        this.owner = owner;
        this.position = position;
        this.velocity = velocity;
    }

    @Override
    public void interact(Actor other) {
        super.interact(other);
        if (other.isSolid()) {
            Vector delta = other.getBox().getCollision(position);
            if (delta != null) {
                position = position.add(delta);
                velocity = velocity.mirrored(delta);
            }
        }

        if (other.getBox().isColliding(getBox()) && other != owner) {
            if (other.hurt(this, Damage.FIRE, 1.0, getPosition())) {
                getWorld().unregister(this);
            }
        }
    }

    @Override
    public int getPriority() {
        return 666;
    }

    @Override
    public Box getBox() {
        // position est l'attribut position de l'objet
        return new Box(position, SIZE_FIREBALL, SIZE_FIREBALL);
    }

    @Override
    public void update(Input input) {
        super.update(input);
        double delta = input.getDeltaTime();

        //Fireball learns gravity
        Vector acceleration = getWorld().getGravity();
        velocity = velocity.add(acceleration.mul(delta));
        position = position.add(velocity.mul(delta));

        lifeTime -= delta;
        if (lifeTime < 0) getWorld().unregister(this);

    }

    @Override
    public void draw(Input input, Output output) {
        super.draw(input, output);
        output.drawSprite(getWorld().getLoader().getSprite("fireball"), getBox(), input.getTime());
    }
}
