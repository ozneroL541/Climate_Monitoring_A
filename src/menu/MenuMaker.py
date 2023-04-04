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
import os
# Name of the file
filename = "menu.txt"
# Options array
# Modify this array to modify the options
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
    # Output string
    out = ""
    # for ( int i = 0; i < lenght(options); i++ )
    for i in range(len(options)):
        # Index string creation
        i_str = (str) (i + 1)
        # Output string cration
        out += i_str +  separator + options[i] + '\n'
    return out
# This function print the string on the fn file
def PrintOnFile(fn, str_to_print ):
    # Open the file
    file = open(fn, "wt")
    # Write the string ont the file
    file.write(str_to_print)
    # Close the file
    file.close()
# If exists a file with the same name
if os.path.isfile(filename):
    # Delete the preexisting file
    os.remove(filename)
# Print the menu on filename
PrintOnFile(filename, Menu_Maker() )
