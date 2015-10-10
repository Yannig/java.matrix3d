package matrix3D.structure;
import matrix3D.geom3D.*;

/**
 * Cette classe permet de stocker un ensemble de transformation 3D.
 * @author Yannig Perré
 * @version 0.3
 */
public class MatrixVector extends CommonVector {
    /** Liste des matrices */
    public AffineTransform3D [] transformations = new AffineTransform3D[DEFAULT_ALLOCATION_SIZE];
    /** Transformation affine servant au calcul cumulé des transformations */
    protected AffineTransform3D buffer = new AffineTransform3D();
    /** Constructeur par défaut */
    public MatrixVector() {
        for(int i=0;i<transformations.length;i++) 
            transformations[i] = new AffineTransform3D();
    }
    /** Ajoute une transformation */
    public MatrixVector add(AffineTransform3D a) {
        try {
            transformations[size] = a;
            size++;
        } catch (Exception e) {
            allocateMore();
            add(a);
        }
        return this;
    }
    /** Retourne une matrice résultat de toutes les transformations */
    public AffineTransform3D getInstance() {
        buffer = new AffineTransform3D();
        for (int i=0;i<size;i++) {
            buffer = buffer.mul(transformations[i]);
        }
        return buffer;
    }
    /** Procédure d'allocation de mémoire en plus */
    protected void allocateMore() {
        AffineTransform3D [] temp = new AffineTransform3D[transformations.length*2];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=transformations[i];
            else temp[i]=new AffineTransform3D();
        }
        transformations = temp;
    }
    /** Change la matrice courante en matrice de rotation */
    public void rotate(double rX,double rY,double rZ) {
        try {
            transformations[size].setToRotation(rX,rY,rZ);
            size++;
        } catch (Exception e) {
            allocateMore();
            rotate(rX,rY,rZ);
        }
    }
    /** Idem avec un point */
    public void rotate(Vertex cntr,double rX,double rY,double rZ) {
        try {
            transformations[size].setToRotation(cntr,rX,rY,rZ);
            size++;
        } catch (Exception e) {
            allocateMore();
            rotate(cntr,rX,rY,rZ);
        }
    }
    /** Fonction de translation */
    public void translate(double tX,double tY,double tZ) {
        try {
            transformations[size].setToTranslation(tX,tY,tZ);
            size++;
        } catch (Exception e) {
            allocateMore();
            translate(tX,tY,tZ);
        }
    }
    /** Fonction de scale */
    public void scale(double cX,double cY,double cZ) {
        try {
            transformations[size].setToScale(cX,cY,cZ);
            size++;
        } catch (Exception e) {
            allocateMore();
            scale(cX,cY,cZ);
        }
    }
}