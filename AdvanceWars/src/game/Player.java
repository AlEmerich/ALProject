package game;

import entity.MapEntitySprite;
import entity.SoldierEntity;
import entity.UnitSimpleEntity;
import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;
import levels.LevelOne;
import soldier.core.*;

import java.awt.*;
import java.util.List;
import java.util.Random;

/**
 * Created by alan on 21/01/17.
 */
public class Player {

    public enum NUMBER {
        ONE("Red Army",LevelOne.SIZE_X_WINDOW,6),
        TWO("Blue Army",LevelOne.SIZE_X_WINDOW,LevelOne.SIZE_Y_WINDOW-9);

        int upx, downy;
        String title;

        NUMBER(String title,int upx,int downy)
        {
            this.upx = upx;
            this.downy = downy;
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
        army = new UnitGroup(p.toString());
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

    public void init(Canvas canvas, GameUniverse universe,int numberOfUnit)
    {
        boolean infantry = true;
        for(int i=0;i<numberOfUnit;i++)
        {
            Unit unit = infantry ? factory.infantryUnit("Unit "+(i+1)) : factory.riderUnit("Unit "+i);


            army.addUnit(unit);
            SoldierEntity s = new UnitSimpleEntity(this,canvas,unit);
            String pos = "";
            boolean create = false;

            while(!create)
            {
                pos = getRandomPointForArmy(player.upx,player.downy);
                create = true;
                List<GameEntity> list = ((GameUniverseBoardImpl) universe).getBoardAsTree().get(pos);
                for(GameEntity g: list)
                {
                    boolean overlap = g instanceof MapEntitySprite
                            && !((MapEntitySprite) g).getType().overlappable;
                    boolean soldier = g instanceof UnitSimpleEntity;
                    if(overlap || soldier)
                        create = false;
                }
            }

            s.setPosition(new Point(Integer.parseInt(pos.split(",")[0])*LevelOne.SPRITE_SIZE,
                    Integer.parseInt(pos.split(",")[1])*LevelOne.SPRITE_SIZE));
            universe.addGameEntity(s);
            infantry = !infantry;
        }


    }

    public AgeAbstractFactory getFactory()
    {
        return this.factory;
    }

    private String getRandomPointForArmy(int upx, int downy)
    {
        Random r = new Random();
        return (r.nextInt(upx))+","+(downy+r.nextInt(3));
    }
}
