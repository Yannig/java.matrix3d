package matrix3D.structure;
import matrix3D.engine.fx.*;
import matrix3D.engine.*;
import matrix3D.geom3D.*;
import java.awt.Color;

/**
 * Cette classe nous permet de stocker et de calculer des lumiéres
 * @author Yannig Perré
 * @version 0.1
 */
public class FxVector extends CommonVector {
    /** Composante ambiente de notre environnement */
    public Light ambient = new AmbientLight(java.awt.Color.white,0.1f);
    /** Composante de lumière diverse */
    public Effect effects [] = new Light[DEFAULT_ALLOCATION_SIZE];
    /** Valeur de la transparence courante */
    public float radiance = 0;
    /** Figure contenant tous les points de lumière pour transformation */
    public Shape lights = new Shape() {
        public void doTransf(int time) {}
    };
    /** Creates new LightVector */
    public FxVector() {
        add(ambient);
    }
    /** Ajoute un point dans la chaîne des points */
    public FxVector add(Light l) {
        try {
            lights.add(l.position);
            effects[size] = l;
            size++;
        } catch (Exception e) {
            allocateMore();
            add(l);
        }
        return this;
    }
    /** Suppression d'un point par référence */
    public FxVector remove(Light l) {
        for(int i=0,j=0;i<size;i++) {
            if (!effects[i].equals(l)) j++;
            effects[j]=effects[i];
        }
        return this;
    }
    /** Suppression d'un point par index */
    public FxVector remove(int index) {
        for(int i=0,j=0;i<size;i++) {
            if (i!=index) j++;
            effects[j]=effects[i];
        }
        return this;
    }
    /** Réalloue de la mémoire */
    protected void allocateMore() {
        Effect temp [] = new Effect[effects.length*2];
        for(int i=0;i<temp.length;i++) {
            if (i<size) temp[i]=effects[i];
            else temp[i]=null;
        }
        effects = temp;
    }
    /** Transforme tous les effets en fonction de la caméra */
    public void preProcess(AffineTransform3D camera) {
        Light current_light;
        lights.transf(camera);
        for(int i=0;i<size;i++) {
            if (effects[i].isLight()) {
                current_light = (Light)effects[i];
            }
        }
    }
    /** Renvoie la couleur pour le point donnée */
    public Color getColor(Color col,Vertex position,Vertex norm,int rad,Camera cam) {
        Vertex distance = new Vertex();
        Light current_light;
        radiance = 0;
        int red   = col.getRed();
        int green = col.getGreen();
        int blue  = col.getBlue();
        for (int i=0;i<size;i++) {
            if (effects[i].isLight()) {
                current_light = (Light)(effects[i]);
                float current_radiance = rad*current_light.getRadiance(position,norm,cam);
                if (current_radiance>0) {
                    red+=current_light.red*current_radiance;
                    green+=current_light.green*current_radiance;
                    blue+=current_light.blue*current_radiance;
                    radiance+=current_radiance;
                }
            }
        }
        red/=radiance+1;green/=radiance+1;blue/=radiance+1;
        float HSB [] = Color.RGBtoHSB(red,green,blue,null);
        radiance *= (float)(((double)(red+green+blue))/765.);
        if (radiance>1) {
            HSB[1]/=radiance*radiance;
            return new Color(col.HSBtoRGB(HSB[0],HSB[1],1));
        }
        return new Color(col.HSBtoRGB(HSB[0],HSB[1],radiance));
    }
}