package game.competition;

import game.arena.IArena;
import game.entities.sportsman.WinterSportsman;
import game.enumm.Discipline;
import game.enumm.Gender;

import java.awt.*;

public abstract class WSDecorator implements IWinterSportsman {
    protected IWinterSportsman decoWinterSportsman;

    public WSDecorator(IWinterSportsman iw){
        this.decoWinterSportsman=iw;
    }

    @Override
    public void change_Acceleration(double d) {
        return;
    }

    @Override
    public void changecolor(Color c) {
        decoWinterSportsman.changecolor(c);
    }
    public IWinterSportsman getDecoWinterSportsman(){
        return decoWinterSportsman;
    }

}
