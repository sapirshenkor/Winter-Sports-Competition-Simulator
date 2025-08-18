package game.arena;

import game.entities.IMobileEntity;

public interface IArena {
    public Double getFriction();
    public Boolean isFinished(IMobileEntity me);
    public Double getLength();
}
