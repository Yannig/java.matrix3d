package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;

/**
 * Crée directement une fonction 2D dont la forme est donnée par une équation du type :
 * y = f(x);
 * @author  Yannig Perré
 * @version 0.1.0
 * Modifications :
 * le 21 novembre par Perré Y. : version d'origine de la classe
 */
public abstract class Function2D extends Shape {
    /** Stocke le pas de la grille */
    protected double _pas = 0.1;
    protected int _size = 50;
    /** Constructeurs par défaut */
    public Function2D() {
    }
    /** On précise le pas et le quadrillage */
    public Function2D(double pas,int s) {
        init(pas,s);
    }
    /** Fonction a implanter pour pouvoir instancier la classe */
    public abstract double f(double x);
    
    /** Initialise la figure à l'aide de la fonction f(x,y) */
    public void init(double pas,int s) {
        double x;
        center.set(((double) _size) * _pas * 0.5,0,0);
        _pas=pas; _size=s;
        Vertex [] temp = new Vertex[s];
        for(int i=0;i<_size;i++) {
            x = -(((double) _size) * _pas * 0.5) + (_pas * (double) i);
            addPoint(temp[i]=new Vertex(x,f(x),0));
        }
        for(int i=0;i<_size-1;i++) addElement(new Line(this,temp[i],temp[i+1]));
        setType(WIRE_FRAME);
    }
}