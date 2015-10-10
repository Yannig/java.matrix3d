package matrix3D.engine;

/**
 * Cette classe nous permet de lire directement le temps. Elle sert à 
 * synchroniser les animations en fonction du temps.
 * @author Yannig Perré
 * @version 0.1
 */
public class Timer extends Object {
    public long startTimer;
    /** Crée une instance de Timer */
    public Timer() {}
    public Timer(boolean start) {
        init();
    }
    public void init() {
        startTimer = System.currentTimeMillis();
    }
    public int getMillis() {
        return (int)(System.currentTimeMillis() - startTimer);
    }
}