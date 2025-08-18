package game.entities;
import utilities.Point;

public abstract class Entity {
    private Point location;

    //constructor
    public Entity(){
        location=new Point(0,0);
    }
    //Constructor
    public Entity(Point location){
        if(location.getX()>=0 && location.getX()<=1000000 && location.getY()>=0 && location.getY()<=800) {
            this.location = new Point(location.getX(), location.getY());
        }
    }
    //get location
    public Point getLocation() {
        return location;
    }
    //set location
    public void setLocation(Point Newlocation) {
        if(Newlocation.getX()>=0 && Newlocation.getX()<=1000000 && Newlocation.getY()>=0 && Newlocation.getY()<=800) {
            this.location = new Point(Newlocation.getX(), Newlocation.getY());
        }
    }
    //print the details
    @Override
    public String toString() {
        return location.toString();
    }
    //check if two object are equals


}
