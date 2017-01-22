package entity;

import game.Player;
import game.SpriteManagerSoldierImpl;
import gameframework.core.SpriteManager;
import soldier.core.Unit;
import soldier.core.UnitGroup;
import util.ImageUtility;

import java.awt.*;

/**
 * Created by alan on 21/01/17.
 */
public class UnitSimpleEntity extends SoldierEntity {

    //Needed to be store to merge soldier in overlap
    private Canvas canvas;
    private SpriteManager spriteManager;
    private String filenameImage;

    public UnitSimpleEntity(Player which,Canvas canvas, Unit unit) {
        super(which,unit);
        if(which.which() == Player.NUMBER.ONE)
            filenameImage = "soldierRed.png";
        else
            filenameImage = "soldierBlue.png";
        this.canvas = canvas;
        spriteManager = new SpriteManagerSoldierImpl(ImageUtility.getResource(filenameImage), canvas);
        spriteManager.setTypes(
                //
                "Idle",//
                "Right",
                "Left",
                "Down",
                "Up",
                "Wait"
        );
    }

    public void draw(Graphics g) {
        String spriteType = "";
        Point tmp = getSpeedVector().getDirection();
        movable = true;

        if (tmp.getX() == 1) {
            spriteType += "Right";
        } else if (tmp.getX() == -1) {
            spriteType += "Left";
        } else if (tmp.getY() == 1) {
            spriteType += "Down";
        } else if (tmp.getY() == -1) {
            spriteType += "Up";
        } else {
            if(this.getUnit().getMovementPoint() == 0)
                spriteType = "Wait";
            else
                spriteType = "Idle";
            //spriteManager.reset();
            movable = true;
        }
        spriteManager.setType(spriteType);
        spriteManager.draw(g, getPosition());

    }

    public UnitGroupEntity merge(Unit u)
    {
        UnitGroup ug = new UnitGroup("Squad "+this.getUnit().getName());

        this.owner.removeInArmy(u);
        this.owner.removeInArmy(this.getUnit());
        ug.addUnit(this.getUnit());
        ug.addUnit(u);
        this.owner.addInArmy(ug);
        UnitGroupEntity entity = new UnitGroupEntity(this.owner,this.canvas,ug);
        entity.setPosition(this.getPosition());
        return entity;
    }

    @Override
    public void oneStepMoveAddedBehavior() {
        if (movable) {
            spriteManager.increment();
        }
    }
}
