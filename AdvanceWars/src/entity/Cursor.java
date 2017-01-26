package entity;

import game.MenuSoldierStateFrame;
import game.Player;
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
    private MenuSoldierStateFrame soldierFrame;
    private CursorMode mode=CursorMode.EXPLORE;

    private Player.NUMBER currentPlayer;

    public Cursor(Canvas canvas)
    {
        ImageUtility util = new ImageUtility();
        image = new DrawableImage(util.getResource("cursor.png"), canvas);
        this.soldierFrame = new MenuSoldierStateFrame((Frame) canvas.getParent());
        currentPlayer = Player.NUMBER.ONE;
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
            this.soldierFrame.setVisible(false);
        }
        this.setSpeedVector(SpeedVectorDefaultImpl.createNullVector());
    }

    @Override
    public Rectangle getBoundingBox() {
        return (new Rectangle(0, 0, MapEntitySprite.RENDERING_SIZE, MapEntitySprite.RENDERING_SIZE));
    }

    public Player.NUMBER getCurrentPlayer(){ return this.currentPlayer; }

    public void changeCurrentPlayer()
    {
        this.currentPlayer = (this.currentPlayer.name() == Player.NUMBER.ONE.name() ? Player.NUMBER.TWO : Player.NUMBER.ONE);
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
}
