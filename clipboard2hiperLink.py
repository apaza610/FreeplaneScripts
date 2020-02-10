################################################
#   Convertir direcciones URI en Hyperlinks
#
#   autor: Homar Orozco Apaza 
#   año: 5527 
################################################
import pyperclip

文字列 = pyperclip.paste()

入力 = input("1) con t=00:00:00\n2) con ?v=1&t=0\n3) simple\n................elegirOpcion:  ")

if 入力.isdigit():
    if len(入力) != 0:          # si no esta vacio
        if int(入力) == 1:
            文字列 = '<a href="'+文字列+'#t=00:00:00">🔗</a>'
        elif int(入力)==2:
            文字列 = '<a href="'+文字列+'?v=1&t=0">🔗</a>'     
    pyperclip.copy(文字列)

else:
    文字列 = '<a href="'+文字列+'">🔗</a>'
    pyperclip.copy(文字列)
