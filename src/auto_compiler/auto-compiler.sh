#!/bin/bash
#**************************************
# * Matricola    Cognome     Nome
# * 754530       Galimberti  Riccardo
# * 755152       Paredi      Giacomo
# * 753252       Radice      Lorenzo
# * Sede: Como
#**************************************
# GNU Bash automatic compiler for ClimateMonitor program

# Go to the upper directory
if cd $(dirname $(which $0)) && cd ../../
then
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
    echo ""
    echo "Execution: aborted"
    echo ""
fi
