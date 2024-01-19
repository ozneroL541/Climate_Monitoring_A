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

# Paths
bin="bin/"
doc="doc/"
src="src/"
lib="lib/"
obj=$bin
robj="../"
# ClassPath
cp="-cp "$bin""
# Include
inc="LICENSE.txt README.md readme.txt autori.txt"
# Javac Arguments
args="-encoding UTF-8 $cp -d"
srca="$src*/*.java"
# Manifest file
manifest="META-INF/MANIFEST.MF"
# Executable
jar="ClimateMonitor.jar"
# Do not remove
description=".description.txt"

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
# Compile to Objects
compile() {
    # Make dir
    mkdir $obj 2> /dev/null
    if extract_jar; then
        # Compile java
        d="javac $args $obj $srca"
        echo "$d" && eval $d
        res=$?
    else
        res=1
    fi
    result $res "Compilation"
}
# Remove Objects files
rmobj() {
    if cd $obj; then
        # Delete all files except description
        d1="find . ! -name "$description" ! -name "$jar" -type f -delete"
        # Delete all directories
        d2="find . -type d -empty -delete"
        echo "$d1" && eval $d1
        echo "$d2" && eval $d2
        res=$?
        cd $robj
    else
        echo ""
        echo "No bin found"
        echo ""
        res=1
    fi
    result $res "Object removal"
}
# Extract libraries
extract_jar() {
    # Make dir
    mkdir $obj 2> /dev/null
    # Go to bin directory
    if cd $obj; then
        d="jar -xf ../"$lib"commons-lang3-3.1.jar && jar -xf ../"$lib"opencsv-5.5.2.jar"
        echo "$d" && eval $d
        res=$?
        cd $robj
    else
        echo ""
        echo "ERROR: no bin found"
        echo ""
        res=1
    fi
    result $res "JAR extraction"
}
# Change Manifest
change_manifest() {
    # Make dir
    mkdir $obj 2> /dev/null
    # Go to bin directory
    if cd $obj; then
        # Made the MANIFEST.MF file
        echo "echo "Main-Class: src.climatemonitoring.ClimateMonitor" > "$manifest""
        echo "Main-Class: src.climatemonitoring.ClimateMonitor" > "$manifest"
        res=$?
        # Exit up
        cd $robj 
    else
        echo ""
        echo "ERROR: no bin found"
        echo ""
        res=1
    fi
    result $res "Manifest file changing"
}
# Copy files 
copy() {
    # Copy files
    d="cp "$inc" "$bin""
    echo "$d" && eval $d
    result $? "Files copy"
}
# JAR
compile_jar() {
    # Make dir
    mkdir $bin 2> /dev/null
    # Compile java
    if compile && change_manifest && copy && cd $obj; then
        # Make an executable JAR
        d="jar cfm $robj$bin$jar $manifest * */* */*/* */*/*/* */*/*/*/* */*/*/*/*/* */*/*/*/*/*/*"
        echo "$d" && eval $d
        res=$?
        cd $robj
        if result $res "JAR creation"
        then
            # Remove Compiled files
            rmobj
        fi
    fi
}
# Remove JAR
rmjar() {
    d="rm $bin$jar"
    echo "$d" && $d
    result $? "JAR removing"
}
# Document
document() {
    # Make dir
    mkdir $obj 2> /dev/null
    if extract_jar; then
        # Compile java
        d="javadoc -quiet --release v0.28.0-beta -locale  it_IT -doctitle \"Climate Monitor\" -author -version -package -use $args $doc -sourcepath $srca"
        echo "$d" && eval $d
        res=$?
        echo ""
        rmobj
    else
        res=1
    fi
    result $? "Documentation creation"
}
# Remove Documetation
rmdoc() {
    if cd $doc; then
        # Delete all files except description
        d1="find . ! -name $description -type f -delete"
        # Delete all directories
        d2="find . -type d -empty -delete"
        echo "$d1" && eval $d1
        echo "$d2" && eval $d2
        res=$?
        result $res "Documentation removal"
        cd ..
        return $res
    else
        echo ""
        echo "No doc found"
        echo ""
    fi
}
# Compile and Document
comp_doc() {
    # Compile Java and Make JAR
    compile_jar
    # Make JavaDoc
    document
}
# Remove all
rmall() {
    # Remove JAR
    rmjar
    # Remove Object files
    rmobj
    # Remove Documentation
    rmdoc
}
# Help menu
help() {
    echo "Help Menu"
    echo "  -h      print this menu"
    echo "  -c      compile with javac"
    echo "  -j      make executable JAR file and delete object files"
    echo "  -d      make Documentation with javadoc"
    echo "  -r      remove"
    echo "      o   object files"
    echo "      j   JAR file"
    echo "      d   Documentation"
    echo "      *   all"
    echo "  *       build JAR and make Documentation without making garbage"
}

# Move in a directory different from src

# Check the Path
if  [ -n "$(echo $PWD | grep 'Climate_Monitoring')" ]; then
    case $1 in
        # Help
        "h" | "-h" | "help" | "-help" | "--help")
            help
        ;;
        # Compile
        "c" | "-c" | "compile" | "javac" | "-compile" | "--javac" | "--compile")
            compile
        ;;
        # JAR
        "j" | "-j" | "-jar" | "jar" | "--jar")
            compile_jar
        ;;
        # Doument
        "d" | "-d" | "document" | "javadoc" | "-document" | "--document" | "--javadoc")
            document
        ;;
        # Remove
        "r" | "-r" | "rm" | "-rm" | "remove" | "-remove"  | "--remove")
        case $2 in
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
