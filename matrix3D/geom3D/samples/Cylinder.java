package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;
/**
 * Crée directement une figure de type Cylindre
 * @author  Yannig Perré
 * @version 0.3
 * Modifications :
 * le 19 novembre par Perré Y. (v0.3) :
 * - remise à niveau depuis les modifications de Matrix v0.2
 */
public abstract class Cylinder extends Shape {
    public Cylinder() {init(1,1,8);}
    public Cylinder(double hauteur, double largeur, int nombreCote) {
        init(hauteur,largeur,nombreCote);
    }
    public void init(double hauteur, double largeur, int nombreCote) {
        center.set(0,0,0);
        addPoint(new Vertex(largeur/2,-hauteur/2,0));
        addPoint(new Vertex(largeur/2, hauteur/2,0));
        doRotation(nombreCote,0,2,this.CREATE_BOTH_SURFACE,null,null);
    }
}