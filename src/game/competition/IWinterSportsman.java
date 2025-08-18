package game.competition;

import game.arena.IArena;
import game.entities.sportsman.Sportman;
import game.entities.sportsman.WinterSportsman;
import game.enumm.Discipline;
import game.enumm.Gender;

import java.awt.*;

public interface IWinterSportsman {
    void change_Acceleration(double d);
    void changecolor(Color c);

}
