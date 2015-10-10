package matrix3D.structure;
import matrix3D.geom3D.*;

/**
 * Petite pile permettant d'inverser le sens trigo des points d'une surface
 * @author Yannig Perré
 * @version 0.1
 */
public class PointFifo extends CommonVector {
    Vertex fifo [] = new Vertex[DEFAULT_ALLOCATION_SIZE];
    public PointFifo() {}
    public PointFifo add(Vertex p) {
        try {
            fifo[size] = p;
            size++;
        } catch (Exception e) {
            allocateMore();
            add(p);
        }
        return this;
    }
    public void push(Vertex p) {
        add(p);
    }
    public Vertex pop() {
        return fifo[--size];
    }
    public boolean isEmpty() {
        return size==0;
    }
    /** Procédure d'allocation de mémoire en plus */
    protected void allocateMore() {
        Vertex [] temp = new Vertex[fifo.length*2];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=fifo[i];
            else temp[i]=null;
        }
        fifo = temp;
    }
}