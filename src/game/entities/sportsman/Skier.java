package game.entities.sportsman;

import game.arena.IArena;
import game.enumm.Discipline;
import game.enumm.Gender;
import game.enumm.League;
import utilities.Point;

import java.util.Objects;

public class Skier extends WinterSportsman {

    public Skier(String name, double age, Gender g, double Acceleration, double Maxspeed, Discipline qual, IArena arena) {
        super(name, age, g, Acceleration, Maxspeed,qual,arena);

    }



    //set the Current location to start point.
    public void initRace( Point start){
        this.setLocation(start);
        this.setAcceleration(this.getAcceleration()+League.calcAccelerationBonus(this.getAge()));
    }

    //print the details
    @Override
    public String toString() {
        return " Skier"+super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Skier)){
            return false;
        }
        Skier new_skier=(Skier)o;
        return Objects.equals(new_skier.getName(), this.getName()) && Objects.equals(new_skier.getAge(), this.getAge()) && Objects.equals(new_skier.getAcceleration(), this.getAcceleration()) &&new_skier.getGender()==this.getGender() && new_skier.getDiscipline()==this.getDiscipline() && Objects.equals(new_skier.getSpeed(), this.getSpeed());
    }
    public Skier clone(){
        Skier cloneskier=new Skier(this.getName()+" (clone)",this.getAge(),this.getGender(),this.getAcceleration()-League.calcAccelerationBonus(this.getAge()),this.getMaxSpeed(),this.getDiscipline(),this.getArena());
        //cloneskier.update_support();
        return cloneskier;

    }
}
