package matrix3D.geom3D;
import matrix3D.geom3D.*;
import java.text.*;
/**
 * Cette Classe permet la transformation affine en coordonnées 3D d'un point.
 * @author  Yannig Perré
 * @version 0.7.1
 */
public class AffineTransform3D extends Object {
    /** La matrice est une matrice d'identité */
    public static final int IDENTITY = 0x0001;
    /** La matrice est une matrice de rotation */
    public static final int ROTATION = 0x0002;
    /** La matrice est une matrice de translation */
    public static final int TRANSLATION = 0x0004;
    /** La matrice est une matrice d'homothétie */
    public static final int HOMOTHETY = 0x0008;
    /** La matrice est une matrice shear */
    public static final int SHEAR = 0x0010;
    /** La matrice est une matrice Symmetry */
    public static final int SYMMETRY = 0x0020;
    /** La matrice est une matrice associée à la caméra */
    public static final int CAMERA = 0x0040;
    /** Champ spécifiant les transformations associées avec la matrice */
    protected int _type = IDENTITY;
    public Matrix matrix = new Matrix();
    /** Matrice servant au calcul de rotation (X,Y,Z) */
    private static Matrix _rotationX = new Matrix();
    private static Matrix _rotationY = new Matrix();
    private static Matrix _rotationZ = new Matrix();
    /** Matrice de translation */
    private static Matrix _translateToOrigin = new Matrix();
    private static Matrix _translateInverse = new Matrix();
    
