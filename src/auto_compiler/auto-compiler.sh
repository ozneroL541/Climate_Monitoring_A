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

# Move in a directory different from src

# Go to the upper directory
if cd $(dirname $(which $0)) && cd ../../
then
    pwd
    if  [ "$(basename "$(pwd)")" == "Climate_Monitoring" ]; then
            # Create tmp directory
        if mkdir tmp && cd tmp && mkdir lib && cd lib
        then
            echo ""
            echo "tmp dir making: succeed"
            echo ""
            if jar -xf ../../lib/opencsv-5.5.2.jar
            then
                echo ""
                echo "JAR extraction: succeed"
                echo ""
            else
                echo ""
                echo "JAR extraction: failed"
                echo ""
            fi
            cd ../..
        else
            echo ""
            echo "tmp dir making: failed"
            echo ""
        fi
        # Compile java
        if javac src/*/*.java -d bin/ -cp tmp/lib/
        then
            echo ""
            echo "Compilation: succedeed"
            echo ""
            if cd bin
            then
                # Make an executable JAR
                if jar cvfe ClimateMonitor.jar src.climatemonitoring.ClimateMonitor src/*/*.class
                then
                    rm -r src
                    echo ""
                    echo "JAR creation: succeed"
                    echo ""
                else
                    echo ""
                    echo "JAR creation: failed"
                    echo ""
                fi
                cd ..
            fi
        else
            echo ""
            echo "Compilation: failed"
            echo ""
        fi
        # Make JavaDoc
        if javadoc src/*/*.java -d doc/ -cp tmp/lib/
        then
            echo ""
            echo "JavaDoc creation: succeed"
            echo ""
        else
            echo ""
            echo "JavaDoc creation: failed"
            echo ""
        fi
        # Remove Temporary Directory
        if rm -r tmp
        then
            echo ""
            echo "Execution: succedeed"
            echo ""
        else
            echo ""
            echo "Execution: failed"
            echo ""
        fi
    else
        echo "Error: Wrong Path"
    fi
else
    echo ""
    echo "Execution: aborted"
    echo ""
fi
