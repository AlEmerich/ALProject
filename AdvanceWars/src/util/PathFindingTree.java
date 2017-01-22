package util;

import entity.MapEntitySprite;
import entity.MapFilter;
import entity.SoldierEntity;
import game.GameUniverseBoardImpl;
import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;

import java.util.ArrayList;
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

        String mapWhereUnit = PathFinding.formatKey(unit);

        // Just map tiles in tree, not unit
        for(GameEntity ge : board.get(mapWhereUnit))
            if(ge instanceof MapEntitySprite)
                this.paths = new Tree<>(true,mapWhereUnit, unit.getUnit().getMovementPoint());

        recursivePathFinderTree(this.paths,unit.getUnit().getMovementPoint()-1,board);

        return this.paths;
    }

    @Override
    public void reset()
    {
        if(this.paths == null)
            return;

        this.getMapFromCase(this.paths.key).setFilter(MapFilter.NONE);

        for(Tree n : this.paths.children)
            resetRec(n);
        Tree.memory.clear();
        this.paths.clear();
    }

    private void resetRec(Tree t)
    {
        if(this.getMapFromCase((String) t.key).getFilter() != MapFilter.NONE)
        {
            this.getMapFromCase((String) t.key).setFilter(MapFilter.NONE);
            for(Tree n : (List<Tree>) t.children)
                resetRec(n);
        }
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

        if(currentLevel >= 0)
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
                    Tree children;
                    if(((MapEntitySprite)data).getFilter() == MapFilter.NONE)
                    {
                        children = (Tree) n.put(key,currentLevel);
                        ((MapEntitySprite) data).setFilter(MapFilter.POSSIBLE);
                    }
                    else
                    {
                        children = this.paths.getAlreadyIn(key);
                        if(children != null && children.level < currentLevel)
                            children.level = currentLevel;
                    }
                    if(children != null )
                        recursivePathFinderTree(children,currentLevel-1,board);
                }
            }
        }
    }

    /**
     * Colorize the fastest way for the soldier to go from its position to the cursor
     * @param destKey the formatted coordinates of the position cursor.
     * @return true if the cursor is in the field of possibilities, false if not.
     */
    public List<String> setFastestWay(String destKey)
    {
        Tree destNode = (Tree) this.paths.get(destKey);
        List<String> ret = new ArrayList();

        MapEntitySprite data;
        while(destNode != null && (data = this.getMapFromCase((String) destNode.key)) != null) {
            data.setFilter(MapFilter.FASTEST);
            ret.add((String) destNode.key);
            destNode = destNode.parent;
        }
        return ret;
    }

    public void removeFastestWay(String destKey)
    {
        Tree destNode = (Tree) this.paths.get(destKey);
        MapEntitySprite data;

        // To the source
        while(destNode != null && (data = this.getMapFromCase((String) destNode.key)) != null) {
            data.setFilter(MapFilter.POSSIBLE);
            destNode = destNode.parent;
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