package matrix3D.structure;
import matrix3D.geom3D.*;
import java.awt.Color;
/**
 * A l'image de PointVector, cette structure est apparue pour le besoin de simplifier
 * la classe Shape.
 * @author  Yannig Perré
 * @version 0.2.1
 */
public class DrawableElementVector extends CommonVector {
    /** Contient la liste des éléments de Shape */
    public DrawableElement elements [] = new DrawableElement[DEFAULT_ALLOCATION_SIZE];
    /** Lien vers la figure Shape père (pour MAJ de sa liste interne) */
    public Shape father = null;

    /** Crée un instance */
    public DrawableElementVector(Shape father) {
        super();
        father=father;
    }
    /** Crée un instance */
    public DrawableElementVector(Shape f,DrawableElementVector vectorToDuplicate) {
        father=f;
        duplicate(vectorToDuplicate);
    }
    public void duplicate(DrawableElementVector vectorToDuplicate) {
        size = vectorToDuplicate.size;
        elements = new DrawableElement[vectorToDuplicate.elements.length];
        for(int i=0;i<size;i++) {
            elements[i] = new Surface(father,vectorToDuplicate.elements[i]);
        }
    }
    /** Ajoute une surface */
    public DrawableElementVector add(DrawableElement sf) {
        try {
            elements[size] = sf;
            size++;
        } catch (Exception e) {
            allocateMore();
            add(sf);
        }
        return this;
    }
    /** Suppression d'une surface par référence */
    public DrawableElementVector remove(DrawableElement sf) {
        for(int i=0,j=0;i<size+1;i++) {
            if (!elements[i].equals(sf)) j++;
            elements[j]=elements[i];
        }
        elements[size]=null;
        return this;
    }
    /** Suppression d'un point par index */
    public DrawableElementVector remove(int index) {
        for(int i=0,j=0;i<size;i++) {
            if (i!=index) j++;
            elements[j]=elements[i];
        }
        elements[size]=null;
        return this;
    }
    /** Renvoie le point identique */
    public DrawableElement get(DrawableElement p) {
        for(int i=0;i<size;i++) {
            if (elements[i].equals(p)) return elements[i];
        }
        return null;
    }
    /** Renvoie le point index */
    public DrawableElement get(int index) {
        return elements[index];
    }
    /** Procédure d'allocation de mémoire en plus */
    protected void allocateMore() {
        DrawableElement [] temp = new DrawableElement[elements.length*2];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=elements[i];
            else temp[i]=null;
        }
        elements = temp;
    }
    /** Alloue la couleur à toute les surfaces */
    public void setColor(Color color) {
        for(int i=0;i<size;i++) elements[i].setColor(color);
    }
    /** Enléve la couleur de tous les éléments */
    public void clearColor() {
        for(int i=0;i<size;i++) elements[i].clearColor();
    }
    /** Change le type de toutes les surfaces */
    public void setType(int type) {
        for(int i=0;i<size;i++) elements[i].setType(type);
    }
    public void preProcessRendering() {
        for(int i=0;i<size;i++) elements[i].preProcessRendering();
    }
}