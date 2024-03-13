#---------------------------------------------------------------
# miniNavegador: for mindmaps as an easy/fast way to visualize:
# boton1: pictures in node (embedded in <img>)or interact with webGames
# boton2: pictures in details of node (external images)
# boton3: to open webGames or webPages

from tkinter import *
from tkinter import messagebox
import webview 
from pathlib import Path
from PIL import Image
from lxml import etree
import sys
import math
import re
from pynput import keyboard

def destroy(window):
    window.destroy()

def main():
	tk = Tk()
	# sys.argv[1] = r'D:\apz\maps\progr\web\js\0libs\ctjs\0docum\0otros\Asteroids.svg'
	[ancho, alto] = [400, 400]
	
	if "http" in sys.argv[1]:											# caso: sitio web o juego
		#creacion de ventana visor de paginas web
		cola = (sys.argv[1].split('_')[-1]).replace('/','')							# 500x500/
		if re.search(r'\d+x\d+', cola):
			[a, b] = cola.split('x')
			[ancho, alto] = [int(a), int(b)]
		window = webview.create_window('~~', sys.argv[1], None, None, ancho, alto+33, frameless=False, x=int(sys.argv[2]), y=600-int(alto/2))
		
		# Create a listener for keyboard events
		listener = keyboard.Listener(on_press=lambda key: key == keyboard.Key.esc and window.destroy())
		listener.start()
		webview.start()

	else:																# caso: algun tipo de imagen
		if sys.argv[1].endswith('.svg'):
			tree = etree.parse(sys.argv[1])
			root = tree.getroot()
			ancho = 100
			alto = 100
			ancho1 = root.attrib.get('width')
			alto1 = root.attrib.get('height')
			if "px" in ancho1:
				ancho = int( ancho1.replace('px','') )
				alto  = int( alto1.replace('px','') )
			elif "mm" in ancho1:
				ancho = int( float(ancho1.replace('mm','')) * 3.78 )
				alto = int( float(alto1.replace('mm','')) * 3.78 )

			uri_path = Path(sys.argv[1]).as_uri()

			#creacion de ventana visor de paginas web
			window = webview.create_window('~~', uri_path, None, None, ancho, alto, frameless=True, x=tk.winfo_pointerx(), y=tk.winfo_pointery())

			# Create a listener for keyboard events
			listener = keyboard.Listener(on_press=lambda key: key == keyboard.Key.esc and window.destroy())
			listener.start()
			webview.start()

		else:
			img = Image.open(sys.argv[1])
			ancho = img.width + 5	#393, 118
			alto = img.height + 5
			uri_path = Path(sys.argv[1]).as_uri()

			#creacion de ventana visor de paginas web
			window = webview.create_window('~~', uri_path, None, None, ancho, alto, frameless=True, x=tk.winfo_pointerx(), y=tk.winfo_pointery())

			# Create a listener for keyboard events
			listener = keyboard.Listener(on_press=lambda key: key == keyboard.Key.esc and window.destroy())
			listener.start()
			webview.start()

	# tk.bind('<Escape>', lambda: tk.destroy())

if __name__ == '__main__':
	main()
