package util;

import gameframework.core.GameEntity;
import gameframework.core.Overlappable;
import levels.TestLevel;

import java.util.List;
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

        public int x,y;

        Direction(int x,int y)
        {
            this.x = x;
            this.y = y;
        }

    }

    static Direction getDirection(String sourceKey, String destKey)
    {
        String[] s = sourceKey.split(",");
        String[] d = destKey.split(",");

        int x = Integer.parseInt(s[0]) - Integer.parseInt(d[0]);
        int y = Integer.parseInt(s[1]) - Integer.parseInt(d[1]);

        if(x == -1)
            return Direction.LEFT;
        else if(x == 1)
            return Direction.RIGHT;
        else if(y == 1)
            return Direction.DOWN;
        else if(y == -1)
            return Direction.UP;
        return null;
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

    List<String> setFastestWay(String destkey);

    /**
     * Reset the collection
     */
    void reset();

    String toString();
}
