package game;

import entity.SoldierEntity;
import soldier.util.AttributUnitVisitor;
import util.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Locale;

/**
 * Created by alaguitard on 18/01/17.
 */
public class MenuSoldierStateFrame extends JDialog {

    private SoldierEntity unit = null;

    JLabel titleLabel;
    JLabel iconPanel;
    JPanel listPanel;

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

        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.LINE_AXIS));

        JPanel rightPanel = new JPanel(new BorderLayout());
        // Title
        JPanel northPanel = new JPanel();
        northPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.titleLabel = new JLabel();
        northPanel.add(titleLabel);
        rightPanel.add(northPanel,BorderLayout.NORTH);

        // Image
        JPanel westPanel = new JPanel();
        this.iconPanel = new JLabel();
        westPanel.add(iconPanel);
        this.add(westPanel);

        this.listPanel = new JPanel();
        JScrollPane scroll = new JScrollPane(this.listPanel);
        rightPanel.add(scroll,BorderLayout.CENTER);

        this.add(rightPanel);
    }

    public void setUnit(SoldierEntity u)
    {
        if(u == null)
        {
            this.unit = null;
            return;
        }
        this.listPanel.removeAll();
        this.setTitle(u.getUnit().getName());
        this.unit = u;
        ImageUtility util = new ImageUtility();
        // Image
        ImageIcon icon = new ImageIcon(util.getResource(this.getUnit().getFilenameImage()));
        this.iconPanel.setIcon(icon);

        // Title

        AttributUnitVisitor visitor = new AttributUnitVisitor();
        this.unit.getUnit().accept(visitor);
        this.titleLabel.setText(this.unit.getUnit().getName()+" ATK "+ visitor.attack
                + " HP " +String.format(Locale.US,"%.2f",visitor.health)
                + " MP "+ visitor.movementPoint+"/"+visitor.maxMovement);

        //this.listPanel.setLayout(new GridLayout(0,1));
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT,-10,0);
        this.listPanel.setLayout(layout);
        this.listPanel.add(new JLabel(visitor.soldiers+"</ul></html>"));
    }

    public SoldierEntity getUnit()
    {
        return this.unit;
    }

}
