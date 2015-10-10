package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;
import java.lang.Math;
/**
 * Crée directement une figure de type Sphere
 * @author  Yannig Perré
 * @version 0.3
 * Modifications :
 */
public abstract class Sphere extends Shape {
    public Sphere() {init(1,8,16);}
    public Sphere(double diameter, int n) {
        init(diameter,n,n);
    }
    public Sphere(double diameter, int n, int rotation) {
        init(diameter,n,rotation);
    }
    public void init(double diameter, int n, int rotation) {
        center.set(0,0,0);
        Vertex down=new Vertex(0,-diameter/2,0),up=new Vertex(0,diameter/2,0);
        addPoint(down);
        AffineTransform3D temp = new AffineTransform3D();
        for (int i=1;i<n;i++) {
            double alpha = (Math.PI*((double)i))/((double)n);
            temp.setToRotation(0,0,alpha);
            addPoint(new Vertex(down.transform(temp)));
        }
        addPoint(up);
        doRotation(rotation,1,n,LINK_BOTH_WITH_POINT,down,up);
    }
}