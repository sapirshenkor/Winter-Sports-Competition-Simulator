package game.entities.sportsman;

import game.arena.IArena;
import game.competition.Competitor;
import game.competition.IWinterSportsman;
import game.enumm.Discipline;
import game.enumm.Gender;
import game.enumm.League;
import utilities.Point;

import java.awt.*;

public abstract class WinterSportsman extends Sportman implements Competitor, IWinterSportsman {
    private Discipline discipline;
    private Color color;


    //constructor
    public WinterSportsman(String name, double age, Gender g, double Acceleration, double Maxspeed, Discipline d, IArena arena){
        super(name, age, g, Maxspeed, Acceleration,arena);
        this.discipline=d;
        color=null;
    }
    //get the discipline
    public Discipline getDiscipline() {

        return discipline;
    }

    @Override
    public void change_Acceleration(double d) {
        this.setAcceleration(d);
    }
    public void changecolor(Color c){
        color=c;
    }

    //print the details
    @Override
    public String toString() {
        return "WinterSportman{" +
                "discipline=" + discipline +
                "} "+super.toString() ;
    }

    public Color getColor() {
        return color;
    }
    public double getlocation(){
        Point p=super.getLocation();
        return p.getX();
    }

}
