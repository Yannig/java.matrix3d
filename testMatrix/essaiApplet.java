
import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.util.*;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Classe d'essai sur les différentes conneries sur les buffers de rendus et autres conneries
 * @author Yannig Perré
 */
public class essaiApplet extends Applet implements MouseInputListener {
    /** Lien vers la zone de rendu */
    public Graphics graph = null;
    public essaiApplet() {
      addMouseListener(this);
    }
    public void init() {}
    public void start() {}
    public void stop() {}
    public void destroy() {}
    public void mouseReleased(final java.awt.event.MouseEvent p1) {}
    public void mouseEntered(final java.awt.event.MouseEvent p1) {}
    public void mouseClicked(final java.awt.event.MouseEvent p1) {
        graph = getGraphics();
        graph.setColor(java.awt.Color.black);
        graph.drawLine(0,0,getSize().width,getSize().height);
    }
    public void mousePressed(final java.awt.event.MouseEvent p1) {}
    public void mouseExited(final java.awt.event.MouseEvent p1) {}
    public void mouseDragged(final java.awt.event.MouseEvent p1) {}
    public void mouseMoved(final java.awt.event.MouseEvent p1) {}
}