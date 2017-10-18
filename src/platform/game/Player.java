package platform.game;

import platform.util.*;

import java.awt.event.KeyEvent;

/**
 * Controlled Actor
 */

public class Player extends Actor {
    private Vector position;
    private Vector velocity;
    private boolean onGround;
    private boolean againstWall;
    private final double SIZE_PLAYER = 0.6;

    private double health;
    private double maxHealth;

    public Player(Vector position, double maxHealth) {
        if (position == null) throw new NullPointerException();
        this.position = position;
        velocity = Vector.ZERO;
        if (maxHealth < 1) throw new IllegalArgumentException("maxHealth must be > 0");
        this.maxHealth = maxHealth;
        health = maxHealth;

    }

    @Override
    public void preUpdate() {
        onGround = false;
        againstWall = false;
    }

    @Override
    public void postUpdate() {
        super.postUpdate();
        getWorld().setView(getPosition(), 7);
        if (health > maxHealth) health = maxHealth;
        if (health <= 0) this.gameOver();
    }

    @Override
    public void update(Input input) {
        super.update(input);


        if (againstWall) {//Coefficient de frottement sur les murs il est Override sur le sol par ...isReleased
            double scale = Math.pow(0.05, input.getDeltaTime());
            velocity = new Vector(velocity.getX(), velocity.getY() * scale);
        }

        double maxSpeed = 6.0;
        if (input.getKeyboardButton(KeyEvent.VK_RIGHT).isDown()) {
            if (velocity.getX() < maxSpeed) {
                double increase = 60.0 * input.getDeltaTime();
                double speed = velocity.getX() + increase;
                if (speed > maxSpeed)
                    speed = maxSpeed;
                velocity = new Vector(speed, velocity.getY());
            }
        }

        if (input.getKeyboardButton(KeyEvent.VK_LEFT).isDown()) {
            if (velocity.getX() > -maxSpeed) {
                double decrease = -(60.0 * input.getDeltaTime());
                double speed = velocity.getX() + decrease;
                if (speed < -maxSpeed)
                    speed = -maxSpeed;
                velocity = new Vector(speed, velocity.getY());

            }
        }

        if (input.getKeyboardButton(KeyEvent.VK_RIGHT).isReleased() || input.getKeyboardButton(KeyEvent.VK_LEFT).isReleased()) {
            velocity = new Vector(0, velocity.getY());
        }

        if (input.getKeyboardButton(KeyEvent.VK_UP).isPressed() && onGround) {
            velocity = new Vector(velocity.getX(), 7);
        }

        if (input.getKeyboardButton(KeyEvent.VK_SPACE).isPressed()) {
            Fireball ball1 = new Fireball(getPosition(), velocity.add(velocity.resized(3.0)), this, 1.0);
            getWorld().register(ball1);

        }

        if (input.getKeyboardButton(KeyEvent.VK_E).isPressed())
            getWorld().hurt(getBox(), this, Damage.ACTIVATION, 1.0, getPosition());


        if (input.getKeyboardButton(KeyEvent.VK_B).isPressed())
            getWorld().hurt(getBox(), this, Damage.AIR, 1.0, getPosition());


        double delta = input.getDeltaTime();

        Vector acceleration = getWorld().getGravity();
        velocity = velocity.add(acceleration.mul(delta));
        position = position.add(velocity.mul(delta));

    }

    @Override
    public void interact(Actor other) {
        super.interact(other);
        if (other.isSolid()) {
            Vector delta = other.getBox().getCollision(getBox());
            if (delta != null) {
                position = position.add(delta);
                if (delta.getX() != 0.0) {
                    velocity = new Vector(0.0, velocity.getY());
                    againstWall = true;
                }

                if (delta.getY() != 0.0) {
                    velocity = new Vector(velocity.getX(), 0.0);
                    onGround = true;
                }
            }
        }
    }

    @Override
    public void draw(Input input, Output output) {
        output.drawSprite(getWorld().getLoader().getSprite("blockerMad"), getBox());

    }

    @Override
    public Vector getPosition() {
        return super.getPosition();
    }

    @Override
    public Box getBox() {
        return new Box(position, SIZE_PLAYER, SIZE_PLAYER);
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public int getPriority() {
        return 42;
    }

    @Override
    public boolean hurt(Actor investigator, Damage type, double amount, Vector location) {

        if (investigator == this)
            return false;

        switch (type) {
            case AIR:
                velocity = getPosition().sub(location).resized(amount);
                return true;

            case PHYSICAL:
                velocity = getPosition().sub(location).resized(amount);
                health -= amount;
                return true;

            case VOID:
                health = 0;
                return true;

            case FIRE:
                health -= amount;
                return true;

            case HEAL:
                if (health == maxHealth)
                    return false;
                health += amount;
                return true;

            case ACTIVATION:
                return true;

            default:
                return super.hurt(investigator, type, amount, location);
        }
    }

    public void gameOver() {
        getWorld().nextLevel();
    }
}
