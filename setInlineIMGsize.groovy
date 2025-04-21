/*---------------------------------------------------------------------
|  Method reSizeInlineIMG.groovy
|
|  Purpose: Si la foto esta en el Node Core (y no en lower section) este
|           programa permite redimensionarla usando el tag <img>
|           cambiando los parametros width="" height=""
|
|  Pre-condition: 
|  Post-condition:
|
|  Parameters:
|       - La cadena html cruda del nodo seleccionado
|       - El factor de conversion para aumentar/reducir la foto
|
|  Returns: La cadena Html modificada conteniendo: width="xy" height="xy" modificados
|           El nodo es sobreeescrito con la nueva cadena Html modificada
|
|   注意: ...............
|
|   私について: Homar Richard Orozco Apaza
|               La Paz - Bolivia
|                       2025
*-------------------------------------------------------------------*/
import java.nio.file.Path
import java.nio.file.Paths

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

//------------------------------------------------------------------------------------
import javax.swing.JOptionPane

// Display a dialog box to ask for the user's entry
def porciento = JOptionPane.showInputDialog(null, "cuanto porciento:", "Porciento Input", JOptionPane.QUESTION_MESSAGE)
//------------------------------------------------------------------------------------

String cadenaHTML = node.getText()          //texto HTML crudo
Document doc = Jsoup.parse(cadenaHTML)

def imgTag = doc.select("img").first()

//-------------------------------------------
//path del mindmap actual: 
String mapaActual = node.map.file                       //D:\folder1\folderMapa\mapa1.mm
Path pathAbsolMmp = Paths.get(mapaActual).getParent()   //D:\folder1\folderMapa

// def basePath = System.getProperty("user.dir")   // E:\misapps\AutomateWindowsOS
def imgRelPath = imgTag.attr("src")                // 0douga/0otros/MascarasDeCapa/0media/selectionSaved.gif
def imgAbsPath = new File(pathAbsolMmp.toString(), imgRelPath).getAbsolutePath() //D:\apz\maps\art2d\image\pintura\krita\0douga\0otros\MascarasDeCapa\0media\selectionSaved.gif
// c.statusInfo = imgAbsPath
BufferedImage img = ImageIO.read(new File(imgAbsPath))
def ancho = img.getWidth()
def alto = img.getHeight()

imgTag.attr("width", ((int)(porciento.toInteger()/100*ancho)).toString())
imgTag.attr("height", ((int)(porciento.toInteger()/100*alto)).toString())

// c.statusInfo = imgTag
// c.statusInfo = doc.html()
node.setText(doc.html())
