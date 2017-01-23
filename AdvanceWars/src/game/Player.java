package game;

import entity.SoldierEntity;
import entity.UnitSimpleEntity;
import gameframework.core.GameUniverse;
import levels.LevelOne;
import soldier.core.AgeAbstractFactory;
import soldier.core.BreakingRuleException;
import soldier.core.Unit;
import soldier.core.UnitGroup;

import java.awt.*;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alan on 21/01/17.
 */
public class Player {

    public enum NUMBER {
        ONE("Red Army",new Point(9* LevelOne.SPRITE_SIZE,19* LevelOne.SPRITE_SIZE),
                new Point(10* LevelOne.SPRITE_SIZE,17* LevelOne.SPRITE_SIZE),
                new Point(11* LevelOne.SPRITE_SIZE,19* LevelOne.SPRITE_SIZE)),
        TWO("Blue Army",new Point(9* LevelOne.SPRITE_SIZE,6 * LevelOne.SPRITE_SIZE),
                new Point(10* LevelOne.SPRITE_SIZE,8 * LevelOne.SPRITE_SIZE),
                new Point(11* LevelOne.SPRITE_SIZE,6 * LevelOne.SPRITE_SIZE));

        TreeMap<String,Point> initPoints;
        String title;

        NUMBER(String title,Point one,Point two,Point three)
        {
            initPoints = new TreeMap<>();
            initPoints.put("Unit 1",one);
            initPoints.put("Unit 2",two);
            initPoints.put("Unit 3",three);
        }

        @Override
        public String toString()
        {
            return this.title;
        }
    }

    private AgeAbstractFactory factory;
    private Unit army;

    private NUMBER player;

    public Player(AgeAbstractFactory factory,NUMBER p)
    {
        this.factory = factory;
        army = new UnitGroup(p.name());
        this.player = p;
    }

    public Player.NUMBER which()
    {
        return this.player;
    }

    public Unit getArmy()
    {
        return this.army;
    }

    public void addInArmy(Unit u){ this.army.addUnit(u);}
    public void removeInArmy(Unit u)
    {
        army.removeUnit(u);
    }

    public void beginTurn()
    {
        this.army.resetMovementPoint();
    }

    public void init(Canvas canvas, GameUniverse universe)
    {
        boolean infantry = true;
        for(Map.Entry<String, Point> init : player.initPoints.entrySet())
        {
            Unit unit = infantry ? factory.infantryUnit(init.getKey()) : factory.riderUnit(init.getKey());
            try{
                unit.addEquipment(factory.attackWeapon());
                unit.addEquipment(factory.defenseWeapon());
            }catch(BreakingRuleException e){}

            army.addUnit(unit);
            SoldierEntity s = new UnitSimpleEntity(this,canvas,unit);
            s.setPosition(init.getValue());
            universe.addGameEntity(s);
            infantry = !infantry;
        }
    }
}
