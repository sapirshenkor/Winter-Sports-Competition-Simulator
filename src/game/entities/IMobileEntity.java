package game.entities;

import utilities.Point;

public interface IMobileEntity {
    public void move();
    public Point getLocation();
    public void setLocation(Point p);
}
