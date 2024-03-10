import javax.swing.*

import org.freeplane.features.map.NodeModel
import org.freeplane.features.mode.Controller
import java.nio.file.Paths
import java.nio.file.Path

import groovy.xml.*
import java.awt.MouseInfo
import java.awt.event.*;

// Get the current mouse pointer coordinates
def mouseCoordenadas = MouseInfo.getPointerInfo().getLocation()
def pathFotoJuego = ""      //la foto o el webGame que se abriran en el miniBrowser

def elNodo = Controller.getCurrentModeController().mapController.rootNode
def elFile = elNodo.map.file
def elFolder = elFile.parent    //D:\apz\maps\progr\leng\java\0libs\swing

def frame = new JFrame("")
frame.setUndecorated(true)
def boton1 = new JButton("imgUP")
def boton2 = new JButton("imgDWN")
def boton3 = new JButton("link")

boton1.addActionListener(new ActionListener() {
    void actionPerformed(ActionEvent e) {
        def htmlText = node.getHtmlText().replace('&nbsp;','&#xA0;')   //<img src="0fotos/jbutton_mnemonic.gif" width="200" height="72">
        
        def patron = ~/(<img.+?>)/          //ya que pide que los <img> sean cerrados por </img>
        def matcher = htmlText =~ patron
        def imgFallada = ""
        def imgCorregida = ""
        if(matcher.find()){
            imgFallada = "${matcher[0][0]}"
            imgCorregida = "${matcher[0][1]}</img>"
        }
        
        def html = new XmlSlurper().parseText(htmlText.replaceAll(imgFallada, imgCorregida))
        def laFotoRel = html.body.img.@src      // 0fotos/jbutton_mnemonic.gif
        pathFotoJuego = Paths.get(elFolder, "/${laFotoRel}")
        frame.dispose()
        //------------------python miniNavegador------------------
        print "abriendo: ${pathFotoJuego}"
        def command = ["python", "F:/navegador.py", pathFotoJuego]
        command.execute()
    }
})

boton2.addActionListener(new ActionListener() {
    void actionPerformed(ActionEvent e) {
        def laFotoRel = node.externalObject.getUri()
        pathFotoJuego = Paths.get(elFolder, laFotoRel)
        frame.dispose()
        //------------------python miniNavegador------------------
        print "abriendo: ${pathFotoJuego}"
        def command = ["python", "F:/navegador.py", pathFotoJuego]
        command.execute()
    }
})

boton3.addActionListener(new ActionListener() {
    void actionPerformed(ActionEvent e) {
        def htmlText = node.getHtmlText().replace('&nbsp;','&#xA0;')   //'<p><a href="https://example.com">eee</a></p>'
        def nodes = new XmlSlurper().parseText(htmlText)
        def links1 = nodes.'**'.findAll { it.name() == 'a' }*.@href*.text()
        // 
        def links2 = []
        if (node.detailsText){
            htmlText = node.detailsText.replace('&nbsp;','&#xA0;')  //details es parte baja del nodo
            nodes = new XmlSlurper().parseText(htmlText)
            links2 = nodes.'**'.findAll { it.name() == 'a' }*.@href*.text()
        }
        def links = links1 + links2
        // 
        int opcion = 0
        if(links.size > 1){
            opcion = JOptionPane.showInputDialog("elegi del rango: 1,...,${links.size}", "").toInteger();
            opcion--
        }
        pathFotoJuego = links[opcion]
        frame.dispose()
        //------------------python miniNavegador------------------
        print "abriendo: ${pathFotoJuego}"
        def command = ["python", "F:/navegador.py", pathFotoJuego]
        command.execute()
    }
})

frame.setLayout(new java.awt.FlowLayout());
frame.add(boton1)
frame.add(boton2)
frame.add(boton3)

frame.setSize(300, 40)
frame.setLocation(mouseCoordenadas)
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

// Add a key listener to the frame
frame.addKeyListener(new KeyAdapter() {
    void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            frame.dispose()
        }
    }
})
// Set the focus to the frame so it can receive key events
frame.setFocusable(true)
frame.requestFocusInWindow()
frame.setVisible(true)
