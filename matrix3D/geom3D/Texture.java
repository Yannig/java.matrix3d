package matrix3D.geom3D;

import java.awt.image.*;
import java.awt.*;
import java.util.Observer;
import matrix3D.engine.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;

/**
 * Cette classe va servir a stocker les informations relatives à une texture de la scène.
 * On y stockera la version originale de la texture et sa version retournée.
 * @author Yannig Perré
 * @version 0.1
 */
public class Texture implements EngineRenderingHints {
    /** référence vers l'image d'origine */
    public TexturePaint texture = null;
    /** Version de l'image renversée (pour le rendu d'un triangle inversé) */
    public TexturePaint textureInv = null;
    /** Taille de notre Texture */
    public int width=0,height=0;
    public Rectangle anchor = null;
    /** Option de rendu */
    public int renderingHints = LINEAR_EXTRAPOLATION;
    /** Tableau servant à faire le rendu du triangle */
    static private int [] xt = {0,0,0};
    static private int [] yt = {0,0,0};
    /** Transformation affine nécessaire au calcul du triangle */
    static private AffineTransform transform = new AffineTransform();
    static private AffineTransform transfInv = new AffineTransform();
    /**Variables temporaires de calcul */
    static private double _x,_y,_x1,_x2,_y2;
    static private double _cosa,_sina;
    /** Constructeur (image origine + taille) */
    public Texture(BufferedImage txtr,int sizeX,int sizeY) {
        init(txtr,sizeX,sizeY,NO_TRANSFORMATION);
    }
    /** Idem avec des options de transformation de base sur la texture */
    public Texture(BufferedImage txtr,int sizeX,int sizeY,int options) {
        init(txtr,sizeX,sizeY,options);
    }
    public void init(BufferedImage txtr,int sizeX,int sizeY,int options) {
        width=sizeX;height=sizeY;
        if ((options&ROTATION_90)!=0) {
            width=sizeY;height=sizeX;
            BufferedImage tempBufferRotation = new BufferedImage(width,height,
                                                   BufferedImage.TYPE_4BYTE_ABGR);
            AffineTransform tempAffineRotation = new AffineTransform(0,1,-1,0,sizeY,0);
            tempBufferRotation.createGraphics().drawImage(txtr,tempAffineRotation,null);
            txtr=tempBufferRotation;
        } else if ((options&ROTATION_180)!=0) {
            BufferedImage tempBufferRotation = new BufferedImage(width,height,
                                                   BufferedImage.TYPE_4BYTE_ABGR);
            AffineTransform tempAffineRotation = new AffineTransform(-1,0,0,-1,width,height);
            tempBufferRotation.createGraphics().drawImage(txtr,tempAffineRotation,null);
            txtr=tempBufferRotation;
        } else if ((options&ROTATION_270)!=0) {
            width=sizeY;height=sizeX;
            BufferedImage tempBufferRotation = new BufferedImage(width,height,
                                                   BufferedImage.TYPE_4BYTE_ABGR);
            AffineTransform tempAffineRotation = new AffineTransform(0,-1,1,0,0,sizeX);
            tempBufferRotation.createGraphics().drawImage(txtr,tempAffineRotation,null);
            txtr=tempBufferRotation;
        }
        if ((options&SYMETRY_Y)!=0) {
            BufferedImage tempBufferRotation = new BufferedImage(width,height,
                                                   BufferedImage.TYPE_4BYTE_ABGR);
            AffineTransform tempAffineRotation = new AffineTransform(-1,0,0,1,width,0);
            tempBufferRotation.createGraphics().drawImage(txtr,tempAffineRotation,null);
            txtr=tempBufferRotation;
        }
        if ((options&SYMETRY_X)!=0) {
            BufferedImage tempBufferRotation = new BufferedImage(width,height,
                                                   BufferedImage.TYPE_4BYTE_ABGR);
            AffineTransform tempAffineRotation = new AffineTransform(1,0,0,-1,0,height);
            tempBufferRotation.createGraphics().drawImage(txtr,tempAffineRotation,null);
            txtr=tempBufferRotation;
        }
        anchor = new Rectangle(width,height);
        texture = new TexturePaint(txtr,anchor);
        BufferedImage tempBuffer = new BufferedImage(width,height,
                                                     BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D tempGraph = tempBuffer.createGraphics();
        // Rotation de 180 ° + translation pour retour à l'origine
        AffineTransform tempAffine = new AffineTransform(-1,0,0,-1,width,height);
        tempGraph.drawImage(txtr,tempAffine,null);
        textureInv=new TexturePaint(tempBuffer,anchor);
        xt[1]=width;
        yt[2]=height;
    }
    /** Effectue une projection de la texture sur les points 2D précisés (pas de correction
     * de perpective ...). La projection se fait toujours par triangle donc le tableau devra
     * toujours contenir au moins 3 points. S'il y a des points supplémentaires, ils ne seront 
     * pas pris en compte.
     */
    public void doProjection(int [] x,int [] y,Graphics2D graph,boolean inv,float rad) {
        switch(renderingHints) {
            //graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            case BILINEAR_EXTRAPOLATION :
            graph.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                   RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            break;
            case LINEAR_EXTRAPOLATION :
            graph.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                   RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            break;
            default :
            graph.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                   RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        }
        // Calcul de la transformation
        _x=x[0]-x[1];
        _y=y[0]-y[1];
        _x1=Math.sqrt(_x*_x+_y*_y);
        _cosa=_x/_x1;
        _sina=-_y/_x1;
        _x=x[2]-x[1];
        _y=y[2]-y[1];
        _x2=_x*_cosa-_y*_sina;
        _y2=_x*_sina+_y*_cosa;
        transform.setTransform(_x1/width,0.,
                               _x2/height,_y2/height,0.,0.);
        transfInv.setTransform(_cosa,-_sina,
                               _sina,_cosa,
                               x[1],y[1]);
        transfInv.concatenate(transform);
        Composite oldAlpha = graph.getComposite();
        graph.setTransform(transfInv);
        if (rad<=1) {
            graph.fillPolygon(xt,yt,3);
            graph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,rad));
            if (inv) graph.setPaint(textureInv);
            else graph.setPaint(texture);
            graph.fillPolygon(xt,yt,3);
            graph.setComposite(oldAlpha);
        } else {
            Paint context = graph.getPaint();
            if (inv) graph.setPaint(textureInv);
            else graph.setPaint(texture);
            graph.fillPolygon(xt,yt,3);
            graph.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,Math.max(0,rad/(1-rad))));
            graph.setPaint(context);
            graph.fillPolygon(xt,yt,3);
            graph.setComposite(oldAlpha);
        }
    }
    /** Procéde à une projection sur 4 points (découpage en 2) */
    public void doQuadProjection(int [] x,int [] y,Graphics2D graph,boolean inv,float transp) {
        doProjection(x,y,graph,false,transp);
        x[1]=x[3];y[1]=y[3];
        x[3]=x[0];y[3]=y[0];
        x[0]=x[2];y[0]=y[2];
        x[2]=x[3];y[2]=y[3];
        doProjection(x,y,graph,true,transp);
    }
    public void setRenderingHints(int hints) {
        renderingHints=hints;
    }
}