//Abrir el mindmap presente ya sea con file Explorer o en el servidor Web
import org.freeplane.core.ui.components.UITools
import javax.swing.*
import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Desktop
import java.net.URI

def frame = new JFrame("Seleccionar Destino:")
def buttonGroup = new ButtonGroup()
def radioButton1 = new JRadioButton("lcl/raiz")
def radioButton2 = new JRadioButton("lcl/foto")
def radioButton3 = new JRadioButton("lcl/video")
def radioButton4 = new JRadioButton("web/raiz")
def radioButton5 = new JRadioButton("web/foto")
def radioButton6 = new JRadioButton("web/video")

Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();
radioButton1.addActionListener {
    if (radioButton1.isSelected()) {
        Desktop.getDesktop().open(new File(mmpPathAbs.toString()))
        frame.dispose()
    }
}
radioButton2.addActionListener {
    if (radioButton2.isSelected()) {
        Desktop.getDesktop().open(new File(mmpPathAbs.toString() + "/0fotos"))
        frame.dispose()
    }
}
radioButton3.addActionListener {
    if (radioButton3.isSelected()) {
        Desktop.getDesktop().open(new File(mmpPathAbs.toString() + "/0douga"))
        frame.dispose()
    }
}

File f = new File(mmpPathAbs.toString())           // evitar cosas como ../..
String linkPathCanonico = f.getCanonicalPath()      // D:\mmpsProg\Prog_Java\104_documentos\how_split_a_string.pdf
String linkPathWeb = linkPathCanonico.replace("D:","http://localhost")
linkPathWeb = linkPathWeb.replace("\\","/")        //http://192.168.1.12/miServer/how_split_a_string.pdf
Desktop desktop = Desktop.getDesktop()
radioButton4.addActionListener {
    if (radioButton4.isSelected()) {
        desktop.browse(new URI(linkPathWeb))
        frame.dispose()
    }
}
radioButton5.addActionListener {
    if (radioButton5.isSelected()) {
        desktop.browse(new URI("${linkPathWeb}/0fotos"))
        frame.dispose()
    }
}
radioButton6.addActionListener {
    if (radioButton6.isSelected()) {
        desktop.browse(new URI("${linkPathWeb}/0douga"))
        frame.dispose()
    }
}

buttonGroup.add(radioButton1)
buttonGroup.add(radioButton2)
buttonGroup.add(radioButton3)
buttonGroup.add(radioButton4)
buttonGroup.add(radioButton5)
buttonGroup.add(radioButton6)

def panel = new JPanel()
panel.add(radioButton1)
panel.add(radioButton2)
panel.add(radioButton3)
panel.add(radioButton4)
panel.add(radioButton5)
panel.add(radioButton6)

def framePadre = UITools.getCurrentFrame()
def coordenads = framePadre.location

frame.getContentPane().add(panel)
frame.setLocation(coordenads.x.toInteger() + 800, coordenads.y.toInteger() + 500)
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
frame.pack()
frame.visible = true
