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

# Make Temporary File
function maketmp {
    if rm -r tmp; mkdir $tmp && cd $tmp && mkdir $lib && cd $lib
    then
        echo ""
        echo "tmp dir making: succeed"
        echo ""
        if jar -xf ../../$lib"opencsv-5.5.2.jar"
        then
            echo ""
            echo "JAR extraction: succeed"
            echo ""
        else
            echo ""
            echo "JAR extraction: failed"
            echo ""
            exit -1
        fi
        cd ../..
    else
        echo ""
        echo "tmp dir making: failed"
        echo ""
        exit -1
    fi
}
# Remove Temporary
function rmtmp {
    if rm -r $tmp
    then
        echo ""
        echo "Execution: succedeed"
        echo ""
    else
        echo ""
        echo "Execution: failed"
        echo ""
        exit -1
    fi
}
# Compile
function compile {
    # Compile java
    if javac $src*/*.java -d $bin -cp $tmp$lib
    then
        echo ""
        echo "Compilation: succedeed"
        echo ""
        if cd $bin
        then
            # Make an executable JAR
            if jar cvfe $jar src.climatemonitoring.ClimateMonitor $src*/*.class
            then
                rm -r $src
                echo ""
                echo "JAR creation: succeed"
                echo ""
            else
                echo ""
                echo "JAR creation: failed"
                echo ""
                exit -1
            fi
            cd ..
        fi
    else
        echo ""
        echo "Compilation: failed"
        echo ""
        exit -1
    fi
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


# Move in a directory different from src

# Check the Path
if  [ "$(basename "$(pwd)")" == "Climate_Monitoring" ]; then
    # Create tmp directory
    if maketmp
    then
        # Compile Java and Make JAR
        compile
        # Make JavaDoc
        document
        # Remove Temporary Directory
        rmtmp
    fi
else
    echo "Error: Wrong Path"
fi


