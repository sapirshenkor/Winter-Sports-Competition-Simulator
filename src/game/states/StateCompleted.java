package game.states;

import game.entities.sportsman.Sportman;

public class StateCompleted implements CompetitorState{
    @Override
    public String alert(Sportman cs) {
        return "Completed";
    }
}
