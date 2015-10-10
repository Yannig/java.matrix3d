package matrix3D.geom3D;
import java.awt.Color;
import matrix3D.structure.*;
import matrix3D.engine.*;
/**
 * Cette classe spécifie les méthodes qu'un objet doit faire pour pouvoir afficher quelque
 * chose dans le moteur de rendu. On pourra ainsi retrouver les facettes d'une surface,
 * les lignes reliant 2 points ou les particules (show must go on !).
 * @author Yannig Perré
 * @version 0.5.0
 */
public abstract class DrawableElement extends PointVector implements matrix3D.engine.EngineRenderingHints {
    /** Lien vers la Shape auxquels, on est rattaché */
    public Shape father = null;
    /** Couleur par défaut de l'élément */
    public Color color = null;
    /** Options de rendu de l'élément */
    public int type = NONE;
    /** Si l'objet est une source de lumière irradiant, on inverse le sens de l'éclairage. */
    public int radient = NORMAL;
    /** Norme de notre élément */
    public Vertex norm = new Vertex();
    public DrawableElement(Shape f) {
        father = f;
    }
    /** C'est dans cette méthode que l'on dessine notre élément */
    public abstract void draw(Camera c,int option);
    public void draw(Camera c) {draw(c,getType());}
    /** Méthode appelée avant le rendu */
    public abstract void preProcessRendering();
    /** Méthode changeant la couleur de l'élément */
    public DrawableElement setColor(Color c) {
        color = color;
        return this;
    }
    public Color getColor() {
        return ((color==null) ? father.getColor() : color);
    }
    /** Méthode supprimant la couleur de l'élément */
    public DrawableElement clearColor() {
        color = null;
        return this;
    }
    /** Type de l'élément */
    public DrawableElement setType(int typeRend) {
        type = typeRend;
        return this;
    }
    public int getType() {
        return type;
    }
    /** Rend l'élement rayonnant */
    public DrawableElement setRadient() {
        radient = RADIENT;
        return this;
    }
    /** Rend l'élément absorbant (normal) */
    public DrawableElement setAbsorb() {
        radient = NORMAL;
        return this;
    }
    /** Test de visibilité de la surface */
    public abstract boolean isVisible(Camera c);
    /** Renvoie la distance de l'élément */
    public abstract double getDistance();
    /** Rajoute un point */
    public abstract DrawableElement addPoint(Vertex p);
    /** Change la texture de l'élément */
    public abstract DrawableElement setTexture(Texture txtr);
    /** récupére la texture */
    public abstract Texture getTexture();
}