package matrix3D.geom3D;
import matrix3D.structure.*;
import java.lang.Math;
/**
 * Cette Classe stocke les coordonnées d'un point en 3D.
 * @author  Yannig Perré
 * @version 0.5
 */
public class Vertex extends Object {
    /** intance du point d'origine */
    public final static Vertex origin = new Vertex();
    /** position x à l'origine */
    public double xo=0;
    /** position y à l'origine */
    public double yo=0;
    /** position z à l'origine */
    public double zo=0;
    /** dimension fictive pour la translation (=1) */
    public double to=1;
    /** position en x après transformation */
    public double x=0;
    /** position en y après transformation */
    public double y=0;
    /** position en z après transformation */
    public double z=0;
    /** dimension fictive t après transformation */
    public double t=1;
    /** Distance relative à l'origine */
    public double distance = 0;
    /** Liste des surfaces adjacentes */
    public DrawableElementVector surfaces = new DrawableElementVector(null);
    /** constructeur par défaut */
    public Vertex() {}
    /** initialisation valuée */
    public Vertex(double x,double y,double z) {
        set(x,y,z);
    }
    /** Constructeur avec le contenu d'un autre point */
    public Vertex(Vertex p) {
        set(p);
    }
    /** Constructeur avec l'inverse du contenu d'un autre point */
    public Vertex(Vertex p,boolean inv) {
        set(-p.x,-p.y,-p.z);
    }
    /** Constructeur pour un vecteur */
    public Vertex(Vertex a,Vertex b) {
        set(a,b);
    }
    /** Initialise le contenu du point */
    public Vertex set(Vertex p) {
        set(p.x,p.y,p.z);
        return this;
    }
    public Vertex set(Vertex a,Vertex b) {
        set(b.x-a.x,b.y-a.y,b.z-a.z);
        return this;
    }
    public Vertex set(double x,double y,double z) {
        this.x=xo=x;
        this.y=yo=y;
        this.z=zo=z;
        this.t=to=1;
        return this;
    }
    /** Transforme le point courant à l'aide d'une matrice de transformation */
    public Vertex transform(AffineTransform3D trans) {
        x=xo*trans.matrix.xx + yo*trans.matrix.xy + zo*trans.matrix.xz + to*trans.matrix.xt;
        y=xo*trans.matrix.yx + yo*trans.matrix.yy + zo*trans.matrix.yz + to*trans.matrix.yt;
        z=xo*trans.matrix.zx + yo*trans.matrix.zy + zo*trans.matrix.zz + to*trans.matrix.zt;
        t=1;//xo*trans.matrix.tx + yo*trans.matrix.ty + zo*trans.matrix.tz + to*trans.matrix.tt;
        return this;
    }
    /** renvoie le produit scalaire des 2 vecteurs */
    public double mul(Vertex p) {
        return -(x*p.x+y*p.y+z*p.z);
    }
    /** test si l'objet est le même */
    public boolean equals(Object o) {
        Vertex v;
        try {
            v = (Vertex)o;
            return ((v.xo==xo)&&(v.yo==yo)&&(v.zo==zo)&&(v.to==to));
        } catch (Exception e) { return false; }
    }
    /** On procéde au deux projections */
    public void doProjection(int distance,int sizeX,int sizeY,int offsetX,int offsetY,int [] xa, int [] ya,int i) {
        if (z>0.1) {
            xa[i] = (sizeX - offsetX) + (int)((x*distance)/z);
            ya[i] = (sizeY - offsetY) - (int)((y*distance)/z);
            this.distance = (x*x) + (y*y) + (z*z);
        } else {
            xa[i] = DrawableElement.UP_CLIPPING+1;
            ya[i] = DrawableElement.UP_CLIPPING+1;
            this.distance = Double.MAX_VALUE;
        }
    }
    /** Duplique le point */
    public Object Clone() {
        return new Vertex(this);
    }
    /** Calcul la distance avec l'origine */
    public double distance() {
        return Math.sqrt((x*x) + (y*y) + (z*z));
    }
    public double getDistance() {
        return distance;
    }
    /** Calcul le vecteur orthogonal au deux vecteurs */
    public Vertex vector(Vertex v) {
        return new Vertex(
        (y*v.z)-(z*v.y),
        (z*v.x)-(x*v.z),
        (x*v.y)-(y*v.x));
    }
    /** Calcul le vecteur par la normale à la surface formée par les trois points */
    public static Vertex getNorm(Vertex a,Vertex b,Vertex c) {
        Vertex tmp1 = new Vertex(a,b);
        Vertex tmp2 = new Vertex(a,c);
        return tmp1.vector(tmp2);
    }
    /** Calcul le vecteur moyen au sommet à l'aide des surfaces adjacentes */
    public Vertex getNorm() {
        double x = 0;
        double y = 0;
        double z = 0;
        if (surfaces.size==0) return new Vertex();
        for(int i = 0;i<surfaces.size;i++) {
            x+=surfaces.elements[i].norm.x;
            y+=surfaces.elements[i].norm.y;
            z+=surfaces.elements[i].norm.z;
        }
        return new Vertex(x/surfaces.size,y/surfaces.size,z/surfaces.size).norm();
    }
    /** Calcule la normalisation */
    public Vertex norm() {
        return norm(distance());
    }
    public Vertex norm(double norm) {
        x=xo/=norm;y=yo/=norm;z=zo/=norm;
        return this;
    }
    /** Inverse la norme du vecteur */
    public void inverse() {
        xo=-xo;yo=-yo;zo=-zo;
        x=-x;y=-y;z=-z;
    }
    public void draw(matrix3D.engine.Camera c) {
        int [] x={0};
        int [] y={0};
        doProjection(c.getSizeX(),c.getSizeX(),c.getSizeY(),c.getOffsetX(),c.getOffsetY(),x,y,0);
        c.getGraphics().setColor(java.awt.Color.black);
        c.getGraphics().drawLine(x[0]-3,y[0]-3,x[0]+3,y[0]+3);
        c.getGraphics().drawLine(x[0]+3,y[0]-3,x[0]-3,y[0]+3);
    }
    /** Fonction d'affichage sous forme de texte */
    public String toString() {
        java.text.NumberFormat frm = new java.text.DecimalFormat(" 0.00 ;-0.00 ");
        return "(" + frm.format(x) + frm.format(y) + frm.format(z) + ")";
    }
}