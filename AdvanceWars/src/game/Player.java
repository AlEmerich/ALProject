package game;

import entity.SoldierEntity;
import gameframework.core.GameUniverse;
import levels.TestLevel;
import soldier.core.AgeAbstractFactory;
import soldier.core.BreakingRuleException;
import soldier.core.Unit;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alan on 21/01/17.
 */
public class Player {

    public enum NUMBER {
        ONE(new Point(9* TestLevel.SPRITE_SIZE,6 * TestLevel.SPRITE_SIZE),
                new Point(10* TestLevel.SPRITE_SIZE,8 * TestLevel.SPRITE_SIZE),
                new Point(11* TestLevel.SPRITE_SIZE,6 * TestLevel.SPRITE_SIZE)),
        TWO(new Point(9* TestLevel.SPRITE_SIZE,19* TestLevel.SPRITE_SIZE),
                new Point(10* TestLevel.SPRITE_SIZE,17* TestLevel.SPRITE_SIZE),
                new Point(11* TestLevel.SPRITE_SIZE,19* TestLevel.SPRITE_SIZE));

        TreeMap<String,Point> initPoints;

        NUMBER(Point one,Point two,Point three)
        {
            initPoints = new TreeMap<>();
            initPoints.put("Unit 1",one);
            initPoints.put("Unit 2",two);
            initPoints.put("Unit 3",three);
        }
    }

    private AgeAbstractFactory factory;
    private List<SoldierEntity> army;

    public Player(AgeAbstractFactory factory)
    {
        this.factory = factory;
    }

    public void init(Canvas canvas,GameUniverse universe,NUMBER player)
    {
        boolean infantry = true;
        for(Map.Entry<String, Point> init : player.initPoints.entrySet())
        {
            Unit unit = infantry ? factory.infantryUnit(init.getKey()) : factory.riderUnit(init.getKey());
            try{
                unit.addEquipment(factory.attackWeapon());
                unit.addEquipment(factory.defenseWeapon());
            }catch(BreakingRuleException e){}

            SoldierEntity s = new SoldierEntity(canvas,unit);
            s.setPosition(init.getValue());
            universe.addGameEntity(s);
            infantry = !infantry;
        }
    }
}
