package matrix3D.engine.fx;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;
/**
 * Cette version de la lumiére permet de facilement gérer les lumières diffuses fixées
 * à un point
 * @author Yannig Perré
 * @version 0.1
 */
public class PointLight extends Light {
    /** Vecteur direction */
    protected Vertex relativeDirection = new Vertex();
    /** Constructeur */
    public PointLight() {}
    public PointLight(Color col) {
        super(col);
        type|=POINT_LIGHT;
    }
    public PointLight(Vertex pos) {
        position.set(pos);
        type|=POINT_LIGHT;
    }
    public PointLight(Color col,float rad) {
        super(col,rad);
        type|=POINT_LIGHT;
    }
    public PointLight(float rad,Vertex pos) {
        super(Color.white,rad);
        position.set(pos);
        type|=POINT_LIGHT;
    }
    public PointLight(Color col,Vertex pos) {
        super(col);
        position.set(pos);
        type|=POINT_LIGHT;
    }
    public PointLight(Color col,float rad,Vertex pos) {
        super(col,rad);
        position.set(pos);
        type|=POINT_LIGHT;
    }
    /** Renvoi l'illumination à 1 point par rapport à la source  */
    public float getRadiance(Vertex pos, Vertex norm, Camera cam) {
        double distance = relativeDirection.set(pos,position).distance();
        relativeDirection.norm(distance);
        return (float)(relativeDirection.mul(norm)*radiance);
    }
}