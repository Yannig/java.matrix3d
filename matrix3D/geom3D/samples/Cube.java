package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;

/**
 * Crée directement une figure de type cube
 * @author  Yannig Perré
 * @version 0.3
 * Modifications :
 * le 19 novembre par Perré Y. (v0.3) :
 * - remise à niveau depuis les modifications de Matrix v0.2
 */
public abstract class Cube extends Rectangle {
    public Cube() {super(1,1,1);}
    public Cube(double cote) {
        super(cote,cote,cote);
    }
}