package game.entities.sportsman;

import game.arena.IArena;
import game.enumm.Discipline;
import game.enumm.Gender;
import game.enumm.League;
import utilities.Point;

import java.util.Objects;

public class Snowboarder extends WinterSportsman {


    public Snowboarder(String name, double age, Gender g, double Acceleration, double Maxspeed, Discipline qual, IArena arena) {
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
        return super.toString()+" Snowboarder";
    }
    
    public boolean equals(Object o) {
        if (!(o instanceof Snowboarder)){
            return false;
        }
        Snowboarder new_Snowboarder=(Snowboarder)o;
        return Objects.equals(new_Snowboarder.getName(), this.getName()) && Objects.equals(new_Snowboarder.getAge(), this.getAge()) && Objects.equals(new_Snowboarder.getAcceleration(), this.getAcceleration()) &&new_Snowboarder.getGender()==this.getGender() && new_Snowboarder.getDiscipline()==this.getDiscipline() && Objects.equals(new_Snowboarder.getSpeed(), this.getSpeed());
    }
    public Snowboarder clone(){
        return new Snowboarder(this.getName()+" (clone)",this.getAge(),this.getGender(),this.getAcceleration()-League.calcAccelerationBonus(this.getAge()),this.getMaxSpeed(),this.getDiscipline(),this.getArena());
    }
}
