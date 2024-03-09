//Abrir el hyperlink elegido en el miniBrowser (creado en python)
//esta pensado especialmente para abrir small Web Games
import javax.swing.JOptionPane;
import groovy.xml.*

def htmlText = node.getHtmlText().replace('&nbsp;','&#xA0;')   //'<p><a href="https://example.com">eee</a></p>'
def nodes = new XmlSlurper().parseText(htmlText)
def links1 = nodes.'**'.findAll { it.name() == 'a' }*.@href*.text()
// print links[0]

htmlText = node.detailsText.replace('&nbsp;','&#xA0;')  //details es parte baja del nodo
nodes = new XmlSlurper().parseText(htmlText)
def links2 = nodes.'**'.findAll { it.name() == 'a' }*.@href*.text()
def links = links1 + links2

int opcion = 0
if(links.size > 1){
    opcion = JOptionPane.showInputDialog("elegi del rango: 1,...,${links.size}", "").toInteger();
    opcion--
}

def command = ["python", "F:/navegador.py", links[opcion]]
command.execute()
