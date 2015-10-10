package matrix3D.structure;

/**
 * Cette classe est la partie commune de tous les vecteurs utilisés dans les sous
 * classes Vector
 * @author Yannig Perré
 * @version 0.1
 */
public abstract class CommonVector extends Object {
    /** Taille allouée par défaut à la structure */
    protected static final int DEFAULT_ALLOCATION_SIZE = 0x0100;
    /** Nombre de Point du vecteur */
    public int size = 0;

    /** Constructeur par défaut */
    public CommonVector() {
        clear();
    }
    /** Efface tous les objets du vecteur */
    public void clear() {
        size = 0;
    }
    /** Compte le nombre d'occurence de la figure */
    public int size() {
        return size;
    }
    /** Test si la structure contient quelque chose */
    public boolean empty() {
        return (size==0);
    }
    public String toString() {
        return "taille : " + size;
    }
}