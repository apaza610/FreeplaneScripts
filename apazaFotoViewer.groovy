/*---------------------------------------------------------------------
|  Method apazaFotoViewer.groovy
|
|  Purpose: Si la foto esta en el Node Core (y no en lower section) este
|           programa abrira dicha foto en el visor xdefecto del SistemaOperativo
|           ademas modificara el tag <img > para incluir width="" height=""
|
|  Pre-condition: La foto y el MapaMental deben tener una raiz comun, en GNU eso
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
|  Returns: La foto abierta en el visor xdefecto
|           La cadena html modificada conteniendo: width="xy" height="xy"
|
|   注意: Este programa requiere permiso de ejecucion de programas externos !!
|
|   私について: Homar Richard Orozco Apaza
|               La Paz - Bolivia
|                       2018
*-------------------------------------------------------------------*/

import java.nio.file.Path
import java.nio.file.Paths

import java.awt.Desktop;
import java.io.File;

import java.util.regex.Pattern
import java.util.regex.Matcher

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

String cadenaHtml = node.getText()                              //texto Html crudo
Pattern miPatron = Pattern.compile("(<img src=\")(.+?)(\")");   //sacar el URL
Matcher miMatcher = miPatron.matcher(cadenaHtml)
miMatcher.find()                            //si quieres mas de 1 usa bucle while( )
String cadena1 = miMatcher.group(2)

//path de la foto, algo asi: ../folderFotos/euler.jpg
Path pathRelatFoto = Paths.get(cadena1)

//path del mindmap actual: 
String mapaActual = node.map.file                       //D:\folder1\folderMapa\mapa1.mm
Path pathAbsolMmp = Paths.get(mapaActual).getParent()   //D:\folder1\folderMapa

//hallar el path Absoluto de la foto basado en los 2 path anteriores:
Path pathAbsolFoto = pathAbsolMmp.resolve(pathRelatFoto)
pathAbsolFoto = pathAbsolFoto.normalize()

//abrir imagen en el visor predeterminado
Desktop miDesktop = Desktop.getDesktop()
File miFoto = new File(pathAbsolFoto.toString())

if (miFoto.exists()){
    miDesktop.open(miFoto);
}else {
    c.statusInfo = "no existe: " + pathAbsolFoto.toString();
}

//poner atributos width height si no estan previamente ya puestos
if (cadenaHtml.contains("width=")){
    c.statusInfo = "ya tiene atributos"
}else {    
    BufferedImage shashin = ImageIO.read(new File(pathAbsolFoto.toString()))    //lee ancho alto de imagen
    ancho = shashin.getWidth()
    alto = shashin.getHeight()

    String cadenaHtmlWH = miMatcher.group(0) + " width=\"" +ancho+ "\"" + " height=\"" +alto+ "\""
    String cadenaHtmlFinal = cadenaHtml.replaceAll(miMatcher.group(0), cadenaHtmlWH)
    node.setText(cadenaHtmlFinal)       //sobreescribir el HTML del nodo
}

//textUtils.copyToClipboard(cadenaHtmlFinal)
//c.statusInfo = miMatcher.group(0)
