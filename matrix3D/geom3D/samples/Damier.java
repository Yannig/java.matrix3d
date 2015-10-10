package matrix3D.geom3D.samples;

/**
 * Crée directement une figure de type damier de 1 de coté
 * @author  Yannig Perré
 * @version 0.3
 * Modifications :
 * le 19 novembre par Perré Y. (v0.3) :
 * - remise à niveau depuis les modifications de Matrix v0.2
 */
public abstract class Damier extends Function3D {
    public Damier() {super();}
    public Damier(double cote,int sizeX,int sizeZ) {
        super();
        init(cote,cote,sizeX,sizeZ);
    }
    public double f(double x,double z) {
        return 0;
    }
}