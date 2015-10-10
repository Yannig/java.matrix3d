package matrix3D.engine;
import matrix3D.engine.fx.*;
import matrix3D.geom3D.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.image.*;

/**
 * Cette classe permet de facilement représenter un déplacement dans le monde 3D.
 * Elle est nait des suites d'un manque d'intéraction simple pour se déplacer dans un
 * environnement 3D.
 * @author  Yannig Perré
 * @version 0.2.1
 */
public class Camera extends Object {
    /** Lien vers la zone de rendu */
    public Graphics2D graph = null;
    /** Buffer de rendu */
    public BufferedImage _buffer = null;
    /** objet servant au rendu temporaire */
    public Graphics2D _bufferedRender = null;
    /** Taille de la zone de rendu */
    public int sizeX = 640;
    public int sizeY = 480;
    /** Emplacement de notre camera */
    public Vertex center = new Vertex();
    /** Angle d'orientation */
    public Vertex orientation = new Vertex();
    /** Notre transformation affine */
    protected AffineTransform3D _transf = new AffineTransform3D();
   
    /** Lien vers l'objet de rendu */
    public Engine engine = null;
    
    /** Initialise la caméra */
    public Camera() {
        clear();
    }
    public Camera(Graphics2D g,int width,int height) {
        setGraphics(g,width,height);
        clear();
    }
    /** Remet la caméra à l'origine sans rotation */
    public void clear() {
    }
    /** Modifie l'objet de rendu */
    public void setEngine(Engine rend) {
        engine = rend;
    }
    /** Renvoie l'objet rendu */
    public Engine getEngine() {
        return engine;
    }
    /** recalibre la zone de rendu */
    public void resize(int width,int height) {
        sizeX=width;
        sizeY=height;
        _buffer = new BufferedImage(sizeX,sizeY,BufferedImage.TYPE_INT_RGB);
        _bufferedRender = _buffer.createGraphics();
    }
    /** Méthode de consultation sur la taille de la zone de dessin */
    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }
    public int getOffsetX() {
        return getSizeX()/2;
    }
    public int getOffsetY() {
        return getSizeY()/2;
    }
    public int getDistance() {
        return getSizeX();
    }
    /** Renvoie la Matrice de transformation associée à la caméra */
    public AffineTransform3D getCamera() {
        AffineTransform3D translate = AffineTransform3D.getTranslateInv(center);
        AffineTransform3D direction = AffineTransform3D
           .getRotateInstance(center,orientation.x,orientation.y,0);
        AffineTransform3D rotationZ = AffineTransform3D.getRotateInstance(0,0,orientation.z);
        _transf = rotationZ.mul(translate.mul(direction));
        _transf.setCamera();
        return _transf;
    }
    /** Renvoie la Matrice inverse de transformation associée à la caméra */
    public AffineTransform3D getInvCamera() {
        AffineTransform3D translate = AffineTransform3D.getTranslateInstance(center);
        AffineTransform3D direction = AffineTransform3D.getRotateInv(center,orientation);
        return translate.mul(direction);
    }
    /** Change la zone de rendu */
    public void setGraphics(Graphics2D g,int width,int height) {
        graph= g;
        resize(width,height);
    }
    /** Renvoie la zone de dessin */
    public Graphics2D getGraphics() {
        return _bufferedRender;
    }
    /** Efface la zone de dessin */
    public void clearGraphics(Color background) {
        _bufferedRender.setColor(background);
        _bufferedRender.fillRect(0,0,sizeX,sizeY);
    }
    /** Initialise la Camera */
    public void reset() {
        clear();
    }
    /** Dessine la version finalisé de la scène */
    public void draw(java.awt.image.ImageObserver window) {
        graph.drawImage(_buffer,0,0,window);
    }
    /** Mouvement de la camera */
    public void moveTo(double x,double y,double z,double alphaX,double alphaY,double alphaZ) {
        center.set(x,y,z);
        orientation.set(alphaX,alphaY,alphaZ);
    }
}