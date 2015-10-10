package testMatrix;
import matrix3D.engine.*;
import matrix3D.engine.fx.*;
import matrix3D.geom3D.*;
import matrix3D.geom3D.samples.*;

public class AppletEngine extends java.applet.Applet {
    double camx=0,camy=2,camz=3;
    double alphaX=0,alphaY=0,alphaZ=0;
    boolean started = false;
    Camera cam = new Camera();
    Vertex origine = new Vertex(0,0,2);
    PointLight sun_light = new PointLight(java.awt.Color.yellow,2f,new Vertex(0,1,20),10);
    DiffuseLight red = new DiffuseLight(java.awt.Color.red,0.6f,new Vertex(0,1,-1));
    Shape sun = new Sphere(2,8,15) {
        double cx = 0;
        public void doTransf(int time) {
            cx=((double)time)/1000;
            translate(0,1,20);
            rotate(cx,cx,cx);
        }
        public void postTransf() {
            sun_light.position.set(center);
        }
    };
    Shape terre = new Sphere(0.5,8,15) {
        double cx = 0;
        Vertex orbite = new Vertex(-5,0,0);
        public void doTransf(int time) {
            cx=-((double)time)/2000;
            translate(sun.center);
            translateInv(orbite);
            rotate(orbite,0,cx-1,0);
            rotate(0,cx,0);
        }
    };
    Shape lune = new Sphere(0.3,7,13) {
        double cx=0,cy=0,cz=0;
        Vertex orbite = new Vertex(0.5,0,0);
        public void doTransf(int time) {
            cx=(double)time;
            cx/=200;
            translate(terre.center);
            translateInv(orbite);
            rotate(orbite,cx/15,0,0);
            rotate(orbite,0,cx,0);
            rotate(0,cx,0);
        }
    };
    Shape damier = new Function3D(3,20,20){
        double cx=0,cy=0,cz=0;
        public void doTransf(int time) {
            cx=(double)time;
            cx/=1000;
            translate(0,-1.5,22);
            //rotate(0,cx,0);
        }
        public double f(double x,double y) {
            return -Math.cos(Math.sqrt(x*x+y*y)/4)*1.5;
        }
    };
    public Engine engine = new Engine(this);
    public void init () {
        sun.setRadient();
        engine.setAmbient(0);
        engine.add(sun);
        engine.add(terre);
        engine.add(lune);
        engine.add(damier);
        engine.addEffect(red);
        engine.addEffect(sun_light);
        terre.setElementsColor(java.awt.Color.blue);
        sun.setElementsColor(java.awt.Color.white);
        damier.setElementsColor(java.awt.Color.white);
    }
    public void start() {
        if (!started) {
            started=true;
            cam.moveTo(camx,camy,camz,alphaX,alphaY,alphaZ);
            cam.setGraphics((java.awt.Graphics2D)getGraphics(),getSize().width,getSize().height);
            engine.setCamera(cam);
            engine.start();
        }
    }
}