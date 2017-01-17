package entity;

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

    public Cursor(Canvas canvas)
    {
        image = new DrawableImage(ImageUtility.getResource("cursor.png"), canvas);
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
        }
        this.setSpeedVector(SpeedVectorDefaultImpl.createNullVector());
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(0, 0, MapEntitySprite.RENDERING_SIZE, MapEntitySprite.RENDERING_SIZE));
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
