package game.entities.sportsman;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import game.arena.IArena;
import game.competition.Competitor;
import game.entities.MobileEntity;
import game.enumm.Gender;
import game.states.*;
import utilities.Point;
import java.awt.image.BufferedImage;


public abstract class Sportman extends MobileEntity implements Runnable,Cloneable {
    private String Name;
    private double Age;
    private Gender gender;
    private PropertyChangeSupport support;
    private boolean isfinished=false;
    private static int compid=0;
    private CompetitorState currentState;
    private int changeState_Loc;
    private String destinyState;
    private String destinyTime;

    //constructor
    public Sportman(String name, double age, Gender g, double Maxspeed, double Acceleration, IArena arena) {
        super(Maxspeed, Acceleration, arena);
        this.Name=name;
        //age must be positive,exception?
        this.Age=age;
        this.gender=g;
        support = new PropertyChangeSupport(this);
        compid++;
        currentState=new StateActive();
        destinyState="Active";
        changeState_Loc=0;
        destinyTime="";
    }

    public static int getCompid() {
        return compid;
    }

    public static void setCompid(int compid) {
        Sportman.compid = compid;
    }

    //get Name
    public String getName() {
        return Name;
    }
    //get Age
    public double getAge() {
        return Age;
    }
    //get Gender
    public Gender getGender() {
        return gender;
    }
    //print the details
    @Override
    public String toString() {
        return  "{" +
                "Name='" + Name + '\'' +
                ", Age=" + Age +
                ", gender=" + gender +
                ", S.N=" + compid +
                "} "+super.toString() ;
    }

    public void run(){
        long starttime=System.currentTimeMillis();
        boolean ifLoopENtered=false;
        while(!(getArena().isFinished(this))) {
            Point oldloc=this.getLocation();
            move();
            support.firePropertyChange("location",oldloc,this.getLocation());
            if (this.getChangeState_Loc()<=this.getLocation().getX()&& this.getChangeState_Loc()!=0){
                if(!ifLoopENtered){
                    long endTime=System.currentTimeMillis();
                    long duration=(endTime-starttime)/1000;
                    System.out.println("Time taken :"+ duration +" sec" );
                    ifLoopENtered=true;
                    setDestinyTime(Long.toString(duration));
                }
                setFinished(true);
                ChangeByDestinyState(destinyState);
                break;
            }
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        setFinished(true);
        System.out.println(this.getName()+": "+ this.getLocation().getX());
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl){
			support.removePropertyChangeListener(pcl);
	}
    public void update_support(){
        support=new PropertyChangeSupport(this);
    }
    public boolean isIsfinished() {
        return isfinished;
    }
    public void setFinished(boolean finished) {
        boolean oldFinished = this.isfinished;
        this.isfinished = finished;
        support.firePropertyChange("finished", oldFinished, this.isfinished);
    }


    public CompetitorState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(CompetitorState currentState) {
        this.currentState = currentState;
        destinyState="Completed";
    }
    public String alert(){
        return currentState.alert(this);
    }

    public int getChangeState_Loc() {
        return changeState_Loc;
    }

    public void setChangeState_Loc(int changeState_Loc) {
        this.changeState_Loc = changeState_Loc;
    }

    public String getDestinyState() {
        return destinyState;
    }
    public void setDestinyState(String dState){
        destinyState=dState;
    }

    public void ChangeByDestinyState(String destinyState) {
        switch (destinyState){
            case "injured":
                currentState=new StateInjured();
                break;
            case "disabled":
                currentState=new StateDisabled();
                break;
        }
    }

    public String getDestinyTime() {
        return destinyTime;
    }

    public void setDestinyTime(String destinyTime) {
        this.destinyTime = destinyTime;
    }
}
