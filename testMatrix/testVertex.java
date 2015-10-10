package testMatrix;
import matrix3D.geom3D.Vertex;
/**
 * Programme de test de la classe Vertex
 * @author Yannig Perré
 */
public class testVertex extends java.lang.Object {
    public static void main (String args[]) {
        Vertex p1 = new Vertex(0,0,0);
        Vertex p2 = new Vertex(0,2,0);
        Vertex p3 = new Vertex(3,0,0);
        Vertex vector1 = new Vertex(p1,p2);
        Vertex vector2 = new Vertex(p2,p1);
        Vertex ortho = new Vertex(Vertex.getNorm(p2,p1,p3));
        System.out.println("Test de la classe Vertex : ");
        System.out.println("Tests élémentaires : ");
        System.out.println(" p1 : " + p1);
        System.out.println(" p2 : " + p2);
        System.out.println(" p3 : " + p3);
        System.out.println("Formation de vecteur à l'aide de 2 points : ");
        System.out.println("           ____>");
        System.out.println(" vecteur1 (p1,p2) : " + vector1);
        System.out.println("           ____>");
        System.out.println(" vecteur2 (p2,p1) : " + vector2);
        System.out.println("Norme d'une surface formée par 2 vecteurs : ");
        System.out.println(" vecteur orthogonal au plan formé par p1,p2,p3 :\n"
                           + ortho);
        System.out.println(" vecteur orthogonal au plan formé par p1,p2,p3 normé :\n" 
                           + ortho.norm());
        Vertex p2inv = new Vertex(p2.norm(),true);
        System.out.println("Test sur les produits scalaires (modéle de la lumière)");
        System.out.println(" p2 : " + p2);
        System.out.println(" p2inv : " + p2inv);
        System.out.println(" norm(p2,p2inv) : " + p2.vector(p2inv));
        System.out.println(" produit scalaire de 2 vecteurs p2 et p3 :\n" 
                           + p2.mul(p3));
        System.out.println(" produit scalaire de 2 vecteurs inverses :\n"
                           + p2inv.mul(p2));
        System.out.println(" produit scalaire de 2 vecteurs identiques :\n"
                           + p2.mul(p2));
        System.out.println(" produit scalaire de 2 vecteurs orthogonaux :\n"
                           + p2.mul(p3.norm()));
        p1.set(1,0,0).norm();
        p2.set(1,1,0).norm();
        System.out.println(" produit scalaire de 2 vecteurs (angle < 90) :\n"
                           + p2.mul(p1));
        p1.set(1,0,0).norm();
        p2.set(-1,1,0).norm();
        System.out.println(" produit scalaire de 2 vecteurs (angle > 90) :\n"
                           + p2.mul(p1));
    }
}