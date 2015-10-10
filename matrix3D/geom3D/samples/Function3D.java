package matrix3D.geom3D.samples;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;

/**
 * Crée directement une surface dont la forme est donnée par une équation du type :
 * y = f(x,z);
 * @author  Yannig Perré
 * @version 0.1.0
 * Modifications :
 * le 16 novembre par Perré Y. : version d'origine de la classe
 * le 19 novembre par Perré Y. :
 * - Suppression de init dans le constructeur vide (bizarement, il est appelé
 *   systèmatiquement dans les sous classes).
 */
public abstract class Function3D extends Shape {
    /** Stocke le pas de la grille */
    protected double _pasX = 1;
    protected double _pasY = 1;
    protected int _sizeX = 10;
    protected int _sizeY = 10;
    /** Constructeurs par défaut */
    public Function3D() {}
    /** On précise le pas et le quadrillage */
    public Function3D(double pas,int size) {
        init(pas,pas,size,size);
    }
    /** On précise le pas et le quadrillage en x et en y*/
    public Function3D(double pas,int sizeX,int sizeY) {
        init(pas,pas,sizeX,sizeY);
    }
    /** On précise les 2 pas de quadrillages */
    public Function3D(double pasX,double pasY,int sizeX,int sizeY) {
        init(pasX,pasY,sizeX,sizeY);
    }
    /** Fonction a implanter pour pouvoir instancier la classe */
    public abstract double f(double x,double z);
    
    /** Initialise la figure à l'aide de la fonction f(x,y) */
    public void init(double pasX,double pasY,int sizeX,int sizeY) {
        double x,z;
        center.set(((double) _sizeX) * _pasX * 0.5,((double) _sizeY) * _pasY * 0.5,0);
        _pasX=pasX; _pasY=pasY; _sizeX=sizeX; _sizeY=sizeY;
        Vertex [][] temp = new Vertex[sizeX][sizeY];
        Surface surf_temp;
        for(int i=0;i<_sizeX;i++) {
            for(int j=0;j<_sizeY;j++) {
                x = -(((double) _sizeX) * _pasX * 0.5) + (_pasX * (double) i);
                z = -(((double) _sizeY) * _pasY * 0.5) + (_pasY * (double) j);
                addPoint(temp[i][j]=new Vertex(x,f(x,z),z));
                if (i>0) {
                    if ((j>0)&&(j<(_sizeY-1))) {
                        addElement(new Surface(this,Color.gray)
                                                .addVertex(temp[i-1][j])
                                                .addVertex(temp[i][j-1])
                                                .addVertex(temp[i][j])
                                                );
                        addElement(new Surface(this,Color.white)
                                                .addVertex(temp[i][j])
                                                .addVertex(temp[i-1][j+1])
                                                .addVertex(temp[i-1][j])
                                                );
                    } else if (j==0) {
                        addElement(new Surface(this,Color.white)
                                                .addVertex(temp[i][j])
                                                .addVertex(temp[i-1][j+1])
                                                .addVertex(temp[i-1][j])
                                                );
                    } else {
                        addElement(new Surface(this,Color.gray)
                                                .addVertex(temp[i-1][j])
                                                .addVertex(temp[i][j-1])
                                                .addVertex(temp[i][j])
                                                );
                    }
                }
            }
        }
    }
}