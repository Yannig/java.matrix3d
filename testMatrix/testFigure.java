package testMatrix;
import matrix3D.geom3D.*;
import matrix3D.geom3D.samples.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

/**
 * Test la classe figure
 * @author Yannig Perré
 * @version 0.1
 */
public class testFigure extends Applet {
    Button generer = new Button("Générer les matrices et calculer !");
    TextArea trace = new TextArea();
    Label translation = new Label("Translation :");
    Label rotation = new Label("Rotation :");
    TextField tX = new TextField("0"),tY = new TextField("0"),tZ = new TextField("0");
    TextField rX = new TextField("0"),rY = new TextField("0"),rZ = new TextField("0");
    Cube cube = new Cube() {public void doTransf(int time) {}};
    public void start() {
        initComponents();
    }
    public void initComponents() {
        setLayout(null);
        translation.setBounds(5,5,80,25);
        tX.setBounds(90,5,30,25);
        tY.setBounds(125,5,30,25);
        tZ.setBounds(160,5,30,25);
        add(translation);
        add(tX);add(tY);add(tZ);
        rotation.setBounds(5,35,80,25);
        rX.setBounds(90,35,30,25);
        rY.setBounds(125,35,30,25);
        rZ.setBounds(160,35,30,25);
        add(rotation);
        add(rX);add(rY);add(rZ);
        generer.setBounds(5,80,getSize().width-10,25);
        generer.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                process();
            }
        });
        trace.setBounds(5,120,getSize().width-10,getSize().height-125);
        trace.append("Cube : \n");
        trace.append(cube.toString());
        add(generer);
        add(trace);
    }
    public void process() {
        AffineTransform3D translation= new AffineTransform3D();
        translation.setToTranslation(new Double(tX.getText()).doubleValue(),
                                                 new Double(tY.getText()).doubleValue(),
                                                 new Double(tZ.getText()).doubleValue());
        AffineTransform3D rotation = new AffineTransform3D();
        rotation.setToRotation(new Double(rX.getText()).doubleValue(),
                                         new Double(rY.getText()).doubleValue(),
                                         new Double(rZ.getText()).doubleValue());
        trace.append("Matrice de translation :\n");
        trace.append(translation.toString());
        trace.append("Matrice de rotation :\n");
        
        trace.append(rotation.toString());
        trace.append("Résultat de la transformation sur le cube :\n");
        AffineTransform3D transf = translation.mul(rotation);
        cube.transf(transf);
        trace.append("Cube : \n");
        trace.append(cube.toString());
    }
}