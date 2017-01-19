package util;

import gameframework.core.GameEntity;
import gameframework.core.Overlappable;
import levels.TestLevel;

import java.util.Map;

/**
 * Created by alan on 19/01/17.
 */
public interface PathFinding {
    enum Direction
    {
        LEFT(-1,0),
        RIGHT(1,0),
        UP(0,-1),
        DOWN(0,1);

        int x,y;

        Direction(int x,int y)
        {
            this.x = x;
            this.y = y;
        }
    }

    static String formatKey(Overlappable o)
    {
        return o.getPosition().x / TestLevel.SPRITE_SIZE + ","+o.getPosition().y / TestLevel.SPRITE_SIZE;
    }

    /**
     * Calculate possible ways with source g and its movement point
     * @param g the game entity to move
     * @return a collection of ways
     */
    Map getPossibleWays(GameEntity g);

    void removeFastestWay(String map);

    boolean setFastestWay(String sourceKey,String destkey);

    /**
     * Reset the collection
     */
    void reset();

    String toString();
}
