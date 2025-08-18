package game.competition;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import game.arena.IArena;
import game.arena.WinterArena;
import game.states.StateCompleted;
import utilities.Point;

public abstract class Competition implements PropertyChangeListener {
    private IArena arena;
    private int maxCompetitors;
    private ArrayList<Competitor> activeCompetitors;
    private ArrayList<Competitor> finishedCompetitors;
    private ArrayList<Competitor> AllCompetitors;


    //constructor
    public Competition(IArena arena,int maxCompetitors){
        this.arena=arena;
        this.maxCompetitors=maxCompetitors;
        this.activeCompetitors=new ArrayList<>();
        this.finishedCompetitors=new ArrayList<>();
        this.AllCompetitors=new ArrayList<>();
    }
    //get the Max competitors for the Competition
    public int getMaxCompetitors() {
        return maxCompetitors;
    }
    public void setMaxCompetitors(int max){
        maxCompetitors=max;
    }
    //get the arena
    public IArena getArena() {
        return arena;
    }
    public void setArena(IArena a){
        arena=a;
    }

    //get the active Competitors array
    public ArrayList<Competitor> getActiveCompetitors() {
        return activeCompetitors;
    }
    //get the finished Competitors Array
    public ArrayList<Competitor> getFinishedCompetitors() {
        return finishedCompetitors;
    }
    //set the active Competitors array
    public void setActiveCompetitors(Competitor newcomp) {
        this.activeCompetitors.add(newcomp);
        AllCompetitors.add(newcomp);
    }
    //set the finished Competitors Array
    public void setFinishedCompetitors(Competitor newcom) {
        this.activeCompetitors.remove(newcom);
        this.finishedCompetitors.add(newcom);
    }

    //Returns true if the competitor can compete in the competition , else returns false.
    public abstract Boolean isValidCompetitor(Competitor competitor);

    //call add method in the currentArena Object if the sportsman is valid competitor.
    public  void addCompetitor(Competitor competitor){
        try {
            if (this.activeCompetitors.size() >= this.maxCompetitors) {
                throw new IllegalStateException(arena.getClass().getSimpleName() + " is full max= " + maxCompetitors);
            }
            if (!this.isValidCompetitor(competitor)) {
                throw new IllegalArgumentException("Invalid competitor " + (competitor).getClass().getSimpleName() + " " + competitor.getName());
            }
            competitor.initRace(new Point(0, 0));
            competitor.addPropertyChangeListener(this);
            this.setActiveCompetitors(competitor);
        }catch (IllegalStateException | IllegalArgumentException e){
			e.printStackTrace(System.out);
		}

    }

    //
    public void playTurn(){
        for (int i = this.activeCompetitors.size()-1; i>=0 ; i--) {
            this.getActiveCompetitors().get(i).move();
            if (this.arena.isFinished((this.activeCompetitors.get(i)))){
                this.setFinishedCompetitors(this.activeCompetitors.get(i));
            }
        }
    }


    //Returns true if the activeCompetitors list is not empty otherwise returns false
    public Boolean hasActiveCompetitors(){
        System.out.println(activeCompetitors.size());
        return !(this.activeCompetitors.isEmpty());
    }
    public void propertyChange(PropertyChangeEvent evt){
        if(("finished").equals(evt.getPropertyName())) {
            boolean oldValue = (Boolean) evt.getOldValue();
            boolean newValue = (Boolean) evt.getNewValue();
            if (!oldValue && newValue) {
                Competitor competitor = (Competitor) evt.getSource();
                //System.out.println("Competitor " + competitor.getName() + " has finished the race");
                if(!Objects.equals(competitor.getDestinyState(), "Active")) {
                    competitor.setLocation(new Point(competitor.getChangeState_Loc(), competitor.getLocation().getY()));
                    System.out.println("im here");
                }
                else {
                    competitor.setLocation(new Point(arena.getLength(),competitor.getLocation().getY()));
                    competitor.setCurrentState(new StateCompleted());
                }
                finished_Race(competitor);
            }
        }
    }
    public void finished_Race(Competitor comp){
        synchronized (finishedCompetitors) {
            synchronized (activeCompetitors) {
                setFinishedCompetitors(comp);
            }
        }
    }

    public ArrayList<Competitor> getAllCompetitors() {
        return AllCompetitors;
    }

    public void destiny(){
        Random random=new Random();
        int index1= random.nextInt(AllCompetitors.size());
        int index2=random.nextInt(AllCompetitors.size());
        int arenalength1= (int)Math.round(arena.getLength());
        int locx1= random.nextInt(arenalength1-70)+20;
        int arenalength2= (int)Math.round(arena.getLength());
        int locx2= random.nextInt(arenalength2-70)+20;
        AllCompetitors.get(index1).setChangeState_Loc(locx1);
        AllCompetitors.get(index1).setDestinyState("injured");
        System.out.println(AllCompetitors.get(index1).getName()+ ": injured--"+locx1);
        AllCompetitors.get(index2).setChangeState_Loc(locx2);
        AllCompetitors.get(index2).setDestinyState("disabled");
        System.out.println(AllCompetitors.get(index2).getName()+ ": disabled--"+locx2);
    }
}
