package util;

import entity.SoldierEntity;
import game.GameUniverseBoardImpl;
import gameframework.core.Game;
import gameframework.core.GameEntity;
import gameframework.core.GameUniverse;
import gameframework.core.Overlappable;
import levels.TestLevel;
import soldier.core.Unit;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by alaguitard on 18/01/17.
 */
public class PathFinding {

    /*public class Tree<T> {
        private Node<T> root;

        public Tree(T rootData) {
            root = new Node<T>();
            root.data = rootData;
            root.children = new ArrayList<>();
        }

        public class Node<T> {
            private T data;
            private Node<T> parent;
            private List<Node<T>> children;
        }

    }*/

    private TreeMap<String,List<String>> tree;
    private GameUniverse universe;

    public PathFinding(GameUniverse universe){
        this.universe = universe;
    }

    private void setRoot(SoldierEntity unit)
    {
        this.tree = new TreeMap<>();
    }

    public void getPossibleWays(SoldierEntity unit)
    {
        this.setRoot(unit);
        TreeMap<String,List<GameEntity>> board =
                ((GameUniverseBoardImpl) this.universe).getBoardAsTree();

        String key = formatKey(unit);

        this.tree.put(key, new ArrayList<>());
        recursivePathFinder(key,unit.getUnit().getMovmentPoint(),board);
        displayTreeWays();
    }

    public static String formatKey(Overlappable o)
    {
        return o.getPosition().x / TestLevel.SPRITE_SIZE + ","+o.getPosition().y / TestLevel.SPRITE_SIZE;
    }

    private void recursivePathFinder(String currentKey,int currentLevel,TreeMap<String,List<GameEntity>> board)
    {
        int xk = Integer.parseInt(currentKey.split(",")[0]);
        int yk = Integer.parseInt(currentKey.split(",")[1]);

        if(currentLevel-- > 0)
        {
            for(int v=-1;v<2;v++)
                for(int h=-1;h<2;h++)
                {
                    if((v==0 && h==0) || (v==1&&h==1) || (v==-1&&h==-1) || (v==-1&&h==1) || (v==1&&h==-1))
                        continue;
                    int xd = xk+h;
                    int yd = yk+v;

                    String key = xd+","+yd;
                    List<GameEntity> gl = board.get(key);

                    // check if move blocker

                    List<String> l = new ArrayList<>();
                    l.addAll(this.tree.get(currentKey));
                    l.add(h + "," + v);
                    if(!this.tree.containsKey(key))
                        this.tree.put(key, l);
                    else
                        if(this.tree.get(key).size() > l.size())
                            this.tree.put(key,l);
                    recursivePathFinder(key,currentLevel,board);
                }
        }
    }


    private void displayTreeWays(){

        for(String str : this.tree.keySet()){
            System.out.print(str+" ->");
            for(String l : tree.get(str))
                System.out.print(l+" ");
            System.out.println();
        }

    }
}