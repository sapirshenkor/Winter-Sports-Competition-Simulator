package game.arena;

import game.entities.IMobileEntity;
import game.enumm.SnowSurface;
import game.enumm.WeatherCondition;
import utilities.Point;

public class SummerArena implements IArena{
    private final Double length;
    private final SnowSurface surface;
    private final WeatherCondition condition;

    //constructor
    public SummerArena(Double len,SnowSurface sur,WeatherCondition con){
        this.length=len;
        this.surface=sur;
        this.condition=con;
    }
    //get the length
    public Double getLength() {
        return length;
    }
    //get the Snow Surface
    public SnowSurface getSurface() {
        return surface;
    }
    //get the Weather Condition

    public WeatherCondition getCondition() {
        return condition;
    }

    @Override
    public Double getFriction() {
        return this.surface.friction;
    }

    @Override
    public Boolean isFinished(IMobileEntity me) {
        if(this.length<=me.getLocation().getX()){
            me.setLocation(new Point(this.length,me.getLocation().getY()));
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString() {
        return "SummerArena{" +
                "length=" + length +
                ", surface=" + surface +
                ", condition=" + condition +
                '}';
    }
}
