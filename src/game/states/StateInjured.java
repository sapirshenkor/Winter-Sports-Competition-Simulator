package game.states;

import game.entities.sportsman.Sportman;

public class StateInjured implements CompetitorState{
    @Override
    public String alert(Sportman cs) {
        return "Injured";
    }
}
