package game.competition;

import java.awt.*;

public class ColoredSportsman extends WSDecorator{

    public ColoredSportsman(IWinterSportsman iw){
        super(iw);
    }
    @Override
    public void changecolor(Color c){
        decoWinterSportsman.changecolor(c);
    }
}
