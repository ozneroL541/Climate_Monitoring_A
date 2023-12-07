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

# GNU Bash automatic compiler for ClimateMonitor program

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
    javac $src*/*.java -d $bin -cp $tmp$lib
    result $? "Compilation"
}
# Remove Objects files
function rmobj {
    rm -r $bin$src
    result $? "Object files removing"
}
# Remove JAR
function rmjar {
    if rm $bin$jar
    then
        echo ""
        echo "JAR removing: succeed"
        echo ""
    else
        echo ""
        echo "JAR removing: failed"
        echo ""
        exit -1
    fi
}
# Document
function document {
    if javadoc $src*/*.java -d $doc -cp $tmp$lib
    then
        echo ""
        echo "Documentation creation: succeed"
        echo ""
    else
        echo ""
        echo "Documentation creation: failed"
        echo ""
        exit -1
    fi
}
# Remove Documetation
function rmdoc {
    if cd $doc
    then
        # Delete all files and directories except description
        if find . ! -name $description -type f -delete && find . -mindepth 1 -maxdepth 1 ! -name $description -type d -exec rm -rf {} +
        then
            echo ""
            echo "Documentation removing: succeed"
            echo ""
        else
            echo ""
            echo "Documentation removing: failed"
            echo ""
            exit -1
        fi

        cd ..
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
    fi
}
# Remove all
function rmall {
    rmtmp
    rmjar
    rmdoc
}


# Move in a directory different from src

# Check the Path
if  [ "$(basename "$(pwd)")" == "Climate_Monitoring" ]; then
    case $1 in
        # Temporary
        "t" | "-t" | "tmp" | "temporary")
            maketmp
        ;;
        # Compile
        "c" | "-c" | "compile" | "javac" | "-compile")
            maketmp && (compile; rmtmp)
        ;;
        # JAR
        "j" | "-j" | "-jar" | "jar")
            maketmp && (compile_jar; rmtmp)
        ;;
        # Doument
        "d" | "-d" | "document" | "javadoc" | "-document")
            maketmp && (document; rmtmp)
        ;;
        # Remove
        "r" | "-r" | "remove" | "-remove")
        case $2 in
            # Remove tmp
            "tmp" | "temporary")
                rmtmp
            ;;
            # Remove JAR
            "jar" | "javac")
                rmjar
            ;;
            # Remove Documentation
            "doc" | "documentation")
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
