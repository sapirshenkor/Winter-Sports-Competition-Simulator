package game.states;

import game.entities.sportsman.Sportman;

public class StateDisabled implements CompetitorState{
    @Override
    public String alert(Sportman cs) {
        return "Disabld";
    }
}
