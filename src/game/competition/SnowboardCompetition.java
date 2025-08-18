package game.competition;

import game.arena.WinterArena;
import game.entities.sportsman.Snowboarder;
import game.enumm.Discipline;
import game.enumm.Gender;
import game.enumm.League;

public class SnowboardCompetition extends WinterCompetition{
    public SnowboardCompetition(WinterArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender) {
        super(arena, maxCompetitors, discipline, league, gender);
    }
    public Boolean isValidCompetitor(Competitor competitor) {
        if(competitor instanceof Snowboarder){
            return super.isValidCompetitor(competitor);
        }
        return false;
    }
    public String toString() {
        return "SnowboardCompetition{" +
                "arena=" + getArena() +
                ", maxCompetitors=" + getMaxCompetitors() +
                ", discipline=" + getDiscipline() +
                ", league=" + getLeague() +
                ", gender=" + getGender() +
                '}';
    }
}
