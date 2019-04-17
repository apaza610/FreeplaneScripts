/*---------------------------------------------------------------------
|  Method img_ConversionLOCAL_WEB.groovy
|
|  Purpose: Ya sea que la foto este en node CORE o en DETAIL este
|           programa apuntara el src del tag <img> de path RELATIVO a uno WEB
|
|  Pre-condition: Solo 1 foto en CORE y/o en DETAL esta permitida
|                 La foto y el MapaMental deben tener una raiz comun, en GNU eso
|                 es automatico, pero en Windows (multiples root) significa que 
|                 ambos debieran estar en el mismo disco.              
|
|  Post-condition: ..............
|
|  Parameters:
|       - El path absoluto del MapaMental.mm
|       - El path relativo de la foto.jpg en el nodo seleccionado
|       - La cadena html cruda del nodo seleccionado
|
|  Returns: todo el <html> del nodo original pero con el <img src=""> modificado
|
|   注意: Este programa requiere permiso de ejecucion de programas externos + red !!
|
|   私について: Homar Richard Orozco Apaza
|               La Paz - Bolivia
|                       2019
-------------------------------------------------------------------*/
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import java.util.regex.Matcher
import java.io.File

//hallr el CWD al de este Mindmap:
Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent(); // D:\mmps3D\07_GameEngines

if(node.text != null){
    String cadenaHTML1 = node.getText();
    if(cadenaHTML1.contains("img src=") && !cadenaHTML1.contains("<img src=\"http://")){
        node.text = conversorLocalWeb(cadenaHTML1, mmpPathAbs);
    }
}
if(node.detailsText != null){
    String cadenaHTML2 = node.detailsText;
    if(cadenaHTML2.contains("img src=") && !cadenaHTML2.contains("<img src=\"http://")){
        node.detailsText = conversorLocalWeb(cadenaHTML2, mmpPathAbs);
    }
}

public static String conversorLocalWeb(String cadenaHTML, mmpPathAbs){
    Pattern miPatron = Pattern.compile("(<img src=\")(.+?)(\">)");   //para sacar el URL
    Matcher miMatcher = miPatron.matcher(cadenaHTML);
    miMatcher.find();                           //ejecutar la busqueda
    String fotoPathRel = miMatcher.group(2);    // ../Folder2/Einstein.jpg

    Path fotoPathAbs = mmpPathAbs.resolve(fotoPathRel)  // D:\mmps3D\07_GameEngines\Godot\textures\2018-03-02%2013-09-16.jpg
    File f = new File(fotoPathAbs.toString())
    String fotoPathCanonico = f.getCanonicalPath()      // evitar cosas como ../..

    /******conversion en link web*******/
    String fotoPathWeb = fotoPathCanonico.replace("D:","http://192.168.1.12/miServer"); // OJO el disco que estas usando para servidor !!!
    fotoPathWeb = fotoPathWeb.replace("\\","/");        // http://192.168.1.12/mmps3D/07_GameEngines/Godot/textures/2018-03-02%2013-09-16.jpg

    /*******reconstruccion de la cadena*****************************/
    return cadenaHTML.replace(fotoPathRel,fotoPathWeb);
}
