//simple tool to fix the clutter in mindmap
//in order to use icons as hyperlinks
//output: is in the clipboard ready to be used in html inside mindmaps

import groovy.swing.SwingBuilder
import javax.swing.JFrame
import java.awt.datatransfer.StringSelection
import java.awt.Toolkit

new SwingBuilder().edt {
    frame(title: 'Icon as Hyperlink', size: [300, 200], show: true) {
        
        vbox {
            label('icon name:')
            textField(id: 'tfIcono', 'etn_micro_pic')
            label('desired hyperlink:')
            textField(id: 'tfLink', 'gdev/progr/unreal/progrUE.mm')
            label(id:'textResul', 'Click the button!')
            def combinado = ""
            button(text: 'ejecutar', actionPerformed: {
                
                combinado = '<a href="'+tfLink.text+'"><img src="../0assets/icons/apz/'+tfIcono.text+'.png"></a>'
                textResul.text = combinado
                Toolkit.defaultToolkit.systemClipboard.setContents(new StringSelection(combinado), null)
            })
        }
    }
}

// <a href="gdev/progr/unreal/progrUE.mm"><img src="../0assets/icons/apz/etn_micro_pic.png"></a>