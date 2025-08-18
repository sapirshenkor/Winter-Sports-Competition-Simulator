package game.entities;

import game.arena.IArena;
import utilities.Point;

import java.util.Objects;

public abstract class MobileEntity extends Entity implements IMobileEntity {
    private double maxSpeed;
    private double acceleration;
    private double speed;
    private IArena arena;

    //constructor
    public MobileEntity(double Maxspeed, double Acceleration ,IArena arena){
        super();
        this.maxSpeed=Maxspeed;
        this.acceleration=Acceleration;
        this.speed=0.0;
        this.arena=arena;
    }
    //get the Max speed
    public Double getMaxSpeed() {
        return maxSpeed;
    }
    //get the Acceleration
    public Double getAcceleration() {
        return acceleration;
    }
    //get the current speed
    public Double getSpeed() {
        return speed;
    }
    //set the speed
    public void setSpeed(Double s){
        this.speed=s;
    }
    //set the acceleration
    public void setAcceleration(Double new_acceleration) {
        this.acceleration = new_acceleration;
    }

// accelerate if not at top speed :
// currSpeed += acceleration*friction.
// move forward: currLocation.x += currSpeed
// (y is always 0 for now)
//returns false if crossed finish line
// prints this.name+" My Location is "+currentLocation.toString()+
//" My speed is "+currentSpeed
    public void move() {
        if (this.getSpeed() <= this.getMaxSpeed()) {
            Double new_speed;
            new_speed = this.getSpeed() + (this.getAcceleration() * arena.getFriction());
            if (new_speed > this.getMaxSpeed()) {
                this.setSpeed(this.getMaxSpeed());
            } else {
                this.setSpeed(new_speed);
            }
        }
        Point p = new Point(this.getLocation().getX() + this.getSpeed(), this.getLocation().getY());
        if(p.getX()> arena.getLength()){
            this.setLocation(new Point(arena.getLength(),p.getY()));
        }
        else {
            this.setLocation(p);
        }
    }

    //print the details
    @Override
    public String toString() {
        return  "{" +
                "maxSpeed=" + maxSpeed +
                ", acceleration=" + acceleration +
                ", speed=" + speed +
                "} "+super.toString();
    }


    public IArena getArena() {
        return arena;
    }

    public void setArena(IArena arena) {
        this.arena = arena;
    }
}
