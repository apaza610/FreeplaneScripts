//Abrir el mindmap presente ya sea con file Explorer o en el servidor Web
import javax.swing.JOptionPane
import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Desktop
import java.net.URI

String opcion = JOptionPane.showInputDialog(null, "(d)irectorio, (f)otos, (v)ideos, (w)eb")

Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();
c.statusInfo = mmpPathAbs
//textUtils.copyToClipboard(mmpPathAbs.toString())

File f = new File(mmpPathAbs.toString())           // evitar cosas como ../..
String linkPathCanonico = f.getCanonicalPath()      // D:\mmpsProg\Prog_Java\104_documentos\how_split_a_string.pdf

switch (opcion){
    case 'd':
        Desktop.getDesktop().open(new File(mmpPathAbs.toString()))
        break
    case 'f':
        Desktop.getDesktop().open(new File(mmpPathAbs.toString() + "/0fotos"))
        break
    case 'v':
        Desktop.getDesktop().open(new File(mmpPathAbs.toString() + "/0douga"))
        break
    case 'w':
        String linkPathWeb = linkPathCanonico.replace("D:","http://localhost")
        linkPathWeb = linkPathWeb.replace("\\","/")        //http://192.168.1.12/miServer/how_split_a_string.pdf
        
        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop()    
            if(desktop.isSupported(Desktop.Action.BROWSE)){
                desktop.browse(new URI(linkPathWeb))
            }
        }
        break
}

