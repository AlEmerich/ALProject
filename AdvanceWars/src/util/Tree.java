package util;

import java.util.*;

/**
 * Created by alan on 18/01/17.
 */
public class Tree<K,T> implements Map {
    public static HashMap<String,Tree> memory = new HashMap<>();

    public List<Tree<K,T>> children;
    public K key;
    public Tree<K,T> parent;
    private int levelMax;

    public Tree(K rootKey,int levelMax) {
        this.key = rootKey;
        this.parent = null;
        this.levelMax = levelMax;
        this.children = new ArrayList<>();
    }

    public Tree getAlreadyIn(String key)
    {
        return memory.get(key);
    }
    public Tree<K,T> createNode(K key, int movment)
    {
        Tree ancient = (Tree) this.memory.get(key);
        Tree<K,T> n = ancient;
        if(ancient == null) {
            n = new Tree<>(key,movment-1);
            memory.put((String) key,n);
        }

        return n;
    }

    public boolean isLeaf()
    {
        return children.isEmpty();
    }

    @Override
    public String toString()
    {
        String ret= (String) this.key;
        for(Tree<K, T> c : this.children)
            ret += recDisplayNode(c,0);

        return ret;
    }

    private String recDisplayNode(Tree<K, T> n, int tab)
    {
        String ret = "\n|";
        for(int j=0;j<tab;j++)
            ret+="-";
        ret+=n.key+"";

        for(Tree<K, T> c : n.children)
            ret += recDisplayNode(c,tab+1);

        return ret;
    }

    @Override
    public int size() {
        return levelMax;
    }

    @Override
    public boolean isEmpty() {
        return (this.key == null);
    }

    @Override
    public boolean containsKey(Object o) {
        K key = (K) o;
        if(this.key.equals(key))
            return true;

        if(!this.isLeaf())
        {
            for(Tree<K, T> n : this.children)
            {
                if(this.containsNodeKey(n,key,this.levelMax))
                    return true;
            }
        }

        return false;
    }

    private boolean containsNodeKey(Tree<K, T> current, K key,int levelMax)
    {
        if(levelMax > 0)
        {
            if(current.key.equals(key))
                return true;

            if(!current.isLeaf())
            {
                for(Tree<K, T> n : current.children) {
                    if(this.containsNodeKey(n,key,levelMax-1))
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        T value = (T) o;
        if(this.children.equals(value))
            return true;

        if(!this.isLeaf())
        {
            for(Tree<K, T> n : this.children)
            {
                if(this.containsNodeValue(n,value,this.levelMax))
                    return true;
            }
        }

        return false;
    }

    private boolean containsNodeValue(Tree<K, T> current,T value,int levelMax)
    {
        if(levelMax > 0)
        {
            if(current.children.equals(value))
                return true;

            if(!current.isLeaf())
            {
                for(Tree<K, T> n : current.children) {
                    if(this.containsNodeValue(n,value,levelMax-1))
                        return true;
                }
            }
        }

        return false;
    }

    @Override
    public Object get(Object o) {
        String key = (String) o;
        if(this.key.equals(key))
            return this;
        else
        {
            for(Tree t : this.children)
            {
                Tree ret = (Tree) t.get(key);
                if(ret != null)
                    return ret;
            }
        }
        return null;
    }

    @Override
    public Object put(Object o, Object o2) {
        K key = (K) o;
        Tree t = this.createNode(key,this.levelMax);
        this.children.add(t);
        t.parent = this;

        return t;
    }

    @Override
    public Object remove(Object o) {
        return null;
    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }
}
