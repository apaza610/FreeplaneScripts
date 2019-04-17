import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

linkPathRel = node.link.text.toString()         // ../../mmpsProg/Prog_Java/104_documentos/how_split_a_string.pdf

Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();  // D:\mmps3D\07_GameEngines

Path linkPathAbs = mmpPathAbs.resolve(linkPathRel)  // D:\mmps3D\07_GameEngines\..\..\mmpsProg\Prog_Java\104_documentos\how_split_a_string.pdf
File f = new File(linkPathAbs.toString())           // evitar cosas como ../..
String linkPathCanonico = f.getCanonicalPath()      // D:\mmpsProg\Prog_Java\104_documentos\how_split_a_string.pdf

String linkPathWeb = linkPathCanonico.replace("D:","http://192.168.1.12/miServer")
linkPathWeb = linkPathWeb.replace("\\","/")         // http://192.168.1.12/miServer/mmpsProg/Prog_Java/104_documentos/how_split_a_string.pdf

print("\n----------***------------------------\n")
node.link.text = linkPathWeb