package utilities;

public class Point {
    private final double x;
    private final double y;

    //constructor
    public Point(double x, double y){
        this.x=x;
        this.y=y;
    }
    //get the x
    public double getX() {
        return x;
    }

    //get the y
    public double getY() {
        return y;
    }
    //print the Point object
    public String toString() {
        return "{x: "+this.x+", y: "+this.y+"}";
    }

    //check if two object are equals
    public boolean equals(Object other) {
        return ((other instanceof Point)&& (x==((Point)other).getX() && y==((Point)other).getY()));
    }

}
