/*
 * RenderingHints.java
 *
 * Created on 15 janvier 2002, 23:45
 */

package matrix3D.engine;

/**
 *
 * @author  Yansolo
 * @version 
 */
public interface EngineRenderingHints {
    /** Pas de consigne de rendu */
    public final static int NONE                   = 0x0000;
    /** Définir la figure en mode Fil de fer */
    public final static int WIRE_FRAME             = 0x0001;
    /** Définir la figure comme pleine */
    public final static int REGULAR_FRAME          = 0x0002;
    /** Définir la figure comme pleine + affichage fil de fer */
    public final static int REGULAR_WIRED_FRAME    = 0x0004;
    /** Définir la figure comme transparente */
    public final static int TRANSPARENT_FRAME      = 0x0008;
    /** Surface texturée */
    public final static int TEXTURED_FACET         = 0x0010;
    
    /** Constante pour le rendu des textures */
    /** Rendu de la texture par extrapolation linéaire */
    public final static int LINEAR_EXTRAPOLATION   = 0x0010;
    /** Rendu de la texture par extrapolation bilinéaire */
    public final static int BILINEAR_EXTRAPOLATION = 0x0020;
    /** On ne fait pas d'opétation sur la texture */
    public final static int NO_TRANSFORMATION      = 0x0040;
    /** rotation de 90° */
    public final static int ROTATION_90            = 0x0080;
    /** rotation de 180° */
    public final static int ROTATION_180           = 0x0100;
    /** rotation de 270° */
    public final static int ROTATION_270           = 0x0200;
    /** Symmétrie de la texture selon les X */
    public final static int SYMETRY_X              = 0x0400;
    /** Symmétrie de la texture selon les Y */
    public final static int SYMETRY_Y              = 0x0800;
    
    /** Nombre de rendu pris en considération pour statistique */
    public final static int RENDERING_STATS_NUMBER = 10;

    /** construction par rotation : type de lien au haut et au bas */
    /** Relie le bas avec un point */
    public final static int LINK_BOTTOM_WITH_POINT = 0x0001;
    /** Relie le sommet avec un point */
    public final static int LINK_UP_WITH_POINT     = 0x0002;
    /** Relie le sommet et le bas avec un point */
    public final static int LINK_BOTH_WITH_POINT   = LINK_BOTTOM_WITH_POINT|LINK_UP_WITH_POINT;
    /** crée une surface au bas de la rotation */
    public final static int CREATE_BOTTOM_SURFACE  = 0x0004;
    /** crée une surface au haut de la rotation */
    public final static int CREATE_UP_SURFACE      = 0x0008;
    /** crée une surface en bas et en haut */
    public final static int CREATE_BOTH_SURFACE    = CREATE_BOTTOM_SURFACE|CREATE_UP_SURFACE;
    
    /** Constantes pour le clipping */
    public final static int UP_CLIPPING = 10000;
    public final static int DOWN_CLIPPING = -9000;
    
    /** Option de rendu de la surface */
    public final static int NORMAL   = 1;
    public final static int RADIENT = -1;
    
}

