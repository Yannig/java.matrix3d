package matrix3D.engine.fx;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;
/**
 * Comme son nom l'indique, cette classe permet de stocker les effets de lumiére
 * de type ambiant que l'on peut associer sur notre univers.
 * @author Yannig Perré
 * @version 0.1
 */
public class AmbientLight extends Light {
    /** Création d'une nouvelle instance */
    public AmbientLight() {
        type|=AMBIENT_LIGHT;
    }
    public AmbientLight(Color col) {
        super(col);
        type|=AMBIENT_LIGHT;
    }
    public AmbientLight(Color col,float radiance) {
        super(col,radiance);
        type|=AMBIENT_LIGHT;
    }
    /** Renvoi l'illumination à 1 point par rapport à la source  */
    public float getRadiance(Vertex pos, Vertex norm, Camera cam) {
        return radiance;
    }
}