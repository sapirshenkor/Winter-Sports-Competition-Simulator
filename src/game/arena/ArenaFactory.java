package game.arena;

import game.enumm.SnowSurface;
import game.enumm.WeatherCondition;

public class ArenaFactory {
    public IArena get_arena(String session, double len, SnowSurface sur, WeatherCondition con){
        switch (session){
            case "Winter":
                return new WinterArena(len,sur,con);
            case "Summer":
                return new SummerArena(len,sur,con);
        }
        return null;
    }
}
