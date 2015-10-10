package matrix3D.engine.fx;

/**
 * 
 * @author  Administrateur
 * @version 
 */
public class Effect extends Object {
    /** Type de l'effet */
    /** Une lumière */
    public final static int LIGHT = 0x0100;
    /** Un brouillard */
    public final static int FOG = 0x0200;
    /** Type de l'effet */
    public int type = LIGHT;
    /** Creates new Effect */
    public Effect() {}
    public boolean isLight() {
        return ((type&LIGHT)!=0);
    }
    public boolean isFog() {
        return ((type&FOG)!=0);
    }
}