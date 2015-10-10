package matrix3D.structure;
import matrix3D.geom3D.*;
/**
 * Cette structure va nous permettre de stocker et trier les surfaces a calculer.
 * @author  Yannig Perré
 * @version 0.1.0
 */
public class HeapQueue extends CommonVector {
    /** Liste des surfaces */
    protected DrawableElement [] _queue = new DrawableElement[DEFAULT_ALLOCATION_SIZE];
    
    /** Créer une nouvelle Queue de rendue */
    public HeapQueue() {super();}
    public HeapQueue(int size) {
        _queue = new Surface[size];
    }
    public static int getFather(int son) {
        return  (son - (1 + ((son+1)%2)))>>1;
    }
    public int getLeft(int father) {
        int temp = (father*2)+1;
        if (temp>(size-1)) return father;
        else return temp;
    }
    public int getRight(int father) {
        int temp = (father*2)+2;
        if (temp > (size-1)) return father;
        else return temp;
    }
    /** Retourne le plus prioritaire des fils ou du père */
    protected int max(int node) {
        int temp;
        if (_queue[getRight(node)].getDistance()>=_queue[getLeft(node)].getDistance()) {
            temp=getRight(node);
        }
        else temp=getLeft(node);
        if (_queue[temp].getDistance()>=_queue[node].getDistance()) return temp;
        else return node;
    }
    /** Procéde à un rééquilibrage montant */
    protected void bottomUpSort() {
        if (size>1) {
            for(int current_node=size-1;current_node!=0;current_node=getFather(current_node)) {
                if (_queue[getFather(current_node)].getDistance()<_queue[current_node].getDistance()) {
                    xchange(current_node,getFather(current_node));
                }
            }
        }
    }
    /** Procéde à un tri du haut vers le bas */
    protected void upDownSort() {
        int current_node=0,temp;
        if (size>1) {
            while((temp=max(current_node))!=current_node) {
                xchange(temp,current_node);
                current_node=temp;
            }
        }
    }
    /** Rajoute une surface */
    public void add(DrawableElement s) {
        try {
            _queue[size]=s;
            size++;
        } catch (Exception e) {
            allocateMore();
            add(s);
        }
        bottomUpSort();
    }
    public DrawableElement getHeap() {
        DrawableElement temp = _queue[0];
        _queue[0]=_queue[--size];
        upDownSort();
        return temp;
    }
    /** Fait l'échange de 2 éléments dans la liste */
    private void xchange(int i, int j) {
        DrawableElement temp = _queue[i];
        _queue[i]=_queue[j];
        _queue[j]=temp;
    }
    /** Réalloue de la mémoire */
    protected void allocateMore() {
        DrawableElement [] temp = new Surface[_queue.length*2];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=_queue[i];
            else temp[i]=null;
        }
        _queue = temp;
    }
    public String toString() {
        String result = "(";
        for(int i=0;i<size;i++) result += "" + i + " " + _queue[i] + " priorité : " + _queue[i].getDistance() + "\n" ;
        return result + ")";
    }
}