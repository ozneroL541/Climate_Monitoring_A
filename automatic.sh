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
    rmtmp; mkdir $tmp && cd $tmp && mkdir $lib && cd $lib
    res=$?
    if result $res "Temporary directory making"
    then
        # Extract JAR file
        jar -xf ../../$lib"opencsv-5.5.2.jar"
        res=$?
        result $res "JAR extraction"
        cd ../..
    fi
    return $res
}
# Remove Temporary
function rmtmp {
    if test -d $tmp
    then
        rm -r $tmp
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
        jar cvfe $jar src.climatemonitoring.ClimateMonitor $src*/*.class
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
    javac $src*/*.java -d $bin -cp $lib"opencsv-5.5.2.jar" $lib"commons-lang3-3.1.jar"
    result $? "Compilation"
}
# Remove Objects files
function rmobj {
    rm -r $bin$src
    result $? "Object files removing"
}
# Remove JAR
function rmjar {
    rm $bin$jar
    result $? "JAR removing"
}
# Document
function document {
    javadoc $src*/*.java -d $doc -cp $tmp$lib
    result $? "Documentation creation"
}
# Remove Documetation
function rmdoc {
    if cd $doc
    then
        # Delete all files and directories except description
        find . ! -name $description -type f -delete && find . -mindepth 1 -maxdepth 1 ! -name $description -type d -exec rm -rf {} +
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
    if maketmp
    then
        # Compile Java and Make JAR
        compile_jar
        # Make JavaDoc
        document
        # Remove Temporary Directory
        rmtmp
    else
        return -1
    fi
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
            maketmp && (compile; rmtmp)
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
