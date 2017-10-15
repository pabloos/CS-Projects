echo "Compilando el proyecto..."

javac src/DataStructures/*.java src/pract2017/*.java -d . && 

echo "Comprimiendo el proyecto en formato JAR..." && 

jar -cvfm eped2017.jar MANIFEST.MF es > /dev/null && 

echo "Limpiando..."

rm -R es

echo "Proyecto listo para ejecutar"