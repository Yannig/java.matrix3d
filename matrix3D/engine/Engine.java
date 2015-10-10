package matrix3D.engine;
import matrix3D.structure.*;
import matrix3D.geom3D.*;
import matrix3D.engine.fx.*;
import java.awt.*;

/**
 * Cette objet gère l'affichage des objets dans une scène en 3D.
 * @author Yannig Perré
 * @version 0.1.1
 */
public class Engine extends ObjectVector implements Runnable,EngineRenderingHints {
    /** Lien vers l'observateur */
    protected java.awt.image.ImageObserver _window = null;
    /** Nombre de rendu effectuée */
    protected int _numberOfRenderedScene = 1;
    /** Notre thread de rendu */
    private Thread _renderingThread = null;
    /** Affichage des statistiques de rendu ? */
    public boolean drawStats = true;
    /** Composantes lumineuses de notre environnement */
    public FxVector effects = new FxVector();
    /** Camera de notre rendu */
    public Camera camera = new Camera();
    /** Couleur du fond */
    protected java.awt.Color _backgroundColor = java.awt.Color.white;
    /** Type de rendu */
    protected int type = NONE;
    /** Queue de rendue */
    protected HeapQueue _queue = new HeapQueue();
    /** Nombre de facette affichée */
    protected int _numberOfDrawedSurface = 0;
    /** Notre timer */
    protected Timer timer = new Timer(true);
    /** Date de notre dernier rendu */
    protected int timeOfLastRendering = 0;
    protected int timeOfLastStatRendering = 0;
    /** Date de notre avant dernier rendu */
    protected int timeBefore = 0;
    
    /** Crée une instance de Renderer */
    public Engine(java.awt.image.ImageObserver obs) {
        _window = obs;
        clear();
        camera.setEngine(this);
    }
    public Engine(java.awt.image.ImageObserver obs,Camera cam) {
        _window = obs;
        camera = cam;
        clear();
        camera.setEngine(this);
    }
    /** Renvoie la milliseconde par rapport au lancement du rendu */
    public int getTime() {
        return timer.getMillis();
    }
    /** Modifie la composante ambiante du rendu */
    public void setAmbient(double amb) {
        effects.ambient.setRadiance((float)amb);
    }
    /** renvoie la composante ambiante de la scéne */
    public Light getAmbient() {
        return effects.ambient;
    }
    /** Rajoute une lumière */
    public void addEffect(Light l) {
        effects.add(l);
    }
    /** Renvoie la couleur en fonction de la position, de la norme de la surface */
    public java.awt.Color getColor(java.awt.Color col,Vertex position,Vertex norm,int rad) {
        return effects.getColor(col,position,norm,rad,camera);
    }
    /** Initialise le gestionnaire de scène */
    public void clear() {
        super.clear();
    }
    /** Change la camera */
    public void setCamera(Camera c) {
        camera = c;
        camera.setEngine(this);
    }
    /** Initialise la caméra du gestionnaire de scene */
    public void resetCamera() {
        camera.reset();
    }
    /** Modifie le type du rendu */
    public void setRenderingOption(int t) {
        type = t;
    }
    /** Renvoie le type de rendu */
    public int getRenderingOption() {
        return type;
    }
    /** Affichage/Arrêt des statistiques sur le rendu */
    public void drawStat(boolean draw) {
        drawStats = draw;
    }
    /** Transforme les éléments */
    protected void processPreRendering(AffineTransform3D cam) {
        timeOfLastRendering=getTime();
        if ((drawStats)&&((_numberOfRenderedScene%RENDERING_STATS_NUMBER)==0)) {
            timeBefore=timeOfLastStatRendering;
            timeOfLastStatRendering = timeOfLastRendering;
            _numberOfDrawedSurface = _queue.size;
        }
        _queue.clear();
        // Traitement sur les objets
        for(int i=0;i<size;i++) {
            objects[i].preProcess(cam,timeOfLastRendering);
            for(int j=0;j<objects[i].elements.size;j++) {
                if (objects[i].elements.elements[j].isVisible(camera)) {
                    _queue.add(objects[i].elements.elements[j]);
                }
            }
        }
        if ((drawStats)&&((_numberOfRenderedScene%RENDERING_STATS_NUMBER)==0)) {
            _numberOfDrawedSurface = _queue.size;
        }
    }
    /** Procéde au rendu final */
    protected void drawAll(AffineTransform3D cam) {
        effects.preProcess(cam);
        camera.clearGraphics(effects.ambient.getColor(_backgroundColor));
        while(! _queue.empty()) {
            if (type == NONE) _queue.getHeap().draw(camera);
            else _queue.getHeap().draw(camera,type);
        }
        _numberOfRenderedScene++;
        if (drawStats) {
            camera._bufferedRender.setColor(java.awt.Color.gray);
            camera._bufferedRender.drawString(toString(),20,50);
        }
        if (camera._bufferedRender != camera.graph) camera.draw(_window); // rendu final
    }
    /** Calcul et renvoie la scene */
    public void render() {
        AffineTransform3D cam = camera.getCamera();
        // Calcul des transformations + Calcul des projections + Calcul des faces visibles
        processPreRendering(cam);
        // Calcul des lumières + Rendu des faces
        drawAll(cam);
    }
    /** Renvoie une chaîne de caractère décrivant notre gestionnaire de rendu */
    public String toString() {
        java.text.NumberFormat format = new java.text.DecimalFormat("00.0");
        String result = "Nombre d'objets : " + size + 
        " - nombre de facettes : " + _numberOfFacets +
        " (visibles : " + _numberOfDrawedSurface + ") ";
        int current_time = getTime();
        try { 
            result += " FPS courant : " + format.format((RENDERING_STATS_NUMBER*1000.)/(double)(timeOfLastStatRendering-timeBefore))
            + " (moyenne FPS : " + format.format(_numberOfRenderedScene / ((double)getTime()/1000.)) + ") ";
        } catch (Exception e) {
              result += " FPS : " + -1;
        }
        return result;
    }
    /** Lancement du rendu */
    public void run() {
        resume();
        while(true) {
            render();
        }
    }
    /** Lancement du timer */
    public void resume() {
        timer = new Timer(true);
        _numberOfRenderedScene = 0;
    }
    public void start() {
        _renderingThread = new Thread(this);
        _renderingThread.setPriority(Thread.MIN_PRIORITY);
        _renderingThread.start();
    }
}