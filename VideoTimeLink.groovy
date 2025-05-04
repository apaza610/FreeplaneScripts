/*---------------------------------------------------------------------
|  Purpose: Obtener un hyperlink para video MP4 con un tiempo preSeteado
|
|  Pre-condition: el Clipboard debe contener direccion URL del MP4
|                   http://localhost/apz/maps/ZBrush/TransposeGizmo.mp4
|
|  Returns: el Clipboard contendra la URL + el tiempo deseado
|                   http://localhost/apz/maps/ZBrush/TransposeGizmo.mp4#t=666
|
|   注意: ...............
|
|   私について: Homar Richard Orozco Apaza
|               La Paz - Bolivia
|                       2025
*-------------------------------------------------------------------*/
import javax.swing.JOptionPane
import javax.swing.JPanel
import javax.swing.JTextField
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel
import javax.swing.*

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException

import java.awt.datatransfer.StringSelection
import java.awt.datatransfer.ClipboardOwner

// Create GUI components
JPanel panel = new JPanel()

String textFromClipboard = getClipboardText()
textFromClipboard = textFromClipboard.replaceAll("#.*", "")     // delete time if string contains time
JTextField textField = new JTextField(textFromClipboard, 20)
JTextField intField1 = new JTextField("", 4)
JTextField intField2 = new JTextField("", 4)

// Add components to panel
panel.add(new javax.swing.JLabel("videoURL:"))
panel.add(textField)
panel.add(new javax.swing.JLabel("mins:"))
panel.add(intField1)
panel.add(new javax.swing.JLabel("secs:"))
panel.add(intField2)

// Request focus on country field when the dialog shows
SwingUtilities.invokeLater {
    intField1.requestFocusInWindow()
}

// Show dialog
int result = JOptionPane.showConfirmDialog(null, panel, "Input Data", JOptionPane.OK_CANCEL_OPTION)

if (result == JOptionPane.OK_OPTION) {
//     node.text = "Text: " + textField.text + "\nTotalSecs: " + (intField1.value * 60 + intField2.value)
    String resul = ""
    if(intField1.text == ""){
        resul = textField.text + "#t=" + Integer.parseInt(intField2.text)
    }
    else{
        resul = textField.text + "#t=" + (Integer.parseInt(intField1.text) * 60 + Integer.parseInt(intField2.text))
    }
    setClipboardText(resul)
    c.statusInfo = resul
}

// Function to get text from clipboard
String getClipboardText() {
    String result = null
    // Get the system clipboard
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()

    // Get the contents from the clipboard
    // The 'null' argument indicates we are the requestor
    Transferable contents = clipboard.getContents(null)

    // Check if contents are not null and if plain text flavor is supported
    boolean hasTransferableText = (contents != null) &&
                                  contents.isDataFlavorSupported(DataFlavor.stringFlavor)

    if (hasTransferableText) {
        try {
            // Extract the text data
            result = (String) contents.getTransferData(DataFlavor.stringFlavor)
        } catch (UnsupportedFlavorException | IOException ex) {
            // Handle exceptions if the data flavor is no longer available
            // or if there's an I/O error
            System.err.println("Error retrieving clipboard text: " + ex.getMessage())
            // Optionally re-throw or return null/empty string
        }
    }
    return result
}

// Function to set text onto the clipboard
void setClipboardText(String text) {
    // Get the system clipboard
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()

    // Create a Transferable object for the string
    StringSelection stringSelection = new StringSelection(text)

    // Set the contents of the clipboard
    // Pass 'null' for the ClipboardOwner if you don't need ownership callbacks
    clipboard.setContents(stringSelection, null)
}

