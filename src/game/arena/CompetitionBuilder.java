package game.arena;

import game.competition.SkiCompetition;

public interface CompetitionBuilder {
    void BuildWinterArena();
    void BuildDiscipline();
    void BuildLeague();
    void BuildGender();
    void BuildMaxCompetitors();
    void BuildCompetitorsArry();
    SkiCompetition getSkicopm();
}
