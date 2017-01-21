package entity;

import game.MenuSoldierStateFrame;
import gameframework.core.*;
import gameframework.moves_rules.SpeedVectorDefaultImpl;
import util.ImageUtility;

import java.awt.*;

/**
 * Created by alaguitard on 17/01/17.
 */
public class Cursor extends GameMovable implements Drawable, GameEntity,
        Overlappable {

    private DrawableImage image;
    private boolean testOverlap = true;
    private MenuSoldierStateFrame soldierFrame;
    private CursorMode mode=CursorMode.EXPLORE;

    public Cursor(Canvas canvas)
    {
        image = new DrawableImage(ImageUtility.getResource("cursor.png"), canvas);
        this.soldierFrame = new MenuSoldierStateFrame((Frame) canvas.getParent());
    }

    public SoldierEntity getUnit()
    {
        return soldierFrame.getUnit();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image.getImage(), getPosition().x, getPosition().y, null);
    }

    @Override
    public void oneStepMoveAddedBehavior() {
        if(this.getSpeedVector().getDirection().getX() != 0 ||
                this.getSpeedVector().getDirection().getY() != 0){
            testOverlap = true;
            this.soldierFrame.setVisible(false);
        }
        this.setSpeedVector(SpeedVectorDefaultImpl.createNullVector());
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(0, 0, MapEntitySprite.RENDERING_SIZE, MapEntitySprite.RENDERING_SIZE));
    }

    public void setMode(CursorMode m)
    {
        this.mode = m;
    }

    public CursorMode getMode()
    {
        return this.mode;
    }

    public void showSoldierInformation(SoldierEntity unit)
    {
        this.soldierFrame.setUnit(unit);
        this.soldierFrame.setVisible(true);
    }

    public void setNotTestOverlap()
    {
        this.testOverlap = false;
    }

    public boolean isToTestOverlap()
    {
        return testOverlap;
    }
}
