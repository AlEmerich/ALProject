package game;

import entity.SoldierEntity;
import soldier.core.Unit;

import java.awt.*;

/**
 * Created by alaguitard on 18/01/17.
 */
public class MenuSoldierStateFrame extends Dialog {

    private SoldierEntity unit;

    public MenuSoldierStateFrame(Frame parent)
    {
        super(parent,"Test");

        this.init(parent);
    }

    private void init(Frame parent) {
        this.setSize(new Dimension(parent.getWidth()-2,100));
        this.setLocation(new Point(0,parent.getHeight()));
        this.setResizable(false);
        this.setUndecorated(true);
        this.setAutoRequestFocus(false);

    }

    public void setUnit(SoldierEntity u)
    {
        this.setTitle(u.getUnit().getName());
        this.unit = u;
    }

    public SoldierEntity getUnit()
    {
        return this.unit;
    }
}
