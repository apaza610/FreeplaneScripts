/*---------------------------------------------------------------------
|  Method img_DarWidthHeight.groovy
|
|  Purpose: Ya sea que la foto este en node CORE o en DETAIL este
|           programa modificara el tag <img > para incluir width="" height=""
|
|  Pre-condition: Solo 1 foto en CORE y/o en DETAL esta permitida
                  La foto y el MapaMental deben tener una raiz comun, en GNU eso
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
|  Returns: La cadena html modificada conteniendo: width="xy" height="xy"
|
|   注意: Este programa requiere permiso de ejecucion de programas externos !!
|
|   私について: Homar Richard Orozco Apaza
|               La Paz - Bolivia
|                       2019
*-------------------------------------------------------------------*/
import javax.swing.*
import java.awt.*
import java.net.URL

import java.nio.file.Path
import java.nio.file.Paths

import java.util.regex.Pattern
import java.util.regex.Matcher

if(node.text != null){
    String cadenaHTML1 = node.getText()             //texto Html crudo del CORE del node
    if (cadenaHTML1.contains("img src=")){
        node.text = miFunction(cadenaHTML1);        //reemplazar el HTML del CORE del nodo
    }
}

if(node.detailsText != null){
    String cadenaHTML2 = node.detailsText     //texto Html crudo del DETAILs del node
    if(cadenaHTML2.contains("img src=")){
        node.detailsText = miFunction(cadenaHTML2); //reemplazar el HTML del DETAILs del nodo
    }
}

public static String miFunction(String cadenaHTML){
    Pattern miPatron = Pattern.compile("(<img src=\")(.+?)(\".*?)(>)");   //sacar el URL
    Matcher miMatcher = miPatron.matcher(cadenaHTML)
    miMatcher.find()                   //si quieres mas de 1 usa bucle while( )

    //String tagIMG = miMatcher.group(0)
    URL direccion = new URL(miMatcher.group(2)) // http://192.168.1.12/miServer/mmps3D/node_RichTextLabel.jpg
    Image foto = new ImageIcon(direccion).getImage()
    String cadenaHTMLfinal = miMatcher.group(1) + miMatcher.group(2) + miMatcher.group(3) +" width=\""+foto.getWidth(null)+"\"" +" height=\""+foto.getHeight(null)+"\"" + miMatcher.group(4)
    return cadenaHTML.replace(miMatcher.group(0),cadenaHTMLfinal)
}

//textUtils.copyToClipboard(cadenaHtmlFinal)
//c.statusInfo = miMatcher.group(0)
