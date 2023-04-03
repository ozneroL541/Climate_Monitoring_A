'''
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 753747       Masolo      Carlos
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
'''
'''
This script is aimed at automacally generate a menu.
It is not part of the program, it only creates a file in the specified format

    WARNING!
Always left the exit command a the end of the options array.
Otherways the main Java Program will not work, the Menu class should be changed.
'''
#
filename = "menu.txt"
# Options array
options = [
        "Ricerca aree",
        "Visualizzazione parametri climatici associati",
        "Registrazione (Solo operatori autorizzati)",
        "Login (Solo operatori autorizzati)",
        "Creazione centri di monitoraggio (Solo operatori autorizzati)",
        "Inserirmento valori dei parametri climatici (Solo operatori autorizzati)",
        "Esci"
        ]
# Separator String
separator = " - "
# This function makes the string menu with numbers and with the separator
# example:  "1 - Option1/n2 - option2" ...
def Menu_Maker():
    out = ""
    for i in range(len(options)):
        i_str = (str) (i + 1)
        out += i_str +  separator + options[i] + '\n'
    return out
# This function print the string on the fn file
def PrintOnFile(fn, str_to_print ):
    menu = Menu_Maker()
    file = open(fn, "wt")
    n = file.write(str_to_print)
    file.close()
# Print the menu on filename
PrintOnFile(filename, Menu_Maker() )