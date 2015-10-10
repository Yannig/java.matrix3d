package matrix3D.structure;
import matrix3D.geom3D.*;

/**
 * A la maniére de PointVector et de DrawableElementVector, cette classe répond
 * à l'impératif de pouvoir manipuler de manière élégante des ensembles d'objets
 * @author Yannig Perré
 * @version 0.2
 */
public class ObjectVector extends CommonVector {
    /** Nombre de facette contenu dans le vecteur */
    protected int _numberOfFacets = 0;
    
    /** Liste de nos objets présents sur la scene */
    public Shape [] objects = new Shape[DEFAULT_ALLOCATION_SIZE];
    public ObjectVector() {
        super();
    }
    /** Ajoute un objet à la scene */
    public ObjectVector add(Shape f) {
        try {
            objects[size] = f;
            size++;
            _numberOfFacets += f.elements.size;
        } catch (Exception e) {
            allocateMore();
            add(f);
        }
        return this;
    }
    /** Suppression d'une Shape par référence */
    public ObjectVector remove(Shape f) {
        for(int i=0,j=0;i<size;i++) {
            if (!objects[i].equals(f)) {
                _numberOfFacets -= f.elements.size;
                j++;
            }
            objects[j]=objects[i];
        }
        return this;
    }
    /** Suppression d'un point par index */
    public ObjectVector remove(int index) {
        for(int i=0,j=0;i<size;i++) {
            if (i!=index) {
                _numberOfFacets -= objects[i].elements.size;
                j++;
            }
            objects[j]=objects[i];
        }
        return this;
    }
    /** Alloue une quantité supplémentaire de mémoire */
    protected void allocateMore() {
        Shape [] temp = new Shape[objects.length*2];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=objects[i];
            else temp[i]=null;
        }
        objects = temp;
    }
}