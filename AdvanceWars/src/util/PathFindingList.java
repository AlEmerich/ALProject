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
     * Created by alan on 19/01/17.
     */
    public class PathFindingList implements PathFinding {

        private TreeMap<String,List<String>> tree;
        private GameUniverse universe;

        public PathFindingList(GameUniverse universe){
            this.universe = universe;
        }

        @Override
        public Map<String, List<String>> getPossibleWays(GameEntity g) {
            SoldierEntity unit = (SoldierEntity) g;
            TreeMap<String,List<GameEntity>> board =
                    ((GameUniverseBoardImpl) this.universe).getBoardAsTree();

            String key = PathFinding.formatKey(unit);
            this.tree = new TreeMap<>();

            this.tree.put(key, new ArrayList<>());
            recursivePathFinderList(key,unit.getUnit().getMovmentPoint(),board);

            return this.tree;
        }

        @Override
        public void removeFastestWay(String destKey) {

        }

        @Override
        public List<String> setFastestWay(String destkey) {
            return null;
        }

        @Override
        public void reset() {

        }

        @Override
        public String toString()
        {
            String ret="";
            for(String str : this.tree.keySet()){
                ret+=str+" ->";
                for(String l : tree.get(str))
                    ret+=l+" ";
                ret+="\n";
            }
            return ret;
        }

        private void recursivePathFinderList(String currentKey,int currentLevel,TreeMap<String,List<GameEntity>> board)
        {
            int xk = Integer.parseInt(currentKey.split(",")[0]);
            int yk = Integer.parseInt(currentKey.split(",")[1]);

            if(currentLevel-- > 0)
            {
                for(int v=-1;v<2;v++)
                    for(int h=-1;h<2;h++) {
                        if ((v == 0 && h == 0) || (v == 1 && h == 1) || (v == -1 && h == -1) || (v == -1 && h == 1) || (v == 1 && h == -1))
                            continue;
                        int xd = xk + h;
                        int yd = yk + v;

                        String key = xd + "," + yd;
                        List<GameEntity> gl = board.get(key);

                        // check if move blocker
                        if (gl == null)
                            continue;
                        GameEntity data=null;
                        boolean overlap = true;
                        for (GameEntity g : gl)
                        {
                            if (g instanceof MapEntitySprite) {
                                data = g;
                                overlap = overlap && ((MapEntitySprite) g).getType().overlappable;
                            }
                        }

                        if(overlap)
                        {
                            List<String> l = new ArrayList<>();
                            l.addAll(this.tree.get(currentKey));
                            l.add(h + "," + v);
                            if(!this.tree.containsKey(key))
                                this.tree.put(key, l);
                            else if(this.tree.get(key).size() > l.size())
                                this.tree.put(key,l);

                            gl.forEach(x -> {
                                if(x != null && x instanceof MapEntitySprite)
                                    ((MapEntitySprite) x).setFilter(MapFilter.POSSIBLE);
                            });
                            recursivePathFinderList(key,currentLevel,board);
                        }

                    }
            }
        }
    }
