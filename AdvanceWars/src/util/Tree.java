package util;

import java.util.*;

/**
 * Created by alan on 18/01/17.
 */
public class Tree<K,T> implements Map{
    public static HashMap<String,Tree> memory = new HashMap<>();

    public List<Tree<K,T>> children;
    public K key;
    public Tree<K,T> parent;
    public int level;

    public boolean root;

    public Tree(boolean root,K key, int level) {
        this.root = root;
        this.key = key;
        this.parent = null;
        this.level = level;
        this.children = new ArrayList<>();

        memory.put((String) key,this);
    }

    public Tree getAlreadyIn(String key)
    {
        return memory.get(key);
    }

    private Tree<K,T> createNode(K key, int movment)
    {
        Tree ancient = this.memory.get(key);
        Tree<K,T> n = ancient;
        if(ancient == null) {
            n = new Tree<>(false,key,movment);
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
        String ret = "";
        if(this.parent != null)
            ret += "("+this.parent.key+")->";
        ret += this.key+":level="+this.level;

        TreeSet set = new TreeSet<>();
        set.add(this.key);
        for(Tree<K, T> c : this.children)
            ret += c.recDisplayNode(set,c,this.level);

        return ret;
    }

    private String recDisplayNode(Set<String> alreadyPrint, Tree<K, T> n, int tab)
    {
        alreadyPrint.add((String) this.key);
        String ret = "\n|";
        for(int j=0;j<tab-this.level;j++)
            ret+="-";

        if(this.parent != null)
            ret += "("+n.parent.key+")->";
        ret+=n.key+":level="+this.level;

        for(Tree<K, T> c : n.children)
            if(!alreadyPrint.contains(c.key))
                ret += c.recDisplayNode(alreadyPrint,c,tab);

        return ret;
    }

    @Override
    public int size() {
        return level;
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
                if(this.containsNodeKey(n,key,this.level))
                    return true;
            }
        }

        return false;
    }

    private boolean containsNodeKey(Tree<K, T> current, K key,int level)
    {
        if(level > 0)
        {
            if(current.key.equals(key))
                return true;

            if(!current.isLeaf())
            {
                for(Tree<K, T> n : current.children) {
                    if(this.containsNodeKey(n,key,level-1))
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
                if(this.containsNodeValue(n,value,this.level))
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
        return getAlreadyIn(key);
    }

    @Override
    public Object put(Object o, Object o2) {

        K key = (K) o;
        int level = (int) o2;

        Tree t = this.createNode(key,level);
        this.children.add(t);
        if(!t.root)
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

        this.key = null;
        this.children.clear();
        this.parent = null;

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
