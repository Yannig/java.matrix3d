package matrix3D.geom3D;
import matrix3D.structure.*;
import matrix3D.geom3D.*;
import matrix3D.engine.*;
import java.awt.Color;
import java.lang.Math;
import java.awt.*;
/**
 * Cet objet va permettre de représenter la surface des objets 3D et déterminer si
 * on peut l'afficher ou non.
 * @author  Yannig Perré
 * @version 0.6.0
 */
public class Surface extends DrawableElement {
    /** Détermine si on affiche l'élément lorsque les points sont anticlockwise */
    public boolean clockWise = true;
    /** Points utilisés pour obtenir les normes des surfaces et le sens trigo */
    public int first=0,second=0,third=0;
    /** Vecteurs utilisés pour calculer la norme à la surface */
    private Vertex ab=new Vertex(),ac=new Vertex();
    /** Texture de la surface */
    public Texture texture = null;
    
    /** Initialise la structure de la surface*/
    public Surface(Shape f) {
        super(f);
        clear();
    }
    public Surface(Shape f,boolean clock) {
        super(f);
        clockWise = clock;
        clear();
    }
    public Surface(Shape f,Color c) {
        super(f);
        color=c;
        clear();
    }
    public Surface(Shape f,Color c,boolean clock) {
        super(f);
        color=c;
        clockWise = clock;
        clear();
    }
    public Surface(Shape f,DrawableElement s) {
        super(f);
        int i;
        color = s.getColor();
        type = s.getType();
        replacePointFrom(s,father);
    }
    public Surface(Shape f,DrawableElement s,boolean clock) {
        super(f);
        int i;
        color = s.getColor();
        type = s.getType();
        clockWise = clock;
        replacePointFrom(s,father);
    }
    public Surface(Shape f,DrawableElement s,Color c) {
        super(f);
        int i;
        color = c;
        type = s.getType();
        replacePointFrom(s,father);
    }
    public Surface(Shape f,DrawableElement s,Color c,boolean clock) {
        super(f);
        int i;
        color = c;
        type = s.getType();
        clockWise = clock;
        replacePointFrom(s,father);
    }
    /** Ajoute un point dans la chaîne des points */
    public Surface addVertex(Vertex p) {
        super.add(p);
        preProcess();
        p.surfaces.add(this);
        return this;
    }
    /** Change la texture de la surface */
    public DrawableElement setTexture(Texture txtr) {
        texture=txtr;
        return this;
    }
    /** récupére la texture */
    public Texture getTexture() {
        return ((texture!=null) ? texture : father.getTexture());
    }
    public boolean equals(Object o) {
        try {
            int i;
            for(i=0;i<size;i++)
                if (!points[i].equals(((Surface)o).points[i])) return false;
            if (((Surface)o).points[i]==points[i]) return true;
        } catch (Exception e) {}
        return false;
    }
    /** Test de toutes les propriétés de la surface pour savoir si la surface est visible */
    public boolean test(Camera c) {
        return ((isClockWise())&&(isInFrontOfCamera())&&(almostOnePointVisible(c)));
    }
    public boolean almostOnePointVisible(Camera c) {
        int visible,oneVisible=0;
        for(int i=0;i<size;i++) {
            visible = 0;
            if (((_x[i]>=0)&&(_x[i]<=c.getSizeX()))&&
                ((_y[i]<UP_CLIPPING)&&(_y[i]>DOWN_CLIPPING))) visible |=0x0001;
            if (((_y[i]>=0)&&(_y[i]<=c.getSizeY()))&&
                ((_x[i]<UP_CLIPPING)&&(_x[i]>DOWN_CLIPPING))) visible |=0x0002;
            if (visible==3) return true;
            oneVisible|=visible;
        }
        return oneVisible==3;
    }
    /** Renvoie si la surface est bien devant la caméra ou non */
    public boolean isInFrontOfCamera() {
        for(int i=0;i<size;i++) {
            if (points[i].z<0) return false;
        }
        return true;
    }
    /** Rend la surface sensible (pour son affichage) au mode de rendu clockwise */
    public DrawableElement setClockWise() {
        clockWise = true;
        return this;
    }
    /** Rend la surface insensible au mode de rendu clockwise */
    public DrawableElement unsetClockWise() {
        clockWise = false;
        return this;
    }
    /** Si la surface est dans le sens inverse des aiguilles d'une montre, on
        ne l'affiche pas */
    public boolean isClockWise() {
        return (!clockWise)||(isClockWise(getFirst(),getSecond(),getThird()));
    }
    public boolean isClockWise(int a,int b,int c) {
        return (_x[b]-_x[a])*(_y[c]-_y[a])-(_x[c]-_x[a])*(_y[b]-_y[a])<0;
    }
    /** Ces méthodes ont pour but de déterminer les 3 points à prendre en référence 
        Pour calculer si la surface est visible ou non */
    protected int getFirst() {
        return 0;
    }
    /** Renvoie le 2nd point à prendre en considération */
    protected int getSecond() {
        return size/3;
    }
    /** Idem pour le 3ieme point */
    protected int getThird() {
        return (2*size)/3;
    }
    /** Dessine la surface selon ses caractéristiques */
    public void draw(Camera c) {
        draw(c,getType());
    }
    /** Dessine la surface selon les options passées */
    public void draw(Camera c, int option) {
        int i;
        Graphics2D g = c.getGraphics();
        Color col = getNormColor(getColor(),c,points[0],getNorm(),radient);
        g.setColor(col);
        pushGraphicContext(g);
        switch(option) {
            case WIRE_FRAME :
                g.setColor(father.getColor());
                g.drawPolygon(_x,_y,size);
                break;
            case REGULAR_WIRED_FRAME :
                g.fillPolygon(_x,_y,size);
                g.setColor(java.awt.Color.black);
                g.drawPolygon(_x,_y,size);
                break;
            case REGULAR_FRAME :
            default :
                if ((texture=getTexture())==null) {
                    g.fillPolygon(_x,_y,size);
                } else {
                    switch(size) {
                        case 3 :
                            texture.doProjection(_x,_y,g,false,c.engine.effects.radiance);
                            break;
                        case 4 :
                            texture.doQuadProjection(_x,_y,g,false,c.engine.effects.radiance);
                            break;
                        default :
                            //g.setColor(father.getColor());
                            g.fillPolygon(_x,_y,size);
                            break;
                    }
                }
                break;
        }
        popGraphicContext(g);
    }
    private java.awt.Paint paintContext = null;
    private java.awt.geom.AffineTransform affineContext = null;
    /** Sauvegarde le contexte graphique */
    public void pushGraphicContext(Graphics2D graphic) {
        paintContext = graphic.getPaint();
        affineContext = graphic.getTransform();
    }
    /** Restaure le contexte graphique */
    public void popGraphicContext(Graphics2D graphic) {
        graphic.setPaint(paintContext);
        graphic.setTransform(affineContext);
    }
    /** Renvoie le père de la Shape */
    public Shape getFather() {
        return father;
    }
    /** Rajoute un point sur l'élément (démodé, utiliser addVertex) */
    public DrawableElement addPoint(Vertex p) {
        addVertex(p);
        return this;
    }
    /** Renvoie la couleur de la surface */
    public static Color getNormColor(Color col,Camera c,Vertex position,Vertex norm, int rad) {
        return c.engine.getColor(col,position,norm,rad);
    }
    /** Procéde aux calculs préalable à son dessin */
    public void preProcess(Camera c) {
        for(int i=0;i<size;i++) points[i].doProjection(c.getSizeX(),
                                                       c.getSizeX(),
                                                       c.getSizeY(),
                                                       c.getOffsetX(),
                                                       c.getOffsetY(),
                                                       _x,_y,i);
    }
    /** Renvoie si la surface est visible */
    public boolean isVisible(Camera c) {
        preProcess(c);
        return test(c);
    }
    /** Méthode calculant la norme de la surface après création */
    public void preProcess() {
        first = getFirst();
        second = getSecond();
        third = getThird();
    }
    /** Calcul fait au rendu (calcul de la norme) */
    public void preProcessRendering() {
        ab.set(points[first],points[second]);
        ac.set(points[first],points[third]);
        norm.set((ab.y*ac.z)-(ab.z*ac.y),
                 (ab.z*ac.x)-(ab.x*ac.z),
                 (ab.x*ac.y)-(ab.y*ac.x)).norm();
    }
    /** Renvoie la norme à la surface */
    public Vertex getNorm() {
        return norm;
    }
    /** Calcul la distance par rapport à l'origine */
    public double distance() {
        double avg=points[first].distance + points[second].distance + points[third].distance;
        return avg/3;
    }
    public double getDistance() {
        double avg=points[first].distance + points[second].distance + points[third].distance;
        return avg/3;
    }
    /** Inverse le sens interne trigo des points */
    public Surface inverse() {
        PointFifo temp = new PointFifo();
        for(int i=0;i<size;i++) {
            temp.push(points[i]);
        }
        size=0;
        while(!temp.isEmpty()) {
            addVertex(temp.pop());
        }
        return this;
    }
    public String toString() {
        String result = new String();
        for (int i=0;i<size;i++) {
            result += i + " : " + points[i] + " ";
        }
        return result + "vecteur norme : " + norm;
    }
}