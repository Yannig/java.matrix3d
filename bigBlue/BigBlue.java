package bigBlue;
import matrix3D.geom3D.*;
import matrix3D.geom3D.samples.*;
import matrix3D.engine.*;
import matrix3D.engine.fx.*;
import java.awt.Frame;
import java.awt.Color;
import java.awt.event.*;

/**
 * Et voici le grand Bleu !
 * @author  Yansolo
 */
public class BigBlue extends Frame implements WindowListener,EngineRenderingHints {
    /** Moteur 3D */
    Engine moteur3D = new Engine(this);
    /** Camera */
    Camera camera = new Camera();
    /** Lumière */
    PointLight oceanLight = new PointLight(Color.blue,1f,new Vertex(0,40,0));
    /** Constante de la lumière ambiante */
    double basicAmbient = 0.2;
    double ambientVariation = 0.05;
    /** Un dauphin */
    Shape dauphin = new Cube() {
        public void doTransf(int time) {
            translate(0,0,4);
            rotate((double)time/1000.,0,0);
            rotate(0,(double)time/1000.,0);
        }
    };
    /** Objet gérant la lumière */
    Shape lightMover = new Shape() {
        public void doTransf(int time) {
            moteur3D.setAmbient(basicAmbient + ambientVariation*Math.cos((double)(time)/1000.));
        }
    };
    public BigBlue() {
        initComponents();
    }
    private void initComponents() {
        setLayout(null);
        addWindowListener(this);
        setSize(640,480);
        initEngine();
    }
    public void initEngine() {
        // Réglage des lumières
        moteur3D.getAmbient().setColor(Color.blue);
        moteur3D.setAmbient(0.3);
        moteur3D.addEffect(oceanLight);
        
        // Ajout des objets
        moteur3D.add(dauphin);
        moteur3D.add(lightMover);
        dauphin.setColor(Color.blue);
    }
    public static void main(String [] args) {
        Frame bigBlue = new BigBlue();
        bigBlue.show();
    }
    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        System.exit(0);
    }
    boolean activated = false;
    public void windowActivated(java.awt.event.WindowEvent windowEvent) {
        if (!activated) {
            activated = true;
            camera.setGraphics((java.awt.Graphics2D)getGraphics(),getSize().width,getSize().height);
            moteur3D.setCamera(camera);
            moteur3D.start();
        }
    }
    public void windowDeiconified(java.awt.event.WindowEvent windowEvent) {}
    public void windowDeactivated(java.awt.event.WindowEvent windowEvent) {}
    public void windowIconified(java.awt.event.WindowEvent windowEvent) {}
    public void windowClosed(java.awt.event.WindowEvent windowEvent) {}
    public void windowOpened(java.awt.event.WindowEvent windowEvent) {}
}
