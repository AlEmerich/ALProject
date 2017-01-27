package game;

import gameframework.core.DrawableImage;
import gameframework.core.GameEntity;

import java.awt.*;

/**
 * Created by alan on 26/01/17.
 */
public class PauseMenu extends DrawableImage implements GameEntity {

    private Canvas canvas;
    public boolean draw = false;
    public PauseMenu(Image image, Canvas canvas) {
        super(image, canvas);
        this.canvas = canvas;
    }

    @Override
    public void draw(Graphics g)
    {
        if(draw)
            g.drawImage(this.image,0,0,canvas.getWidth(),canvas.getHeight(),null);
    }
}
