package game.competition;
import java.util.ArrayList;
import java.util.Random;

import game.arena.ArenaFactory;
import game.arena.CompetitionBuilder;
import game.arena.WinterArena;
import game.entities.sportsman.Skier;
import game.enumm.*;

import javax.swing.*;

public class SkiCompetitionBuilder implements CompetitionBuilder {
    private SkiCompetition skicopm;
    private int number_of_default_competitors;

    public SkiCompetitionBuilder(int N){
        skicopm=new SkiCompetition(null,0,null,null,null);
        number_of_default_competitors=N;
    }

    public void BuildWinterArena(){
        skicopm.setArena(new ArenaFactory().get_arena("Winter",700, SnowSurface.ICE, WeatherCondition.CLOUDY));
    }
    public void BuildDiscipline(){
        skicopm.setDiscipline(Discipline.FREESTYLE);
    }
    public void BuildLeague(){
        skicopm.setLeague(League.JUNIOR);
    }
    public void BuildGender(){
        skicopm.setGender(Gender.MALE);

    }
    public void BuildMaxCompetitors(){
        skicopm.setMaxCompetitors(13);
    }

    public void BuildCompetitorsArry(){
        try {

            String[] names = {"sapir", "liran", "etai", "udi", "lucas", "ilan", "ariel", "moshegeni"};
            Gender gender = skicopm.getGender();
            Discipline discipline = skicopm.getDiscipline();
            Random random = new Random();
            int k = 0;
            if (number_of_default_competitors > skicopm.getMaxCompetitors()) {
                JOptionPane.showMessageDialog(null, "Please enter a lower  default value! the max competitors in the race is " + skicopm.getMaxCompetitors(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
                throw new IllegalArgumentException("Please enter a lower value! the max competitors in the race is " + skicopm.getMaxCompetitors());
            }
            while (number_of_default_competitors > k) {
                Skier s = new Skier(names[random.nextInt(8)], random.nextInt(4) + 12, gender,
                        random.nextInt(10)+1, random.nextInt(50)+1, discipline, skicopm.getArena());
                System.out.println(s);
                skicopm.addCompetitor(s);
                k++;
                if ((k % 3) == 0 && number_of_default_competitors > k) {
                    Skier cloneS = s.clone();
                    System.out.println(cloneS);
                    skicopm.addCompetitor(cloneS);
                    k++;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(System.out);
        }
    }

    public SkiCompetition getSkicopm(){
        return skicopm;
    }
}
