package matrix3D.geom3D;
import matrix3D.geom3D.*;
import matrix3D.structure.*;
import matrix3D.engine.*;
import java.awt.Color;
/**
 * Créé à l'occasion de la création de la classe Function2D.
 * @author Yannig Perré
 * @version 0.1
 */
public class Line extends DrawableElement {
    /** Crée une nouvelle ligne */
    public Line(Shape f) {
        super(f);
    }
    public Line(Shape f,Vertex p1,Vertex p2) {
        super(f);
        add(p1);add(p2);
    }
    /** Dessine l'instance de la ligne */
    public void draw(Camera c,int option) {
        c.graph.setColor(getColor());
        for(int i=0;i<size-1;i++) {
            c.graph.drawLine(_x[i],_y[i],_x[i+1],_y[i+1]);
        }
    }
    public void preProcessRendering() {
        norm.set(1,0,0);
    }
    public boolean isClockWise() {
        return false;
    }
    /** Test de visibilité de la surface  */
    public boolean isVisible(Camera c) {
        return true;
    }
    /** Rajoute un point  */
    public DrawableElement addPoint(Vertex p) {
        return this;
    }
    /** Renvoie la distance de l'élément  */
    public double getDistance() {
        return points[0].getDistance();
    }
    /** Change la texture de l'élément  */
    public DrawableElement setTexture(Texture txtr) {
        return this;
    }
    /** récupére la texture  */
    public Texture getTexture() {
        return null;
    }
}