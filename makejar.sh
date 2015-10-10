#!/bin/bash
 
echo Elimination des fichiers Class ...
rm matrix3D/engine/*.class
rm matrix3D/engine/fx/*.class
rm matrix3D/geom3D/*.class
rm matrix3D/geom3D/samples/*.class
rm matrix3D/structure/*.class
rm testMatrix/*.class
echo Fini !

echo Compilation en cours ...
echo matrix3D/geom3D
javac matrix3D/geom3D/*.java
echo matrix3D/structure
javac matrix3D/structure/*.java
echo matrix3D/geom3D/fx
javac matrix3D/geom3D/samples/*.java
echo matrix3D/engine
javac matrix3D/engine/*.java
echo matrix3D/engine/fx
javac matrix3D/engine/fx/*.java
echo testMatrix
javac -Xlint:deprecation testMatrix/testEngine.java
echo Fini !

echo Creation du fichier JAR en cours ...
rm matrix3Dsrc.jar
jar cmf Manifest.txt matrix3Dsrc.jar matrix3D testMatrix
echo Compilation terminee !
