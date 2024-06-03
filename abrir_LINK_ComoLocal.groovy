import java.awt.Desktop;
import java.io.File;

print("\n-------------------------------------\n")
print("\n-------------------------------------\n")
print("\n-------------------------------------\n")
String linkPathWeb = node.link.text.toString()  // http://192.168.1.12/miServer/mmpsProg/Prog_Java/104_documentos/how_split_a_string.pdf
print(linkPathWeb)
print("\n-------------------------------------\n")
String linkPathAbs = linkPathWeb.replace("http://192.168.1.12/miServer","D:")
linkPathAbs = linkPathAbs.replace("/","\\")
linkPathAbs = linkPathAbs.replace("%20"," ")    // D:\mmpsProg\Prog_Java\104_documentos\how_split_a_string.pdf
print(linkPathAbs)
print("\n-------------------------------------\n")
Desktop miDesktop = Desktop.getDesktop()
File miLink = new File(linkPathAbs)
if(miLink.exists()){
    miDesktop.open(miLink)    
}
