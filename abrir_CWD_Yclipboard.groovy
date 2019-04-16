import java.nio.file.Path
import java.nio.file.Paths
import java.awt.Desktop
import java.io.File;

//hallar el CWD de este mindmap
Path mmpPathAbs = Paths.get(node.map.file.toString()).getParent();
c.statusInfo = mmpPathAbs
textUtils.copyToClipboard(mmpPathAbs.toString())

Desktop.getDesktop().open(new File(mmpPathAbs.toString()))