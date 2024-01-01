#!//bin/sh

# jar cfm ClimateMonitor.jar ../tmp/MANIFEST.MF */*/* lib/*.jar resources/*.csv

manifest="META-INF/MANIFEST.MF"

mkdir bin

#Compile
javac src/*/*.java -d tmp/ -cp lib/opencsv-5.5.2.jar:lib/commons-lang3-3.1.jar:.
#Extract
cd bin
jar -xf ../lib/commons-lang3-3.1.jar && jar -xf ../lib/opencsv-5.5.2.jar
#Change Manifest
rm $manifest
# Create the MANIFEST.MF file
echo "echo "Main-Class: src.climatemonitoring.ClimateMonitor" > "$manifest""
echo "Main-Class: src.climatemonitoring.ClimateMonitor" > "$manifest"

cd ..

cp LICENSE README.md autori.txt bin/tmp/

cd bin

jar cfm ClimateMonitor.jar META-INF/MANIFEST.MF * */* */*/* */*/*/* */*/*/*/* */*/*/*/*/*

