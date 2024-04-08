/****************************************
* Dado a que Freeplane tiene un Bug que no deja poner Paths relativos en imagenes Externas
* este programa convierte Paths absolutos a relativos para imagenes Externas
* homar orozco apaza - 2024
****************************************/
import java.nio.file.Path;
import java.nio.file.Paths;

// ui.informationMessage("Hola Mundo")

Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent()   // D:\mmps3D\07_GameEngines
String tmpString = node.externalObject.getUri() // file:///D:/apz/maps/gdev/gral/unity/0fotos/arquitectura.png
tmpString = tmpString.replace("file:///","")    // D:/apz/maps/gdev/gral/unity/0fotos/arquitectura.png
Path imgPathAbs = Paths.get(tmpString)
Path imgPathRel = mmpPathAbs.relativize(imgPathAbs)
c.statusInfo = imgPathRel.toString()
// Por alguna razon basta con usar getUri() y setUri()
// node.externalObject.setUri("file:///D:/apz/maps/gdev/gral/unity/0fotos/colorSpace.png")
node.externalObject.setUri(node.externalObject.getUri())
