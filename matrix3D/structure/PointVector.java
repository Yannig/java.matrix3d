package matrix3D.structure;
import matrix3D.geom3D.*;
/**
 * Cette structure est apparue après une réflexion sur le fonctionnement de la classe 
 * Figure et Affichage qui fonctionne en manipulant toutes les deux des points tout 
 * en ayant un impératif de performance. L'héritage c'est donc imposé comme un procédé 
 * élégant et performant pour ce stockage.
 * @author  Yannig Perré
 * @version 0.2.2
 */
public class PointVector extends CommonVector {
    /** Contient la liste de tous les points qui constituent la figure */
    public Vertex points [] = new Vertex[DEFAULT_ALLOCATION_SIZE];
    /** Zone de tampon permettant de stocker les points projetés */
    protected int [] _x = new int[DEFAULT_ALLOCATION_SIZE];
    protected int [] _y = new int[DEFAULT_ALLOCATION_SIZE];
    
    /** Crée un instance */
    public PointVector() {
        super();
    }
    /** Réalise une duplication */
    public PointVector(PointVector toDuplicate) {
        super();
        duplicate(toDuplicate);
    }
    /** Réalise une duplication */
    public PointVector(PointVector toDuplicate,boolean duplicate) {
        super();
        duplicate(toDuplicate,duplicate);
    }
    /** Ajoute un point dans la chaîne des points */
    public PointVector add(Vertex p) {
        try {
            points[size] = p;
            size++;
        } catch (Exception e) {
            allocateMore();
            add(p);
        }
        return this;
    }
    /** Suppression d'un point par référence */
    public PointVector remove(Vertex p) {
        for(int i=0,j=0;i<size;i++) {
            if (!points[i].equals(p)) j++;
            points[j]=points[i];
        }
        return this;
    }
    /** Suppression d'un point par index */
    public PointVector remove(int index) {
        for(int i=0,j=0;i<size;i++) {
            if (i!=index) j++;
            points[j]=points[i];
        }
        return this;
    }
    /** recopie le vecteur en reprenant les points du vecteur from */
    public PointVector replacePointFrom(PointVector toCopy,PointVector from) {
        clear();
        for(int i=0;i<toCopy.size;i++) add(from.getPoint(toCopy.points[i]));
        return this;
    }
    /** Recopie le vecteur de points en créant de nouvelle instance des points */
    public PointVector duplicate(PointVector toCopy) {
        return duplicate(toCopy,true);
    }
    /** Fait une recopie par copie simple ou par réinstanciation */
    public PointVector duplicate(PointVector toCopy,boolean duplicate) {
        points = new Vertex[toCopy.points.length];
        for(int i=0;i<toCopy.size;i++) {
            if (duplicate) add(new Vertex(toCopy.points[i]));
            else add(toCopy.points[i]);
        }
        return this;
    }
    /** Renvoie le point identique */
    public Vertex getPoint(Vertex p) {
        for(int i=0;i<size;i++) {
            if (points[i].equals(p)) return points[i];
        }
        return null;
    }
    /** Renvoie le point index */
    public Vertex getPoint(int index) {
        return points[index];
    }
    /** Procédure d'allocation de mémoire en plus */
    protected void allocateMore() {
        Vertex [] temp = new Vertex[points.length*2];
        _x = new int [temp.length];
        _y = new int [temp.length];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=points[i];
            else temp[i]=null;
        }
        points = temp;
    }
}