package matrix3D.engine.fx;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;
/**
 * Cette classe est la super classe de toutes les classe qui seront utilisées
 * pour gérer la lumiére
 * @author Yannig Perré
 * @version 0.1
 * Modifications :
 * le 26 novembre par Perré Y. : version d'origine de la classe
 * le 27 novembre par Perré Y. :
 * - Changement d'espace de représentation de la couleur : 
     RGB (red green blue) -> TSL (teinte, saturation, luminance)
 * le 28 novembre par Perré Y. :
 * - On stocke la couleur sous forme RGB pour calculer le résultat avec la couleur
 *   de la surface + radiance.
 */
public abstract class Light extends Effect {
    /** Spécifie que la lumière n'influence pas la couleur */
    public static final int WHITE_LIGHT   = 0x0001;
    /** Spécifie que la lumière modifie la couleur */
    public static final int COLOR_LIGHT   = 0x0002;
    /** Spécifie qu'il s'agit d'une lumière omni */
    public static final int DIFFUSE_LIGHT    = 0x0004;
    /** Spécifie qu'il s'agit d'une lumière d'ambiance */
    public static final int AMBIENT_LIGHT = 0x0008;
    /** Spécifie qu'il s'agit d'une à 1 point */
    public static final int POINT_LIGHT   = 0x0010;
    /** Position de la lumière */
    public Vertex position = new Vertex();
    /** Composante couleur + intensité + luminosité */
    public float HSB [] = {0f,0f,0f};
    /** Luminance de la lumière */
    public float radiance = 1.0f;
    /** Composante RGB */
    public Color color = null;
    public int red=0,green=0,blue=0;

    /** Super constructeur par défaut */
    public Light() {
        init(1f,Color.black);
    }
    public Light(Color col) {
        init(1f,col);
    }
    public Light(Color col,float rad) {
        init(rad,col);
    }
    /** Initialise la lumiére */
    public void init(float rad,Color col) {
        type|=LIGHT;
        color = col;
        if (col!=Color.white) type |= COLOR_LIGHT;
        red = col.getRed();
        green = col.getGreen();
        blue = col.getBlue();
        Color.RGBtoHSB(col.getRed(),col.getGreen(),col.getBlue(),HSB);
        radiance = rad;
    }
    /** Modifie la composante de radiance */
    public void setRadiance(float rad) {
        radiance = rad;
    }
    /** Modifie la couleur */
    public void setColor(Color col) {
        init(radiance,col);
    }
    /** teste les propriétés de la lumière */
    /** Teste pour savoir s'il s'agit d'une lumière diffuse */
    public boolean isDiffuse() {
        return (type&DIFFUSE_LIGHT)!=0;
    }
    /** Teste pour savoir s'il s'agit d'une lumière ambiente */
    public boolean isAmbient() {
        return (type&AMBIENT_LIGHT)!=0;
    }
    /** Teste pour savoir s'il s'agit d'une lumière en un point */
    public boolean isPoint() {
        return (type&POINT_LIGHT)!=0;
    }
    /** Renvoie la couleur en fonction de la couleur */
    public Color getColor(Color col) {
        /** on obtient les composantes du modéle */
        int r = col.getRed();
        int g = col.getGreen();
        int b = col.getBlue();
        float temp [] = Color.RGBtoHSB((r+red)>>1,
                                       (g+green)>>1,
                                       (b+blue)>>1,null);
        return new Color(Color.HSBtoRGB(temp[0],temp[1],temp[2]*radiance));
    }
    /** Renvoi l'illumination à 1 point par rapport à la source */
    public abstract float getRadiance(Vertex pos,Vertex norm,Camera cam);
}