    /** Constructeur par défaut */
    public AffineTransform3D() {}
    public AffineTransform3D(AffineTransform3D m) {
        set(m);
    }
    public void clear() {
        matrix.clear();
        _type = IDENTITY;
    }
    /** Opération de multiplication avec une autre matrice de transformation */
    public AffineTransform3D mul(AffineTransform3D mat) {
        AffineTransform3D result = new AffineTransform3D();
        result.matrix = matrix.mul(mat.matrix);
        result._type=mat._type|_type;
        return result;
    }
    /** Assignation des valeurs d'une matrice */
    public AffineTransform3D set(AffineTransform3D mat) {
        matrix.init(mat.matrix);
        _type=mat._type;
        return this;
    }
    /** Méthode de consultation des propriétés de la matrice */
    /** matrice identité ? */
    public boolean isIdentity() {
        return _type==IDENTITY;
    }
    /** matrice de rotation ? */
    public boolean isRotation() {
        return (_type&ROTATION)!=0;
    }
    /** matrice de translation ? */
    public boolean isTranslation() {
        return (_type&TRANSLATION)!=0;
    }
    /** matrice d'homothety ? */
    public boolean isHomothety() {
        return (_type&HOMOTHETY)!=0;
    }
    /** matrice shear ? */
    public boolean isShear() {
        return (_type&SHEAR)!=0;
    }
    /** matrice associée à une caméra ? */
    public boolean isCamera() {
        return (_type&CAMERA)!=0;
    }
    /** Modifie le type de la matrice pour préciser qu'il s'agit d'une caméra */
    public void setCamera() {
        _type|=CAMERA;
    }
    /** Rend la matrice Identité */
    public void setToIdentity() {
        matrix.clear();
        _type = IDENTITY;
    }
    /** Initialise la matrice de rotation autour d'un point */
    public void setToRotation(Vertex cntr,double rX,double rY,double rZ) {
        _translateToOrigin.xt=cntr.x;
        _translateToOrigin.yt=cntr.y;
        _translateToOrigin.zt=cntr.z;
        _translateInverse.xt=-cntr.x;
        _translateInverse.yt=-cntr.y;
        _translateInverse.zt=-cntr.z;
        setToRotation(rX,rY,rZ);
        matrix=_translateToOrigin.mul(matrix.mul(_translateInverse));
        _type = ROTATION|TRANSLATION;
    }
    /** Crée une matrice de rotation */
    public void setToRotation(double rX,double rY,double rZ) {
        _rotationX.clear();
        _rotationY.clear();
        _rotationZ.clear();
        _rotationZ.yy=(_rotationZ.xx=java.lang.Math.cos(rZ));
        _rotationZ.xy=-(_rotationZ.yx=java.lang.Math.sin(rZ));
        _rotationY.zz=(_rotationY.xx=java.lang.Math.cos(rY));
        _rotationY.xz=-(_rotationY.zx=java.lang.Math.sin(rY));
        _rotationX.zz=(_rotationX.yy=java.lang.Math.cos(rX));
        _rotationX.yz=-(_rotationX.zy=java.lang.Math.sin(rX));
        matrix=_rotationX.mul(_rotationY.mul(_rotationZ));
        _type = ROTATION;
    }
    /** Initiation à l'aide de 3 doubles */
    public void setToTranslation(double tX,double tY,double tZ) {
        clear();
        matrix.xt=tX;matrix.yt=tY;matrix.zt=tZ;
        _type = TRANSLATION;
    }
    public void setToSymmetry(boolean sX,boolean sY,boolean sZ) {
        if (sX) matrix.xx=-1; else matrix.xx=1;
        if (sY) matrix.yy=-1; else matrix.yy=1;
        if (sZ) matrix.zz=-1; else matrix.zz=1;
        _type = SYMMETRY;
    }
    /** Initialise le contenu de la matrice d'homothétie */
    public void setToScale(double cX,double cY,double cZ) {
        matrix.xx=cX;matrix.yy=cY;matrix.zz=cZ;
        _type = HOMOTHETY;
    }
    /** Retourne une instance de transformation affine de translation */
    public static AffineTransform3D getTranslateInstance(double tX,double tY,double tZ) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToTranslation(tX,tY,tZ);
        return tmp;
    }
    /** Retourne une instance de translation par rapport au point vecteur */
    public static AffineTransform3D getTranslateInstance(Vertex orig) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToTranslation(orig.x,orig.y,orig.z);
        return tmp;
    }
    /** Retourne une instance de translation pour retour à l'origine avec le point */
    public static AffineTransform3D getTranslateInv(Vertex orig) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToTranslation(-orig.x,-orig.y,-orig.z);
        return tmp;
    }
    /** Retourne une instance de rotation */
    public static AffineTransform3D getRotateInstance(double cX,double cY,double cZ) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(cX,cY,cZ);
        return tmp;
    }
    /** Retourne une instance de rotation */
    public static AffineTransform3D getRotateInstance(Vertex orientation) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(orientation.x,orientation.y,orientation.z);
        return tmp;
    }
    /** Retourne une instance de rotation autour d'un point */
    public static AffineTransform3D getRotateInstance(Vertex center,double cX,double cY,double cZ) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(center,cX,cY,cZ);
        return tmp;
    }
    /** Retourne une instance de rotation autour d'un point */
    public static AffineTransform3D getRotateInstance(Vertex center,Vertex rotate) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(center,rotate.x,rotate.y,rotate.z);
        return tmp;
    }
    /** Retourne une instance inverse de rotation */
    public static AffineTransform3D getRotateInv(double cX,double cY,double cZ) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(-cX,-cY,-cZ);
        return tmp;
    }
    /** Retourne une instance inverse de rotation */
    public static AffineTransform3D getRotateInv(Vertex center) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(-center.x,-center.y,-center.z);
        return tmp;
    }
    /** Retourne une instance inverse de rotation autour d'un point */
    public static AffineTransform3D getRotateInv(Vertex center,double cX,double cY,double cZ) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(center,-cX,-cY,-cZ);
        return tmp;
    }
    /** Retourne une instance inverse de rotation autour d'un point */
    public static AffineTransform3D getRotateInv(Vertex center,Vertex rotate) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToRotation(center,-rotate.x,-rotate.y,-rotate.z);
        return tmp;
    }
    /** Retourne une instance d'homothétie */
    public static AffineTransform3D getScaleInstance(double sX,double sY,double sZ) {
        AffineTransform3D tmp = new AffineTransform3D();
        tmp.setToScale(sX,sY,sZ);
        return tmp;
    }
    /** Permet l'affichage de la matrice */
    public String toString() {
        return matrix.toString();
    }
}