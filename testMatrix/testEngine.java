package testMatrix;
import java.awt.Frame;
import java.applet.Applet;
import java.awt.Button;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.event.*;
import matrix3D.engine.*;
import matrix3D.engine.fx.*;
import matrix3D.geom3D.*;
import matrix3D.geom3D.samples.*;
import java.lang.Math;
import java.io.*;
import java.net.*;

public class testEngine extends Frame implements WindowListener, MouseListener, KeyListener, EngineRenderingHints {
    double camx=0,camy=2,camz=3;
    double alphaX=0,alphaY=0,alphaZ=0;
    boolean started = false;
    Camera cam = new Camera();
    Vertex origine = new Vertex(0,0,2);
    PointLight sun_light = new PointLight(java.awt.Color.yellow,2f,new Vertex(0,1,20));
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
    Shape shp2 = new Cone(1,0.4,20) {
        double cx=0,cy=0,cz=0;
        public void doTransf(int time) {
            cx=(double)time;
            cx/=500;
            rotate(origine,0,cx+3.14,0);
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
    public testEngine() {
        init();
    }
    public void init() {
        initComponents();
    }
    Button wire_frame = new Button("Fil de fer");
    Button sun_light_button = new Button("Couper le soleil");
    Button diffuse_button = new Button("Couper la lumière diffuse");
    Button red_button = new Button("Rouge");
    Button green_button = new Button("Vert");
    Button blue_button = new Button("Bleu");
    Button yellow_button = new Button("Jaune");
    Button pink_button = new Button("Rose");
    Button orange_button = new Button("Orange");
    Button navigate = new Button("Naviguer !");
    public void initComponents() {
        /** Ajout de gestionnaire d'événements */
        addWindowListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setTitle("Test du moteur (texture) - Version 0.6.3");
        setSize(800,600);
        setLayout(null);
        sun.setRadient();
        engine.setAmbient(0);
        Image yann = loadImage("file:testMatrix/Yann.jpg");
        double coef=1;
        BufferedImage buffer = new BufferedImage((int)(yann.getWidth(this)*coef),
                                                 (int)(yann.getHeight(this)*coef),
                                                 BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferGraph = buffer.createGraphics();
        AffineTransform transf = new AffineTransform(coef,0,0,coef,0,0);
        bufferGraph.drawImage(yann,transf,null);
        Texture texture = new Texture(buffer,buffer.getWidth(),buffer.getHeight());
        texture.setRenderingHints(Texture.BILINEAR_EXTRAPOLATION);
        damier.setTexture(texture);
        engine.add(sun);
        engine.add(terre);
        engine.add(lune);
        engine.add(damier);
        engine.addEffect(red);
        engine.addEffect(sun_light);
        terre.setElementsColor(java.awt.Color.blue);
        sun.setElementsColor(java.awt.Color.white);
        damier.setElementsColor(java.awt.Color.white);
        wire_frame.addActionListener(new ActionListener() {
            boolean wire = true;
            public void actionPerformed(ActionEvent e) {
                if (wire) {
                    wire_frame.setLabel("Face pleine");
                    engine.setRenderingOption(WIRE_FRAME);
                } else {
                    wire_frame.setLabel("Fil de Fer");
                    engine.setRenderingOption(REGULAR_FRAME);
                }
                wire = !wire;
            }
        });
        sun_light_button.addActionListener(new ActionListener() {
            boolean cut = true;
            public void actionPerformed(ActionEvent e) {
                if (cut) {
                    sun_light_button.setLabel("Allumer le soleil");
                    sun_light.setRadiance(0);
                    sun.setAbsorb();
                } else {
                    sun_light_button.setLabel("Couper le soleil");
                    sun_light.setRadiance(2);
                    sun.setRadient();
                }
                cut = !cut;
            }
        });
        diffuse_button.addActionListener(new ActionListener() {
            boolean cut = true;
            public void actionPerformed(ActionEvent e) {
                if (cut) {
                    diffuse_button.setLabel("Rétablir la lumière diffuse");
                    red.setRadiance(0);
                } else {
                    diffuse_button.setLabel("Couper la lumière diffuse");
                    red.setRadiance(0.6f);
                }
                cut = !cut;
            }
        });
        red_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.setColor(java.awt.Color.white);
            }
        });
        green_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.setColor(java.awt.Color.green);
            }
        });
        blue_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.setColor(java.awt.Color.blue);
            }
        });
        yellow_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.setColor(java.awt.Color.yellow);
            }
        });
        pink_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.setColor(java.awt.Color.pink);
            }
        });
        orange_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                red.setColor(java.awt.Color.orange);
            }
        });
        navigate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeAllButton();
            }
        });
        wire_frame.setBounds(5,60,100,20);
        sun_light_button.setBounds(110,60,100,20);
        diffuse_button.setBounds(215,60,150,20);
        red_button.setBounds(370,60,60,20);
        green_button.setBounds(435,60,60,20);
        blue_button.setBounds(500,60,60,20);
        yellow_button.setBounds(565,60,60,20);
        pink_button.setBounds(630,60,60,20);
        orange_button.setBounds(695,60,60,20);
        navigate.setBounds(300,85,200,40);
        //addAllButton();
    }
    public void addAllButton() {
        add(wire_frame);
        add(sun_light_button);
        add(diffuse_button);
        add(red_button);
        add(green_button);
        add(blue_button);
        add(yellow_button);
        add(pink_button);
        add(orange_button);
        add(navigate);
    }
    public void removeAllButton() {
        remove(wire_frame);
        remove(sun_light_button);
        remove(diffuse_button);
        remove(red_button);
        remove(green_button);
        remove(blue_button);
        remove(yellow_button);
        remove(pink_button);
        remove(orange_button);
        remove(navigate);
    }
    public static void main(String args[]) {
        testEngine fra = new testEngine();
        fra.setVisible(true);
    }
    public void windowDeactivated(java.awt.event.WindowEvent p1) {}
    public void windowClosed(java.awt.event.WindowEvent p1) {}
    public void windowDeiconified(java.awt.event.WindowEvent p1) {}
    public void windowOpened(java.awt.event.WindowEvent p1) {}
    public void windowIconified(java.awt.event.WindowEvent p1) {}
    public void windowClosing(java.awt.event.WindowEvent p1) {
        System.exit(0);
    }
    public void start() {
        windowActivated(null);
    }
    public void windowActivated(java.awt.event.WindowEvent p1) {
        if (!started) {
            started=true;
            cam.moveTo(camx,camy,camz,alphaX,alphaY,alphaZ);
            cam.setGraphics((java.awt.Graphics2D)getGraphics(),getSize().width,getSize().height);
            engine.setCamera(cam);
            engine.start();
        }
    }
    public void mouseReleased(java.awt.event.MouseEvent p1) {}
    public void mouseEntered(java.awt.event.MouseEvent p1) {}
    public void mouseClicked(java.awt.event.MouseEvent p1) {
        addAllButton();
    }
    public void mousePressed(java.awt.event.MouseEvent p1) {}
    public void mouseExited(java.awt.event.MouseEvent p1) {}
    
    public void keyPressed(java.awt.event.KeyEvent keyEvent) {}
    public void keyReleased(java.awt.event.KeyEvent keyEvent) {}
    public void keyTyped(java.awt.event.KeyEvent keyEvent) {
        switch(keyEvent.getKeyChar()) {
            // Z
            case 'z' :
            case 'Z' : camz+=0.3;
            break;
            case 's' :
            case 'S' : camz-=0.3;
            break;
            // X
            case 'd' :
            case 'D' : camx+=0.3;
            break;
            case 'q' :
            case 'Q' : camx-=0.3;
            break;
            // Y
            case '-' : camy+=0.3;
            break;
            case '+' : camy-=0.3;
            break;
            // Rotation X
            case '8' : this.alphaX+=0.1;
            break;
            case '2' : this.alphaX-=0.1;
            break;
            // Rotation Y
            case '6' : this.alphaY+=0.1;
            break;
            case '4' : this.alphaY-=0.1;
            break;
            // Rotation Z
            case '*' : this.alphaZ+=0.1;
            break;
            case '/' : this.alphaZ-=0.1;
            break;
        }
        cam.moveTo(camx,camy,camz,alphaX,alphaY,alphaZ);
    }
    /** Charge une image */
    public Image loadImage(String url) {
        try {
            URL fileURL = new URL(url);
            Image img = Toolkit.getDefaultToolkit().getImage(fileURL);
            try {
                MediaTracker tracker = new MediaTracker(this);
                tracker.addImage(img, 0);
                tracker.waitForID(0);
            } catch (Exception e) {}
            return img;
        }catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}