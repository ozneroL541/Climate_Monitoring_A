# Climate Monitoring

Un sistema di monitoraggio di parametri climatici fornito da centri di monitoraggio sul territorio italiano, in grado di rendere disponibili, ad operatori ambientali e comuni cittadini, i dati relativi alla propria zona di interesse.

## Versione
0.21.0 Alpha

## Requisiti software
Richiede Java 17.

## Download
Usando GIT

     git clone https://github.com/ozneroL541/Climate_Monitoring.git

Link per il download in formato compresso:

<https://github.com/ozneroL541/Climate_Monitoring/tree/main>

## Compilazione
Per compilare bisogna trovarsi nella cartella principale della repository.

     cd Climate_Monitoring

### Compilazione automatizzata
---
Per la compilazione automatizzata è richiesta, come prerequisito, la presenza della shell **bash**.

#### GNU/Linux
Tramite linea di comando.

     ./automatic.sh --compile

#### Windows
Siccome Windows non dispone nativamente della shell bash non è disponibile la compilazione automatizzata di default.

È comunque possibile usufruire di questa funzione se si utilizzano applicazioni che utilizzano **bash** come ad esempio **GIT** o **WSL**. In tal caso bisognerà eseguire, *mediante shell bash*, il seguente comando.

     bash automatic.sh --compile

#### MacOS
Tramite linea di comando.

     ./automatic.sh --compile


### Compilazione Manuale
---
#### GNU/Linux
Entrare nella cartella

     cd Climate_Monitoring

##### Compilazione

     javac src/*/*.java -d bin/ -cp lib/opencsv-5.5.2.jar:lib/commons-lang3-3.1.jar:.

##### Creazione JAR Eseguibile
Creare file manifesto temporaneo

     mkdir tmp && echo Main-Class: src.climatemonitoring.ClimateMonitor > tmp/MANIFEST.MF && echo Class-Path: ../lib/opencsv-5.5.2.jar ../lib/commons-lang3-3.1.jar >> tmp/MANIFEST.MF

Entrare nella cartella bin

     cd bin

Creare JAR eseguibile

     jar cvfm ClimateMonitor.jar ../tmp/MANIFEST.MF src/*/*.class

Uscire dalla cartella bin

     cd ..

Rimuovere file manifest temporaneo

     rm -r tmp
## Esecuzione
Per eseguire il programma il JAR eseguibile deve essere all'interno della cartella *bin*.

Lanciare il comando:

     java -jar bin/ClimateMonitor.jar

In altenativa, nel caso si desideri eseguirlo direttamente nella cartella *bin*:

     cd bin 
     java -jar ClimateMonitor.jar

## Autori
- Galimberti Riccardo   @BiskoBerty
- Paredi Giacomo    @Giaki03
- Radice Lorenzo    @ozneroL541

## Licenza

**Climate Monitoring** is free software: you can redistribute it and/or modify
it under the terms of the **GNU General Public License** as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

**Climate Monitoring** is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with **Climate Monitoring**.  If not, see <http://www.gnu.org/licenses/>.
