@echo off
echo Elimination des fichiers Class ...
del matrix3D\engine\*.class
del matrix3D\engine\fx\*.class
del matrix3D\geom3D\*.class
del matrix3D\geom3D\samples\*.class
del matrix3D\structure\*.class
del testMatrix\*.class
echo Fini !

echo Compilation en cours ...
echo matrix3D\geom3D
javac -target 1.4 matrix3D\geom3D\*.java
echo matrix3D\structure
javac -target 1.4 matrix3D\structure\*.java
echo matrix3D\geom3D\fx
javac -target 1.4 matrix3D\geom3D\samples\*.java
echo matrix3D\engine
javac -target 1.4 matrix3D\engine\*.java
echo matrix3D\engine\fx
javac -target 1.4 matrix3D\engine\fx\*.java
echo testMatrix
javac -target 1.4 testMatrix\*.java
echo Fini !

echo Creation du fichier JAR en cours ...
del matrix3Dsrc.jar
jar cmf Manifest.txt matrix3Dsrc.jar matrix3D testMatrix
echo Compilation terminee !
pause > nul