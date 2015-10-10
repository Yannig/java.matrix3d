package matrix3D.geom3D;
import java.awt.Color;
import matrix3D.structure.*;
import matrix3D.engine.*;
/**
 * Cette classe permet le stockage d'une structure en 3D. Elle est nait d'une
 * réflexion sur la version de Matrix v0.5.0 et de la version précédente de cette classe
 * @author  Yannig Perré
 * @version 0.3.5
 */
public abstract class Shape extends matrix3D.structure.PointVector implements EngineRenderingHints {
    /** Stocke le centre relatif de la figure */
    public Vertex center = new Vertex();
    /** Contient la liste des éléments qui composent la figure */
    public DrawableElementVector elements = new DrawableElementVector(this);
    /** Vecteur de transformation affine qui sera utilisée pour le rendu */
    public MatrixVector transformation = new MatrixVector();
    /** Couleur par défaut de la figure */
    public Color color = Color.lightGray;
    /** Champ permettant de savoir si l'objet est en fil de fer ou autre */
    public int type = Engine.REGULAR_FRAME;
    /** Texture par défaut de la figure */
    public Texture texture = null;
    /** Constructeur simple */
    public Shape() {}
    public Shape(Texture txtr) {
        texture=txtr;
    }
    /** Recopie la figure */
    public Shape(Shape f) {
        copy(f);
    }
    /** Recopie la figure et effectue une transformation */
    public Shape(Shape f,AffineTransform3D transf) {
        copy(f);
        transf(transf);
    }
    /** Change la texture de notre figure */
    public Shape setTexture(Texture txtr) {
        texture=txtr;
        return this;
    }
    /** récupére la texture */
    public Texture getTexture() {
        return texture;
    }
    public void copy(Shape toCopy) {
        setCenter(toCopy.center);
        duplicate(toCopy);
        elements = new DrawableElementVector(this,toCopy.elements);
        texture=toCopy.texture;
    }
    /** Renvoie le vecteur des éléments de la Shape */
    public DrawableElementVector getVector() {
        return elements;
    }
    /** Modifie le type de la figure (fil de fer etc..) */
    public void setType(int t) {
        type=t;
    }
    /** Renvoie le type de la figure */
    public int getType() {
        return type;
    }
    /** Modifie la couleur par défaut de la figure */
    public void setColor(Color c) {
        color=c;
    }
    /** Change la couleur de tous les éléments */
    public void setElementsColor(Color c) {
        elements.setColor(null);
        color = c;
    }
    /** renvoi la couleur défaut de la figure */
    public Color getColor() {
        return color;
    }
    /** Change le type de toutes les surfaces */
    public void setElementsType(int t) {
        elements.setType(NONE);
        type = t;
    }
    /** Modifie le centre de la figure */
    public void setCenter(Vertex ctr) {
        center.set(ctr);
    }
    /** renvoie le centre de la figure */
    public Vertex getCenter() {
        return center;
    }
    /** Méthode appelées pour les transformations de la figure */
    public abstract void doTransf(int time);
    /** Méthode appelées à la fin des transformation */
    public void postTransf() {}
    /** Méthode de transformation à l'aide d'une matrice de transformation */
    public Shape transf(AffineTransform3D transfNCamera) {
        for(int i=0;i<size;i++) points[i].transform(transfNCamera);
        return this;
    }
    /** Méthode appelée pour procéder au calcul de la figure au point courant */
    public void preProcess(AffineTransform3D cam,int time) {
        transformation.clear();
        doTransf(time);
        AffineTransform3D transf = transformation.getInstance();
        AffineTransform3D transfNCamera = cam.mul(transf);
        center.transform(transf);
        transf(transfNCamera);
        elements.preProcessRendering();
        postTransf();
    }
    /** Initialise la structure d'acceuil des éléments */
    protected void clearElements() {
        elements.clear();
    }
    /** Compte le nombre de éléments de la figure */
    public int countElements() {
        return elements.size();
    }
    /** Ajoute une éléments à la figure */
    public Shape addElement(DrawableElement s) {
        elements.add(s);
        return this;
    }
    /** Rajoute un point */
    public Shape addPoint(Vertex p) {
        add(p);
        return this;
    }
    /** Fait une rotation des points (pour construction de la sphère par exemple) */
    public void doRotation(int nbOfRotation,int startingPoint,int length,int link,Vertex top,Vertex bottom) {
        int size = length-startingPoint;
        Surface up = new Surface(this);
        Surface down = new Surface(this);
        Vertex temp [][] = new Vertex[nbOfRotation][size];
        for(int i=0;i<size;i++) {
            temp[0][i]=points[i+startingPoint];
        }
        for(int i=1;i<nbOfRotation;i++) {
            double alpha = (Math.PI*2.*((double)i))/((double)nbOfRotation);
            AffineTransform3D rotation = new AffineTransform3D();
            rotation.setToRotation(0,alpha,0);
            for(int j=0;j<size;j++) {
                addPoint(temp[i][j]=new Vertex(temp[0][j].transform(rotation)));
            }
        }
        for(int i=0;i<nbOfRotation;i++) {
            for(int j=0;j<size-1;j++) {
                addElement(new Surface(this)
                .addVertex(temp[i][j])
                .addVertex(temp[(i+1)%nbOfRotation][j])
                .addVertex(temp[(i+1)%nbOfRotation][j+1])
                .addVertex(temp[i][j+1]));
            }
            if ((link&LINK_UP_WITH_POINT)!=0) {
                addElement(new Surface(this)
                           .addVertex(temp[(i+1)%(nbOfRotation)][0])
                           .addVertex(top)
                           .addVertex(temp[i][0]).inverse());
            }
            if ((link&LINK_BOTTOM_WITH_POINT)!=0) {
                addElement(new Surface(this)
                          .addVertex(bottom)
                          .addVertex(temp[i][size-1])
                          .addVertex(temp[(i+1)%nbOfRotation][size-1]));
            }
            if ((link&CREATE_UP_SURFACE)!=0) {
                up.addVertex(temp[i][0]);
            }
            if ((link&CREATE_BOTTOM_SURFACE)!=0) {
                down.addVertex(temp[i][size-1]);
            }
        }
        if ((link&CREATE_BOTTOM_SURFACE)!=0) {
            addElement(down);
        }
        if ((link&CREATE_UP_SURFACE)!=0) {
            up.inverse();
            addElement(up);
        }
    }
    /** Change le type de la surface de la figure en rayonnant*/
    public Shape setRadient() {
        for(int i=0;i<elements.size;i++) {
            elements.elements[i].setRadient();
        }
        return this;
    }
    /** Change le type de la surface de la figure en absorbant */
    public Shape setAbsorb() {
        for(int i=0;i<elements.size;i++) {
            elements.elements[i].setAbsorb();
        }
        return this;
    }
    /** Transformation possible sur la figure */
    /** Efface toutes les transformations */
    public Shape clearTransformation() {
        transformation.clear();
        return this;
    }
    /** Fait tourner la figure sur elle même */
    public Shape rotate(double rX,double rY,double rZ) {
        transformation.rotate(rX,rY,rZ);
        return this;
    }
    /** Fait tourner la figure autour d'un point */
    public Shape rotate(Vertex cntr,double rX,double rY,double rZ) {
        transformation.rotate(cntr,rX,rY,rZ);
        return this;
    }
    /** Translate la figure */
    public Shape translate(double tX,double tY,double tZ) {
        transformation.translate(tX,tY,tZ);
        return this;
    }
    /** Translate la figure */
    public Shape translate(Vertex p) {
        transformation.translate(p.x,p.y,p.z);
        return this;
    }
    /** Translate la figure avec pour référence 2 points */
    public Shape translate(Vertex p1,Vertex p2) {
        transformation.translate(p2.x-p1.x,p2.y-p1.y,p2.z-p1.z);
        return this;
    }
    /** Translate la figure en inverse */
    public Shape translateInv(Vertex p) {
        transformation.translate(-p.x,-p.y,-p.z);
        return this;
    }
    /** Symétrie et/ou Réduction */
    public Shape scale(double cX,double cY,double cZ) {
        transformation.scale(cX,cY,cZ);
        return this;
    }
    /** Renvoie une description de la figure */
    public String toString() {
        String result = "Centre : " + center +
        "nbre points : " + size +
        "nbre surfaces : " + elements.size 
        + "\nListe des points :\n\n";
        for(int i=0;i<size;i++) result +="" + (i+1) + " : " + points[i]+"\n";
        result+="\nSurfaces :\n\n";
        for(int i=0;i<elements.size;i++)
            result+="" + (i+1) + " : " + elements.elements[i] +"\n";
        return result;
    }
    public String verboseToString() {
        String result = "Centre : " + center + "\n" +
        "nbre points : " + size + "\n" +
        "nbre surfaces : " + elements.size +
        "\nListe des points :\n\n";
        for(int i=0;i<size;i++) result +="" + (i+1) + " : " + points[i]+"\n";
        result+="\nSurfaces :\n\n";
        for(int i=0;i<elements.size;i++)
            result+="" + (i+1) + " : " + elements.elements[i] +"\n";
        return result;
    }
}