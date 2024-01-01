#!/bin/sh
#
# * Matricola    Cognome     Nome
# * 754530       Galimberti  Riccardo
# * 755152       Paredi      Giacomo
# * 753252       Radice      Lorenzo
# * Sede: Como
#
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.

# Shell automatic compiler for ClimateMonitor program

# Path
tmp="tmp/"
# ClassPath
cp="-cp $lib"opencsv-5.5.2.jar:"$lib"commons-lang3-3.1.jar:.""
# Javac Arguments
args="$src*/*.java -d"
# Manifest file
manifest="$bin"MANIFEST.MF""

# Function Result
result() {
    if [ $1 -eq 0 ]; then
        success "$2"
    else
        failed "$2"
    fi
}
# Positive result
success() {
    echo ""
    echo "$1: succeeded"
    echo ""
}
# Negative Result
failed() {
    echo ""
    echo "$1: failed"
    echo ""
    return 1
}
# Make Temporary File
maketmp() {
    # Make tmp dir
    rmtmp
    mkdir $bin$tmp
    res=$?
    if result $res "Temporary directory making"; then
        # Create the MANIFEST.MF file
        echo "echo "Main-Class: src.climatemonitoring.ClimateMonitor" > "$manifest" && echo "Class-Path: ../"$lib"opencsv-5.5.2.jar ../"$lib"commons-lang3-3.1.jar" >> "$manifest""
        echo "Main-Class: src.climatemonitoring.ClimateMonitor" > "$manifest" && echo "Class-Path: ../"$lib"opencsv-5.5.2.jar ../"$lib"commons-lang3-3.1.jar" >> "$manifest"
        res=$?
        result $res "Manifest file creation" 
    fi
    return $res
}
# Remove Temporary
rmtmp() {
    if test -d $tmp; then
        d="rm -r $tmp"
        echo "$d" && eval $d
        result $? "Temporary directory removal"
    else
        echo ""
        echo "Temporary directory does not exist"
        echo ""
    fi
}
# Compile to Objects
compile() {
    # Compile java
    d="javac $args $bin $cp"
    echo "$d" && eval $d
    result $? "Compilation"
}
# Remove Objects files
rmobj() {
    if cd $bin; then
        # Delete all files except description
        d1="find . ! -name "$description" ! -name "$jar" -type f -delete"
        # Delete all directories
        d2="find . -type d -empty -delete"
        echo "$d1" && eval $d1
        echo "$d2" && eval $d2
        res=$?
        result $res "Object removal"
        cd ..
        return $res
    else
        echo ""
        echo "No bin found"
        echo ""
    fi
}
