package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;

/**
 * Crée directement une figure de type cone
 * @author  Yannig Perré
 * @version 0.3
 */
public abstract class Cone extends Shape {
    public Cone() {init(1,1,4);}
    public Cone(double hauteur, double largeur, int nombreCote) {
        init(hauteur,largeur,nombreCote);
    }
    public void init(double hauteur, double largeur, int nombreCote) {
        center.set(0,0,0);
        Vertex top = new Vertex(0,2/3*hauteur,0);
        addPoint(new Vertex(largeur/2,-hauteur/3,0));
        addPoint(top);
        doRotation(nombreCote,0,1,CREATE_BOTTOM_SURFACE|LINK_UP_WITH_POINT,top,null);
    }
}