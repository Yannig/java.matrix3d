package matrix3D.engine.fx;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;
/**
 * Cette version de la lumiére permet de facilement gérer les lumières diffuses.
 * @author Yannig Perré
 * @version 0.1
 */
public class DiffuseLight extends PointLight {
    /** Création d'une nouvelle instance */
    public DiffuseLight() {
        position.x=1;
        init();
    }
    public DiffuseLight(Color col) {
        super(col);
        position.x=1;
        init();
    }
    public DiffuseLight(Color col,float rad) {
        super(col,rad);
        position.x=1;
        init();
    }
    public DiffuseLight(Color col,Vertex dir) {
        super(col);
        position.set(dir);
        init();
    }
    public DiffuseLight(float rad,Vertex dir) {
        super(Color.white,rad);
        position.set(dir);
        init();
    }
    public DiffuseLight(Color col,float rad,Vertex dir) {
        super(col,rad);
        position.set(dir);
        init();
    }
    public void init() {
        position.set(position.x*(Double.MAX_VALUE/100),
                     position.y*(Double.MAX_VALUE/100),
                     position.z*(Double.MAX_VALUE/100));
        type=DIFFUSE_LIGHT|LIGHT;
    }
    /** Renvoi l'illumination à 1 point par rapport à la source  */
    public float getRadiance(Vertex pos, Vertex norm, Camera cam) {
        relativeDirection.set(pos,position).norm();
        return (float)(relativeDirection.mul(norm)*radiance);
    }
}