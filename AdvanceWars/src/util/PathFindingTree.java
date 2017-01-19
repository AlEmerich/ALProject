package util;

import entity.MapEntitySprite;
import entity.SoldierEntity;
import game.GameUniverseBoardImpl;
import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alaguitard on 18/01/17.
 */
public class PathFindingTree implements PathFinding{

    private Tree<String,GameEntity> paths;
    private TreeMap<String,List<GameEntity>> board;

    public PathFindingTree(GameUniverse universe){

        this.board = ((GameUniverseBoardImpl) universe).getBoardAsTree();
    }

    @Override
    public Map getPossibleWays(GameEntity g)
    {
        SoldierEntity unit = (SoldierEntity) g;


        String key = PathFinding.formatKey(unit);
        String mapWhereUnit = PathFinding.formatKey(unit);

        // Just map tiles in tree, not unit
        for(GameEntity ge : board.get(mapWhereUnit))
            if(ge instanceof MapEntitySprite)
                this.paths = new Tree<>(key, unit.getUnit().getMovmentPoint());

        recursivePathFinderTree(this.paths,unit.getUnit().getMovmentPoint(),board);

        return this.paths;
        //displayTreeWays();
        //this.paths.displayTree();
    }

    @Override
    public void reset()
    {
        if(this.paths == null)
            return;

        for(Tree n : this.paths.children)
            resetRec(n);
    }

    private void resetRec(Tree t)
    {

        this.getMapFromCase((String) t.key).setFilter(0);

        for(Tree n : (List<Tree>) t.children)
            resetRec(n);
    }

    /**
     * Recursive algorithms.
     * @param n
     * @param currentLevel
     * @param board
     */
    private void recursivePathFinderTree(Tree n, int currentLevel, TreeMap<String,List<GameEntity>> board)
    {
        String[] currentKey = ((String) n.key).split(",");

        int xk = Integer.parseInt(currentKey[0]);
        int yk = Integer.parseInt(currentKey[1]);

        if(currentLevel-- > 0)
        {
            for(Direction dir : Direction.values())
            {
                int xd = xk+dir.x;
                int yd = yk+dir.y;
                String key = xd+","+yd;
                List<GameEntity> gl = board.get(key);

                boolean overlap = true;
                GameEntity data=null;
                if(gl == null)
                    continue;
                for(GameEntity map : gl) {
                    if(map instanceof MapEntitySprite) {
                        data = map;
                        overlap = overlap && ((MapEntitySprite) map).getType().overlappable;
                    }
                }

                if(overlap) {
                    if(((MapEntitySprite)data).getFilter() == 0)
                    {
                        Tree children = (Tree) n.put(key,null);
                        ((MapEntitySprite) data).setFilter(1);
                        recursivePathFinderTree(children,currentLevel,board);
                    }
                    else
                        recursivePathFinderTree(this.paths.getAlreadyIn(key),currentLevel,board);
                }
            }
        }
    }

    /**
     * Colorize the fastest way for the soldier to go from its position to the cursor
     * @param destKey the formatted coordinates of the position cursor.
     * @return true if the cursor is in the field of possibilities, false if not.
     */
    public Map setFastestWay(String destKey)
    {
        Tree destNode = (Tree) this.paths.get(destKey);

        Tree retNode = destNode;

        MapEntitySprite data;
        while(destNode != null && (data = this.getMapFromCase(destKey)) != null) {
            data.setFilter(2);
            destNode = destNode.parent;
        }
        return retNode;
    }

    public void removeFastestWay(Map destNode)
    {
        Tree tree = (Tree) destNode;
        MapEntitySprite data;
        while(destNode != null && (data = this.getMapFromCase((String) ((Tree) destNode).key)) != null) {
            data.setFilter(1);
            destNode = tree.parent;
        }
    }

    private MapEntitySprite getMapFromCase(String key)
    {
        for(GameEntity g : board.get(key))
            if(g instanceof MapEntitySprite)
                return (MapEntitySprite) g;
        return null;
    }

    @Override
    public String toString()
    {
        return this.paths.toString();
    }
}