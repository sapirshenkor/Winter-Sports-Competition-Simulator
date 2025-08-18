package game.states;

import game.entities.sportsman.Sportman;

public class StateActive implements CompetitorState{
    @Override
    public String alert(Sportman cs) {
        return "Active";
    }
}
