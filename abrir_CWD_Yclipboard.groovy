//Abrir el mindmap presente ya sea con file Explorer o en el servidor Web
import javax.swing.*
import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Desktop
import java.net.URI

// Create the radio buttons
def option1 = new JRadioButton("lcl/raiz")
def option2 = new JRadioButton("lcl/fotos")
def option3 = new JRadioButton("lcl/douga")
def option4 = new JRadioButton("web/raiz")
def option5 = new JRadioButton("web/fotos")
def option6 = new JRadioButton("web/douga")
// Group the radio buttons
def group = new ButtonGroup()
group.add(option1)
group.add(option2)
group.add(option3)
group.add(option4)
group.add(option5)
group.add(option6)

// Select the first option by default
option1.setSelected(true)

// Create a panel and add the radio buttons
def panel = new JPanel()
panel.add(option1)
panel.add(option2)
panel.add(option3)
panel.add(option4)
panel.add(option5)
panel.add(option6)

// Show the JOptionPane
def opcion = JOptionPane.showConfirmDialog(null, panel, "Choose an option",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE)

Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();

File f = new File(mmpPathAbs.toString())           // evitar cosas como ../..
String linkPathCanonico = f.getCanonicalPath()      // D:\mmpsProg\Prog_Java\104_documentos\how_split_a_string.pdf
String linkPathWeb = linkPathCanonico.replace("D:","http://localhost")
linkPathWeb = linkPathWeb.replace("\\","/")        //http://192.168.1.12/miServer/how_split_a_string.pdf
Desktop desktop = Desktop.getDesktop()    

if(option1.isSelected()){Desktop.getDesktop().open(new File(mmpPathAbs.toString()))}
else if(option2.isSelected()){Desktop.getDesktop().open(new File(mmpPathAbs.toString() + "/0fotos"))}
else if(option3.isSelected()){Desktop.getDesktop().open(new File(mmpPathAbs.toString() + "/0douga"))}
else if(option4.isSelected()){desktop.browse(new URI(linkPathWeb))}
else if(option5.isSelected()){desktop.browse(new URI("${linkPathWeb}/0fotos"))}
else if(option6.isSelected()){desktop.browse(new URI("${linkPathWeb}/0douga"))}

