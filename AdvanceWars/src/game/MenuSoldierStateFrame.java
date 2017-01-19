package game;

import entity.SoldierEntity;
import util.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by alaguitard on 18/01/17.
 */
public class MenuSoldierStateFrame extends JDialog {

    private SoldierEntity unit;

    JLabel titleLabel;
    JLabel iconPanel;

    public MenuSoldierStateFrame(Frame parent)
    {
        super(parent,"Test");

        this.init(parent);
    }

    private void init(Frame parent) {
        this.setSize(new Dimension(parent.getWidth(),100));
        this.setLocationRelativeTo(parent);
        this.setLocation(new Point(parent.getX(),parent.getY()+parent.getHeight()));
        this.setResizable(false);
        this.setUndecorated(true);
        this.setAutoRequestFocus(false);
        parent.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent componentEvent) {
                int width = componentEvent.getComponent().getWidth();
                MenuSoldierStateFrame.this.setSize(new Dimension(width,100));
            }

            @Override
            public void componentMoved(ComponentEvent componentEvent) {
                int height = componentEvent.getComponent().getHeight();
                int x = componentEvent.getComponent().getLocation().x;
                int y = componentEvent.getComponent().getLocation().y+height;
                MenuSoldierStateFrame.this.setLocation(x,y);
            }

            @Override
            public void componentShown(ComponentEvent componentEvent) {

            }

            @Override
            public void componentHidden(ComponentEvent componentEvent) {

            }
        });

        // Image
        JPanel westPanel = new JPanel();
        this.iconPanel = new JLabel();
        westPanel.add(iconPanel);
        this.add(westPanel);

        // Title
        JPanel northPanel = new JPanel();
        northPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.titleLabel = new JLabel();
        northPanel.add(titleLabel);
        this.add(northPanel);
    }

    public void setUnit(SoldierEntity u)
    {
        this.setTitle(u.getUnit().getName());
        this.unit = u;
        this.setLayout(new GridLayout(1,0));

        // Image
        ImageIcon icon = new ImageIcon(ImageUtility.getResource(this.getUnit().getFilenameImage()));
        this.iconPanel.setIcon(icon);

        // Title
        this.titleLabel.setText(this.unit.getUnit().getName());
    }

    public SoldierEntity getUnit()
    {
        return this.unit;
    }

}
