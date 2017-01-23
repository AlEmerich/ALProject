package game;

import entity.SoldierEntity;
import gameframework.core.GameEntity;
import gameframework.core.GameUniverseDefaultImpl;
import gameframework.core.Movable;
import gameframework.core.Overlappable;
import gameframework.moves_rules.MoveBlockerChecker;
import gameframework.moves_rules.OverlapProcessor;
import util.PathFinding;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by alaguitard on 18/01/17.
 */
public class GameUniverseBoardImpl extends GameUniverseDefaultImpl {

    public GameUniverseBoardImpl(MoveBlockerChecker obs, OverlapProcessor col) {
        super(obs, col);
    }

    public TreeMap<String,List<GameEntity>> getBoardAsTree()
    {
        TreeMap<String,List<GameEntity>> tree = new TreeMap<>();
        for( Iterator<GameEntity> it = this.gameEntities();
             it.hasNext();  )
        {
            GameEntity g = it.next();
            if(g instanceof Overlappable)
            {
                String key = PathFinding.formatKey((Overlappable) g);
                if(!tree.containsKey(key))
                    tree.put(key,new ArrayList<>());
                tree.get(key).add(g);
            }
        }
        return tree;
    }

    @Override
    public void allOneStepMoves()
    {
        GameEntity entity;
        for (Iterator<GameEntity> it = gameEntities();
             it.hasNext() ; ) {
            entity = it.next();
            if (entity instanceof Movable) {
                ((Movable) entity).oneStepMove();
                if(entity instanceof SoldierEntity
                        && !((SoldierEntity) entity).getUnit().alive() )
                    this.removeGameEntity(entity);

            }
        }
    }
}
