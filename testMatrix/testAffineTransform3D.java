package testMatrix;
import matrix3D.geom3D.*;
import java.lang.Math;
/**
 * Programme de test de la classe AffineTransform3D
 * @author Yannig Perré
 */
public class testAffineTransform3D extends java.lang.Object {
    public static double PI = Math.PI;
    public static void main (String args[]) {
        Vertex p1 = new Vertex(1,2,3);
        AffineTransform3D transform = new AffineTransform3D();
        System.out.println("Test de la classe AffineTransform3D : ");
        System.out.println("Matrice d'identitée : ");
        System.out.println(" Contenu d'une matrice de transformation (identité =" 
                           + "no transformations !;) :");
        System.out.println(transform);
        System.out.println(" Point à transformer :" + p1);
        System.out.println(" Transformation par matrice identité : " 
                           + p1.transform(transform));
        System.out.println("Matrice de translation : ");
        System.out.println(" Contenu d'une matrice de translation");
        transform = AffineTransform3D.getTranslateInv(p1);
        System.out.println(transform);
        System.out.println(" Résultat translation (ici retour à l'origine) : " 
                           + p1.transform(transform));
        System.out.println("Matrice de rotation : ");
        System.out.println(" Contenu d'une matrice de rotation (-PI/2,-PI/2,-PI/2)");
        transform = AffineTransform3D.getRotateInstance(-Math.PI/2,-Math.PI/2,-Math.PI/2);
        System.out.println(transform);
        System.out.println(" Contenu d'une matrice de rotation (0,0,0)");
        transform = AffineTransform3D.getRotateInstance(0,0,0);
        System.out.println(transform);
        System.out.println(" Contenu d'une matrice de rotation (2*PI,2*PI,2*PI)");
        transform = AffineTransform3D.getRotateInstance(2*PI,2*PI,2*PI);
        System.out.println(transform);
        System.out.println("Matrice d'homothety / symetrie : ");
        System.out.println(" Contenu d'une matrice d'homothety (0.5,-1,0.4)");
        transform = AffineTransform3D.getScaleInstance(0.5,-1,0.4);
        System.out.println(transform);
        System.out.println("Transformation d'un point : ");
        System.out.println("rotation par rapport à l'origine : ");
        p1=new Vertex(1,0,0);
        System.out.println(" Point à transformer : " + p1);
        System.out.println(" Rotation (0,0,PI/2) : " 
                           + p1.transform(
                           AffineTransform3D
                           .getRotateInstance(0,0,PI/2)
                           ));
        p1=new Vertex(1,1,1);
        Vertex center = new Vertex(1,1,1);
        System.out.println("\nrotation par rapport à un point quelconque : ");
        System.out.println(" Point à transformer : " + p1);
        System.out.println(" Rotation (Vertex(" + center + ",0,0,PI/2) : " 
                           + p1.transform(
                           AffineTransform3D
                           .getRotateInstance(center,0,0,0)
                           ));
        center = new Vertex(1,1,1);
        p1 = new Vertex(1,1,0);
        System.out.println(" Point à transformer : " + p1);
        System.out.println(" Rotation (Vertex(" + center + ",0,0,PI/2) : " 
                           + p1.transform(
                           AffineTransform3D
                           .getRotateInstance(center,0,0,PI/2)
                           ));
        System.out.println("Test de multiplication de matrice");
        AffineTransform3D mat1 = AffineTransform3D.getTranslateInstance(0,2,0);
        AffineTransform3D mat2 = AffineTransform3D.getTranslateInstance(0,-2,0);
        System.out.println("mat1 = \n" + mat1);
        System.out.println("mat2 = \n" + mat2);
        System.out.println("mat resultat = \n" + mat1.mul(mat2));
        AffineTransform3D matrot = AffineTransform3D.getRotateInstance(2*PI,2*PI,0);
        System.out.println("matrot = \n" + mat1);
        System.out.println("mat resultat = \n" + matrot.mul(mat1.mul(mat2)));
        p1.set(10,20,20);
        System.out.println("Transformation d'un point : " + p1);
        System.out.println("Résultat de la transformation : " + p1.transform(matrot.mul(mat1.mul(mat2))));
        System.out.println("Projection : ");
    }
}