package game.competition;

import game.entities.IMobileEntity;
import game.states.CompetitorState;
import utilities.Point;

import java.beans.PropertyChangeListener;

public interface Competitor extends IMobileEntity {
    public void initRace( Point start);
    public String getName();
    public void addPropertyChangeListener(PropertyChangeListener listener);
    public void removePropertyChangeListener(PropertyChangeListener listener);
    public Double getMaxSpeed();
    public Double getSpeed();
    public boolean isIsfinished();
    public double getlocation();
    public void setChangeState_Loc(int changeState_Loc);
    public void setDestinyState(String dState);
    public String getDestinyState();
    public void setCurrentState(CompetitorState currentState);
    public int getChangeState_Loc();
    public String alert();
    public String getDestinyTime();
}
