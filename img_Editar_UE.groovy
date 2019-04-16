import java.util.regex.Pattern
import java.util.regex.Matcher
import java.awt.Desktop;
import java.io.File;

if(node.text != null){
    if(node.text.contains("<img src=")){
        String mmpPathWeb = node.text   //<html> <head> <body> http://192.168.1.12/miServer/mmps3D/07_GameEngines/UnrealEngine/11_programacion/107_fotos/000_git_instalacion.jpg
        Pattern miPatron = Pattern.compile("<img src=\"(http://.+?)\""); 
        Matcher miMatcher = miPatron.matcher(mmpPathWeb)
        miMatcher.find()                            //el grupo1:  http://192.168.1.12/01_DCCprograms/Houdini/12_terrains/107_fotos/043_mask_clear.gif
        
        String fotoPathAbs = miMatcher.group(1).replace("http://192.168.1.12/miServer","D:")
        fotoPathAbs = fotoPathAbs.replace("/","\\")
        fotoPathAbs = fotoPathAbs.replace("%20"," ")    //por si el nombre contiene espacios blancos
        
        Desktop miDesktop = Desktop.getDesktop()    //abrir imagen en el visor predeterminado
        File miFoto = new File(fotoPathAbs)
        if (miFoto.exists()){
            miDesktop.open(miFoto);
        }
    }
}