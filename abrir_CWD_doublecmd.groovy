import java.nio.file.Path
import java.nio.file.Paths
Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();

String app = "C:\\Program Files\\Double Commander\\doublecmd.exe";
String arg1= "-c";
String arg2= "-t";
//String file= "E:\\apz";
Runtime runtime = Runtime.getRuntime();
runtime.exec(new String[]{app,arg1,arg2,mmpPathAbs});
//runtime.exec("calc");

//hallar el CWD de este mindmap
//Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();
//c.statusInfo = mmpPathAbs
//textUtils.copyToClipboard(mmpPathAbs.toString())

//Desktop.getDesktop().open(new File(mmpPathAbs.toString()))    //default windows explorer