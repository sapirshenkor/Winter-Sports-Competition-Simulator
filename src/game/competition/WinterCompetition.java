package game.competition;

import game.arena.WinterArena;
import game.entities.sportsman.WinterSportsman;
import game.enumm.Discipline;
import game.enumm.Gender;
import game.enumm.League;

public abstract class WinterCompetition extends Competition {
    private Gender gender;
    private League league;
    private Discipline discipline;

    //constructor
    public WinterCompetition(WinterArena arena, int maxCompetitors, Discipline discipline, League league, Gender gender){
        super(arena,maxCompetitors);
        this.gender=gender;
        this.league=league;
        this.discipline=discipline;
    }
    //get the Gender
    public Gender getGender() {
        return gender;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    //get the League
    public League getLeague() {
        return league;
    }
    //get the Discipline
    public Discipline getDiscipline() {
        return discipline;
    }

    public Boolean isValidCompetitor(Competitor competitor) {
        WinterSportsman comp=(WinterSportsman) competitor;
        return this.getLeague().isInLeague(comp.getAge()) && this.getGender()==comp.getGender() && this.getDiscipline()==comp.getDiscipline();
    }


}
