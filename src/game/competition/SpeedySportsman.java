package game.competition;

public class SpeedySportsman extends WSDecorator{

    public SpeedySportsman(IWinterSportsman iw){
        super(iw);
    }
    @Override
    public void change_Acceleration(double d){
        decoWinterSportsman.change_Acceleration(d);
    }

}
