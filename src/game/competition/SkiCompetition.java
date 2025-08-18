package game.competition;

import game.arena.WinterArena;
import game.entities.sportsman.Skier;
import game.enumm.Discipline;
import game.enumm.Gender;
import game.enumm.League;

public class SkiCompetition extends WinterCompetition{

    public SkiCompetition(WinterArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender){
        super(arena,maxCompetitors,discipline,league,gender);
    }


    @Override
    public Boolean isValidCompetitor(Competitor competitor) {
        if(competitor instanceof Skier){
            return super.isValidCompetitor(competitor);
        }
        return false;
    }

    @Override
    public String toString() {
        return "SkiCompetition{" +
                "arena=" + getArena().toString() +
                ", maxCompetitors=" + getMaxCompetitors() +
                ", discipline=" + getDiscipline() +
                ", league=" + getLeague() +
                ", gender=" + getGender() +
                '}';
    }
}
