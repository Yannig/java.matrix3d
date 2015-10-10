package testMatrix;

import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Classe d'essai sur les différentes conneries sur les buffers de rendus et autres conneries
 * @author Yannig Perré
 */
public class testGraphic extends Applet {
    /** Lien vers la zone de rendu */
    public Graphics2D graph = null;
    /** Buffer de rendu */
    protected BufferedImage _buffer = null;
    /** objet servant au rendu temporaire */
    public Graphics2D _bufferedRender = null;
    /** Mon beauf */
    Image yann;
    /** Les transformations affines */
    java.awt.geom.AffineTransform transf = new java.awt.geom.AffineTransform();
    java.awt.geom.AffineTransform scale = new java.awt.geom.AffineTransform();
    public BufferedImage yannBuffer;
    public testGraphic() {
        initComponents();
    }
    public Image getImage(String name) {
        return getImage(name,this);
    }
    public static Image getImage(String name, Component cmp) {
        Image img;
        URLClassLoader urlLoader = (URLClassLoader)cmp.getClass().getClassLoader();
        img = cmp.getToolkit().createImage(urlLoader.findResource("./" + name));
        
        MediaTracker tracker = new MediaTracker(cmp);
        tracker.addImage(img, 0);
        try {
            tracker.waitForID(0);
            if (tracker.isErrorAny()) {
                System.out.println("Error loading image " + name);
            }
        } catch (Exception ex) { ex.printStackTrace(); }
        return img;
    }
    double coef = 0.01;
    private void initComponents() {
        retaille(800,600);
        yann = getImage("Yann.jpg");
        transf.setToScale(coef,coef);
        yannBuffer = new BufferedImage((int)(yann.getWidth(this)*coef),
        (int)(yann.getHeight(this)*coef),
        BufferedImage.TYPE_INT_RGB);
        yannBuffer.createGraphics().drawImage(yann,transf,null);
    }
    /** recalibre la zone de rendu */
    public void retaille(int width,int height) {
        _buffer = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        _bufferedRender = _buffer.createGraphics();
    }
    public Component parent = this;
    Thread yannRotation = new Thread() {
        public void run() {
            double alpha=0.01;
            scale.scale(1.1,1.1);
            scale.translate(-30,-20);
            for(coef=0.01;coef<1;coef+=coef/10) {
                transf.setToScale(coef,coef);
                yannBuffer = new BufferedImage(Math.max(1,(int)(yann.getWidth(parent)*coef)),
                                               Math.max(1,(int)(yann.getHeight(parent)*coef)),
                                               BufferedImage.TYPE_INT_RGB);
                yannBuffer.createGraphics().drawImage(yann,transf,null);
                transf.setToScale(1/coef,1/coef);
                _bufferedRender.clearRect(0,0,800,600);
                _bufferedRender.drawImage(yannBuffer,transf,null);
                graph.drawImage(_buffer,0,0,parent);
            }
        }
    };
    public void init() {
        initComponents();
    }
    public void start() {
        graph = (Graphics2D) getGraphics();
        yannRotation.start();
    }
    public void stop() {
    }
    public void destroy() {
    }
}