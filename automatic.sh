#!/bin/bash
#
# * Matricola    Cognome     Nome
# * 754530       Galimberti  Riccardo
# * 755152       Paredi      Giacomo
# * 753252       Radice      Lorenzo
# * Sede: Como
#
#    This file is part of Climate Monitoring.
#
#    Climate Monitoring is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    Climate Monitoring is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with Climate Monitoring.  If not, see <http://www.gnu.org/licenses/>.

# Bash automatic compiler for ClimateMonitor program

# Paths
bin="bin/"
doc="doc/"
src="src/"
lib="lib/"
tmp="tmp/"
# ClassPath
cp="-cp $lib"opencsv-5.5.2.jar:"$lib"commons-lang3-3.1.jar:.""
# Javac Arguments
args="$src*/*.java -d"
# Manifest file
manifest="$tmp"MANIFEST.MF""
# Executable
jar="ClimateMonitor.jar"
# Do not remove
description=".description.txt"

# Function Result
function result {
    if [ $1 -eq 0 ]; then
        success "$2"
    else
        failed "$2"
    fi
}
# Positive result
function success {
    echo ""
    echo "$1: succeeded"
    echo ""
}
# Negative Result
function failed {
    echo ""
    echo "$1: failed"
    echo ""
    return -1
}
# Make Temporary File
function maketmp {
    # Make tmp dir
    rmtmp; mkdir $tmp
    res=$?
    if result $res "Temporary directory making"
    then
        # Create the MANIFEST.MF file
        d=$(echo "Main-Class: src.climatemonitoring.ClimateMonitor" > $manifest && echo "Class-Path: ../$lib"opencsv-5.5.2.jar ../"$lib"commons-lang3-3.1.jar"" >> $manifest)
        echo $d && $d
        res=$?
        result $res "Manifest file creation" 
    fi
    return $res
}
# Remove Temporary
function rmtmp {
    if test -d $tmp
    then
        d=$(rm -r $tmp)
        echo $d && $d
        result $? "Temporary directory removing"
    else
        echo ""
        echo "Temporary directory does not exist"
        echo ""
    fi
}
# Compile
function compile_jar {
    # Compile java
    if compile && cd $bin
    then
        # Make an executable JAR
        d=$(jar cvfm $jar ../$manifest src/*/*.class)
        echo $d && $d
        res=$?
        cd ..
        if result $res "JAR creation"
        then
            # Remove Compiled files
            rmobj
        fi
    fi
}
# Compile to Objects
function compile {
    # Compile java
    d=$(javac $args $bin $cp)
    echo $d && $d
    result $? "Compilation"
}
# Remove Objects files
function rmobj {
    d=$(rm -r $bin$src)
    echo $d && $d
    result $? "Object files removing"
}
# Remove JAR
function rmjar {
    d=$(rm $bin$jar)
    echo $d && $d
    result $? "JAR removing"
}
# Document
function document {
    javadoc $args $doc $cp
    result $? "Documentation creation"
}
# Remove Documetation
function rmdoc {
    if cd $doc
    then
        # Delete all files and directories except description
        d=$(find . ! -name $description -type f -delete && find . -mindepth 1 -maxdepth 1 ! -name $description -type d -exec rm -rf {} +)
        echo $d && $d
        res=$?
        result $res "Documentation removing"
        cd ..
        return $res
    else
        echo ""
        echo "No doc found"
        echo ""
    fi
}
# Compile and Document
function comp_doc {
    # Create tmp directory
    maketmp
    # Compile Java and Make JAR
    compile_jar
    # Make JavaDoc
    document
    # Remove Temporary Directory
    rmtmp
}
# Remove all
function rmall {
    rmtmp
    rmjar
    rmobj
    rmdoc
}
# Help menu
function help {
    echo "Help Menu"
    echo "  -h      print this menu"
    echo "  -t      create temporary files"
    echo "  -c      compile with javac"
    echo "  -j      make executable jar and delete object files"
    echo "  -d      make documentation with javadoc"
    echo "  -r      remove"
    echo "      t   temporary files"
    echo "      o   object files"
    echo "      j   JAR file"
    echo "      d   documentation"
    echo "      *   all"
    echo "  *       build jar and make documentation without making garbage"
}

# Move in a directory different from src

# Check the Path
if  [ "$(basename "$(pwd)")" == "Climate_Monitoring" ]; then
    case $1 in
        # Help
        "h" | "-h" | "help" | "-help" | "--help")
            help
        ;;
        # Temporary
        "t" | "-t" | "tmp" | "temporary" | "--tmp" | "--temporary")
            maketmp
        ;;
        # Compile
        "c" | "-c" | "compile" | "javac" | "-compile" | "--javac" | "--compile")
            compile
        ;;
        # JAR
        "j" | "-j" | "-jar" | "jar" | "--jar")
            maketmp && (compile_jar; rmtmp)
        ;;
        # Doument
        "d" | "-d" | "document" | "javadoc" | "-document" | "--document" | "--javadoc")
            maketmp && (document; rmtmp)
        ;;
        # Remove
        "r" | "-r" | "rm" | "-rm" | "remove" | "-remove"  | "--remove")
        case $2 in
            # Remove tmp
            "t" | "tmp" | "temporary")
                rmtmp
            ;;
            # Remove Object files
            "o" | "obj" | "object" | "javac")
                rmobj
            ;;
            # Remove JAR
            "j" | "jar" | "javac")
                rmjar
            ;;
            # Remove Documentation
            "d" |"doc" | "documentation")
                rmdoc
            ;;
            # Remove all
            *)
                rmall
            ;;
        esac
        ;;
        *)
        # Do all
        comp_doc
        ;;
    esac
else
    echo "Error: Wrong Path"
fi
