package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;

/**
 * Crée directement une figure de type Rectangle
 * @author  Yannig Perré
 * @version 0.2.1
 */
public abstract class Rectangle extends Shape {
    public Rectangle() {
        init(1,1,1);
    }
    public Rectangle(double x,double y,double z) {
        init(x,y,z);
    }
    public void init(double x,double y,double z) {
        Vertex temp [] = new Vertex [8];
        double cx,cy,cz;
        for(int i=0;i<8;i++) {
            if ((i&0x0001)!=0) cz=z/2; else cz=-z/2;
            if ((i&0x0002)!=0) cy=y/2; else cy=-y/2;
            if ((i&0x0004)!=0) cx=x/2; else cx=-x/2;
            addPoint(temp[i]=new Vertex(cx,cy,cz));
        }
        addElement(new Surface(this) // 1 Anti Clock Wise
                   .addVertex(temp[0])
                   .addVertex(temp[4])
                   .addVertex(temp[6])
                   .addVertex(temp[2]));
        addElement(new Surface(this) // 2 Anti Clock Wise
                   .addVertex(temp[0])
                   .addVertex(temp[1])
                   .addVertex(temp[5])
                   .addVertex(temp[4]));
        addElement(new Surface(this) // 3 Anti Clock Wise
                   .addVertex(temp[2])
                   .addVertex(temp[6])
                   .addVertex(temp[7])
                   .addVertex(temp[3]));
        addElement(new Surface(this) // 4 Anti Clock Wise
                   .addVertex(temp[4])
                   .addVertex(temp[5])
                   .addVertex(temp[7])
                   .addVertex(temp[6]));
        addElement(new Surface(this) // 5 Anti Clock Wise
                   .addVertex(temp[0])
                   .addVertex(temp[2])
                   .addVertex(temp[3])
                   .addVertex(temp[1]));
        addElement(new Surface(this) // 6 Anti Clock Wise
                   .addVertex(temp[1])
                   .addVertex(temp[3])
                   .addVertex(temp[7])
                   .addVertex(temp[5]));
    }
}