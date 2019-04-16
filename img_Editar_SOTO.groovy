import java.awt.Desktop;
import java.io.File;

if(node.externalObject.getUri()){
    String cadenaHTML = node.externalObject.getUri().toString()
    if(cadenaHTML.contains("http:")){
        String mmpPathWeb = node.externalObject.getUri()   //http://192.168.1.12/miServer/mmps3D/07_GameEngines/UnrealEngine/11_programacion/107_fotos/000_git_instalacion.jpg
        
        String fotoPathAbs = mmpPathWeb.replace("http://192.168.1.12/miServer","D:")
        fotoPathAbs = fotoPathAbs.replace("/","\\") // D:\mmps3D\07_GameEngines\UnrealEngine\11_programacion\107_fotos\000_git_instalacion.jpg

        Desktop miDesktop = Desktop.getDesktop()    //abrir imagen en el visor predeterminado
        File miFoto = new File(fotoPathAbs)
        if (miFoto.exists()){
            miDesktop.open(miFoto);
        }
    }
}else{
    c.statusInfo = "este nodo no contiene imagen EXTERNA, tal vez sea INLINE !!!"
}