/*---------------------------------------------------------------------
|  Method VisorImagenes.groovy
|
|  Purpose: Si la foto esta en el Node Core (y no en lower section) este
|           programa abrira dicha foto en un visor
|           ademas usando arrow keys modifica el tag <img > para:
			incluir(↓), quitar(↑), redimensionar(← →) width="" height=""
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
|  Returns: La foto abierta en el visor
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

import java.awt.*
import java.awt.Desktop;
import java.io.File;

import java.util.regex.Pattern
import java.util.regex.Matcher

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import javax.swing.*
import java.awt.event.*

String cadenaHtml = node.getText()                              //texto Html crudo
Pattern miPatron = Pattern.compile("(<img src=\")(.+?)(\")");   //sacar el URL
Matcher miMatcher = miPatron.matcher(cadenaHtml)
miMatcher.find()                            //si quieres mas de 1 usa bucle while( )
String cadena1 = miMatcher.group(2)

Path pathRelatFoto = Paths.get(cadena1) //path de la foto: ../folderFotos/euler.jpg
String mapaActual = node.map.file                       //D:\folder1\folderMapa\mapa1.mm
Path pathAbsolMmp = Paths.get(mapaActual).getParent()   //D:\folder1\folderMapa
Path pathAbsolFoto = pathAbsolMmp.resolve(pathRelatFoto)
pathAbsolFoto = pathAbsolFoto.normalize()

//poner atributos width height si no estan previamente ya puestos
if (cadenaHtml.contains("width=")){ c.statusInfo = "ya tiene atributos" }
else { anchoAltoReset(pathAbsolFoto, miMatcher, cadenaHtml) }

//abrir imagen en el visor predeterminado
Desktop miDesktop = Desktop.getDesktop()
File miFoto = new File(pathAbsolFoto.toString())

if (miFoto.exists()){
    //miDesktop.open(miFoto);
    def frame = new JFrame("Image Viewer")
    def label = new JLabel()

    def imageIcon = new ImageIcon(pathAbsolFoto.toString())
    label.icon = imageIcon
    
    //*******************************  
    def robot = new Robot()
    def punto = MouseInfo.getPointerInfo().getLocation()
    
    frame.getContentPane().add(label)
    frame.setLocation(punto.x.toInteger(), punto.y.toInteger())
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.pack()
    frame.visible = true
    
    frame.addKeyListener(new KeyAdapter() {
        void keyPressed(KeyEvent e) {
                switch (e.keyCode) {
                    case KeyEvent.VK_UP:
                        def patron = /width.+>/
						String cadenaHtmlFinal = cadenaHtml.replaceAll(patron, ">")
						node.setText(cadenaHtmlFinal)       //sobreescribir el HTML del nodo
                        break
                    case KeyEvent.VK_DOWN:
                        anchoAltoReset(pathAbsolFoto, miMatcher, cadenaHtml)
                        break
                    case KeyEvent.VK_LEFT:
                        anchoAltoCambiar(0.9, pathAbsolFoto, miMatcher, cadenaHtml)
                        break
                    case KeyEvent.VK_RIGHT:
                        anchoAltoCambiar(1.1, pathAbsolFoto, miMatcher, cadenaHtml)
                        break
                    case KeyEvent.VK_ESCAPE:
                        frame.dispose()
                        break
                }
        }
    }) 
}

def anchoAltoReset(pathAbsolFoto, miMatcher, cadenaHtml){
	BufferedImage shashin = ImageIO.read(new File(pathAbsolFoto.toString()))    //ancho alto                        
    String cadenaHtmlWH = miMatcher.group(0) +"width=\"${shashin.getWidth()}\" height=\"${shashin.getHeight()}\""
    String cadenaHtmlFinal = cadenaHtml.replaceAll(miMatcher.group(0), cadenaHtmlWH)
    node.setText(cadenaHtmlFinal)       //sobreescribir el HTML del nodo
}

def anchoAltoCambiar(conversor, pathAbsolFoto, miMatcher, cadenaHtml){                        
    String cadenaHtml1 = node.getText()      //texto Html crudo
    
    Pattern miPatternW = Pattern.compile("(width=\")(.+?)(\")");
    Pattern miPatternH = Pattern.compile("(height=\")(.+?)(\")");
    Matcher miMatcherW = miPatternW.matcher(cadenaHtml1);
    Matcher miMatcherH = miPatternH.matcher(cadenaHtml1);
    miMatcherW.find();
    miMatcherH.find();
    
    String nuevoW = String.valueOf((int)(Double.valueOf(miMatcherW.group(2)) * conversor))  //180
    String cadenaW = miMatcherW.group(0).replaceAll(miMatcherW.group(2),nuevoW)     //width="180"
    
    String nuevoH = String.valueOf((int)(Double.valueOf(miMatcherH.group(2)) * conversor))  //240
    String cadenaH = miMatcherH.group(0).replaceAll(miMatcherH.group(2),nuevoH)     //height="240"
    
    String cadenaHtml2 = cadenaHtml1.replaceAll(miMatcherW.group(0),cadenaW)
    cadenaHtml2 = cadenaHtml2.replaceAll(miMatcherH.group(0),cadenaH)
    
    node.setText(cadenaHtml2)
}
