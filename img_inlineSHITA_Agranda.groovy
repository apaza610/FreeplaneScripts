/*---------------------------------------------------------------------
|  Method apazaFotoPlus.groovy
|
|  Purpose: Si la foto esta en el Node Core (y no en lower section) este
|           programa permite redimensionarla usando el tag <img>
|           cambiando los parametros width="" height=""
|
|  Pre-condition: el tag <img > debe previamente poseer los parametros width="size" height="size"
|                 ya sea manual o automaticamente (con apazaFotoViewer)              
|
|  Post-condition: ..............
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
|                       2018
*-------------------------------------------------------------------*/
import java.util.regex.Pattern
import java.util.regex.Matcher

double conversor = 1.1;         //aumentar o reducir la imagen por este factor

String cadenaHtml1 = node.detailsText      //texto Html crudo

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

//textUtils.copyToClipboard(cadenaHtml2)

node.detailsText = cadenaHtml2
