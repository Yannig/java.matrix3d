package matrix3D.geom3D;
import java.text.*;
/**
 * Structure de la matrice avec ses calculs associés.
 * @author Yannig Perré
 * @version 0.3.2
 * Remarque : Les commentaires ne sont restés que pour des raisons de lisibilité.
 * En effet, auparavant, le code mis en commentaire servait puisqu'on faisait de 
 * vrai multiplication 4x4 alors que nous n'avons en réalité besoin que de matrice
 * 4x3 ...
 */
public final class Matrix {
    /** Contenu de la matrice de transformation 4x4 */
    public double xx=1;public double xy=0;public double xz=0;public double xt=0;
    public double yx=0;public double yy=1;public double yz=0;public double yt=0;
    public double zx=0;public double zy=0;public double zz=1;public double zt=0;
    //public static final double tx=0;public static final double ty=0;
    //public static final double tz=0;public static final double tt=1;
    /** Constructeur */
    public Matrix() {}
    /** Opération de multiplication avec une autre matrice de transformation */
    public Matrix mul(Matrix mat) {
        Matrix result = new Matrix();
        result.xx = xx*mat.xx + xy*mat.yx + xz*mat.zx /*+ xt*mat.tx*/;
        result.xy = xx*mat.xy + xy*mat.yy + xz*mat.zy /*+ xt*mat.ty*/;
        result.xz = xx*mat.xz + xy*mat.yz + xz*mat.zz /*+ xt*mat.tz*/;
        result.xt = xx*mat.xt + xy*mat.yt + xz*mat.zt + xt/*mat.tt*/;
        result.yx = yx*mat.xx + yy*mat.yx + yz*mat.zx /*+ yt*mat.tx*/;
        result.yy = yx*mat.xy + yy*mat.yy + yz*mat.zy /*+ yt*mat.ty*/;
        result.yz = yx*mat.xz + yy*mat.yz + yz*mat.zz /*+ yt*mat.tz*/;
        result.yt = yx*mat.xt + yy*mat.yt + yz*mat.zt + yt/*mat.tt*/;
        result.zx = zx*mat.xx + zy*mat.yx + zz*mat.zx /*+ zt*mat.tx*/;
        result.zy = zx*mat.xy + zy*mat.yy + zz*mat.zy /*+ zt*mat.ty*/;
        result.zz = zx*mat.xz + zy*mat.yz + zz*mat.zz /*+ zt*mat.tz*/;
        result.zt = zx*mat.xt + zy*mat.yt + zz*mat.zt + zt/*mat.tt*/;
        /*result.tx = tx*mat.xx + ty*mat.yx + tz*mat.zx + tt*mat.tx;
        result.ty = tx*mat.xy + ty*mat.yy + tz*mat.zy + tt*mat.ty;
        result.tz = tx*mat.xz + ty*mat.yz + tz*mat.zz + tt*mat.tz;
        result.tt = tx*mat.xt + ty*mat.yt + tz*mat.zt + tt*mat.tt;*/
        return result;
    }
    /** Initialisation à l'aide d'une autre matrice */
    public void init(Matrix mat) {
        xx=mat.xx;xy=mat.xy;xz=mat.xz;xt=mat.xt;
        yx=mat.yx;yy=mat.yy;yz=mat.yz;yt=mat.yt;
        zx=mat.zx;zy=mat.zy;zz=mat.zz;zt=mat.zt;
        //tx=mat.tx;ty=mat.ty;yz=mat.tz;tt=mat.tt;
    }
    public void clear() {
        xx=yy=zz=/*tt=*/1;
        xy=xz=xt=yx=yz=yt=zx=zy=zt=/*tx=ty=tz=*/0;
    }
    public String toString() {
        NumberFormat frm = new DecimalFormat(" 0.00 ;-0.00 ");
        String result = new String();
        result+="[" + frm.format(xx) + frm.format(xy) + frm.format(xz) + frm.format(xt) + "]\n" +
                "[" + frm.format(yx) + frm.format(yy) + frm.format(yz) + frm.format(yt) + "]\n" +
                "[" + frm.format(zx) + frm.format(zy) + frm.format(zz) + frm.format(zt) + "]\n" /*+
                "[" + frm.format(tx) + frm.format(ty) + frm.format(tz) + frm.format(tt) + "]\n"*/;
        return result;
    }
}