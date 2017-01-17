package game;

import entity.MapEntitySprite;
import gameframework.core.DrawableImage;
import gameframework.core.SpriteManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by alaguitard on 17/01/17.
 */
public class SpriteManagerSoldierImpl implements SpriteManager {

    private final DrawableImage image;
    private int spriteNumber;
    private TypeSprite currentState;

    enum TypeSprite
    {
        Idle(0,4*16,16,16,3),
        Right(4*16, 4*16, 16, 16, 4),
        Left(5*16, 4*16, 16, 16, 4);

        int beginX, beginY;
        int sizeX, sizeY;
        int nbOfSprite;

        TypeSprite(int bx, int by, int sx, int sy, int nbS)
        {
            this.beginX = bx;
            this.beginY = by;
            this.sizeX = sx;
            this.sizeY = sy;
            this.nbOfSprite = nbS;
        }
    }

    public SpriteManagerSoldierImpl(String filename, Canvas canvas)
    {
        this.image = new DrawableImage(filename, canvas);
        this.currentState = TypeSprite.Left;
        this.spriteNumber = 0;
    }

    @Override
    public void setTypes(String... types) {

    }

    @Override
    public void setType(String type) {

    }

    @Override
    public void draw(Graphics g, Point position) {
        int dx1 = (int) position.getX();
		int dy1 = (int) position.getY();
		int dx2 = dx1 + MapEntitySprite.RENDERING_SIZE;
		int dy2 = dy1 + MapEntitySprite.RENDERING_SIZE;

		// Source image coordinates
		int sx1 = currentState.beginX + (spriteNumber * currentState.sizeX);
		int sy1 = currentState.beginY ;
        int sx2 = sx1 + currentState.sizeX;
        if(currentState == TypeSprite.Left)
		    sx2 = sx1 - currentState.sizeX;
		int sy2 = sy1 + currentState.sizeY;

		g.drawImage(image.getImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,
				null);
    }

    @Override
    public void increment() {
    spriteNumber = (spriteNumber + 1)%currentState.nbOfSprite;
    }

    @Override
    public void reset() {
        spriteNumber = 0;
    }

    @Override
    public void setIncrement(int increment) {
        this.spriteNumber = increment;
    }
}
