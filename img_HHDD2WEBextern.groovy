import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.regex.Matcher

/************Para ambos casos de imagenes:  EXTERNa e INLINe***************/
String fotoPathRel = node.externalObject.getUri()   // imagen EXTERN

/***********path absoluto de este mindmap**************/    //print("\n-------------------------------------\n")
Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent() // D:\mmps3D\07_GameEngines
System.setProperty("user.dir", mmpPathAbs.toString());              // settingUp el CWD
Path fotoPathAbs = mmpPathAbs.resolve(fotoPathRel)  // D:\mmps3D\07_GameEngines\Godot\textures\2018-03-02%2013-09-16.jpg

/******conversion en link web*******/
String fotoPathWeb = fotoPathAbs.toString().replace("D:","http://192.168.1.12/miServer"); // OJO el disco que estas usando para servidor !!!
fotoPathWeb = fotoPathWeb.replace("\\","/");        // http://192.168.1.12/mmps3D/07_GameEngines/Godot/textures/2018-03-02%2013-09-16.jpg

/*******reconstruccion de la cadena*****************************/
node.externalObject.setUri(fotoPathWeb)     //setingup el hyperlink
