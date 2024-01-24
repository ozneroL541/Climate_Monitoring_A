Climate Monitoring


Introduzione
Un sistema di monitoraggio di parametri climatici fornito da centri di monitoraggio sul territorio italiano, in grado di rendere disponibili, ad operatori ambientali e comuni cittadini, i dati relativi alla propria zona di interesse.


Versione
1.0.1


Requisiti software
Richiede Java 17.


Download
Usando GIT.

 git clone https://github.com/ozneroL541/Climate_Monitoring.git

Link per il download in formato compresso:
https://codeload.github.com/ozneroL541/Climate_Monitoring/zip/refs/heads/master


Compilazione
⚠️ Attenzione: La compilazione richiede JDK 17 e che il comando jar sia correttamente funzionante.
Verificare il funzionamento del comando jar tramite l'apposita istruzione jar --version.

Per compilare i file sorgente bisogna trovarsi nella cartella principale della repository.

 cd Climate_Monitoring

Compilazione Automatica
💡 Suggerimento: Il file automatic.sh ha diverse funzionalità, per visualizzarle digitare: ./automatic.sh --help.

Per la compilazione automatica è richiesta, come prerequisito, la presenza della shell sh o di sue derivate.

GNU/Linux
Tramite linea di comando.

Compilazione

 ./automatic.sh --compile

Creazione JAR Eseguibile

 ./automatic.sh --jar

Windows
⚠️ Attenzione: La compilazione automatica per il sistema operativo Windows non è disponibile di default. È possibile utilizzare la compilazione automatica solo attraverso applicazioni che fanno uso di shell, come WSL e GIT.
Utilizzando WSL è possibile incorrere in errori a causa della differenza tra interruzioni di riga di file su DOS e su Unix. Si consiglia di utilizzare strumenti come dos2unix per convertire il formato del file automatic.sh.

Compilazione

 ./automatic.sh --compile

Creazione JAR Eseguibile

 ./automatic.sh --jar

MacOS
Tramite l'applicazione Terminale.

Compilazione

 ./automatic.sh --compile

Creazione JAR Eseguibile

 ./automatic.sh --jar

Compilazione Manuale

GNU/Linux & MacOS
Entrare nella cartella.

 cd Climate_Monitoring

Compilazione
Estrarre i file JAR della cartella lib nella cartella bin.

 cd bin
 jar -xf ../lib/commons-lang3-3.1.jar && jar -xf ../lib/opencsv-5.5.2.jar
 cd ..

Compilare i file sorgente.

 javac -encoding UTF-8 -cp bin -d bin src/*/*.java

Creazione JAR Eseguibile
Successivamente alla compilazione.

Entrare nella cartella bin.

 cd bin

Modificare il file MANIFEST.MF.

 echo Main-Class: src.climatemonitoring.ClimateMonitor > META-INF/MANIFEST.MF

Copiare alcuni file nella cartella bin (opzionale).

 cd ..
 cp LICENSE.txt README.md readme.txt autori.txt bin
 cd bin

Creare JAR eseguibile.

 jar cfm ../bin/ClimateMonitor.jar META-INF/MANIFEST.MF * */* */*/* */*/*/* */*/*/*/* */*/*/*/*/*

Rimuovere file e cartelle diversi dal JAR (opzionale).

 find . ! -name .description.txt ! -name ClimateMonitor.jar -type f -delete
 find . -type d -empty -delete

Uscire dalla cartella (opzionale).

 cd ..

Windows
Tramite CMD.
Entrare nella cartella.

 cd Climate_Monitoring

Compilazione
Estrarre i file JAR della cartella lib nella cartella bin.

 cd bin
 jar -xf ../lib/commons-lang3-3.1.jar && jar -xf ../lib/opencsv-5.5.2.jar
 cd ..

Compilare i file sorgente.

 javac -encoding UTF-8 -cp bin -d bin src\climatemonitoring\*.java src\common\*.java src\geographicarea\*.java src\header\*.java src\maxpq\*.java src\menu\*.java src\monitoringcentre\*.java src\parameters\*.java src\users\*.java

Creazione JAR Eseguibile
Successivamente alla compilazione.

Entrare nella cartella bin.

 cd bin

Modificare il file MANIFEST.MF.

 echo Main-Class: src.climatemonitoring.ClimateMonitor > META-INF/MANIFEST.MF

Copiare alcuni file nella cartella bin (opzionale).

 cd ..
 copy LICENSE.txt bin
 copy README.md bin
 copy readme.txt bin
 copy autori.txt bin
 cd bin

Creare JAR eseguibile.

 jar cfm ..\bin\ClimateMonitor.jar META-INF\MANIFEST.MF *

Rimuovere i file e cartelle diversi dal JAR (opzionale).

 del LICENSE.txt bin README.md readme.txt bin autori.txt convertLanguageToBoolean* m* o* 
 del /f /s /q src\* com\* templates\* META-INF\* org\*
 rmdir /s /q src com templates META-INF org

Uscire dalla cartella (opzionale).

 cd ..


Esecuzione
Per eseguire il programma il JAR eseguibile deve essere all'interno della cartella bin.

Dalla cartella principale lanciare il comando:

 java -jar bin/ClimateMonitor.jar

Eseguire sempre il programma mentre ci si trova nella cartella dove è presente la cartella resources e, qualora fosse presente, la cartella data.

💡 Sebbene sconsigliato, è possibile spostare il file ClimateMonitor.jar in qualsiasi altra cartella, purché venga sempre eseguito, utilizzando il corretto path, dalla cartella dove sono presenti le cartelle sopra citate.

Librerie Esterne utilizzate
Il programma fa uso delle librerie OpenCSV 5.5.2 e Commons-Lang3 3.1.


Autori
Galimberti Riccardo @BiskoBerty
Paredi Giacomo @Giaki03
Radice Lorenzo @ozneroL541


Licenza
Climate Monitoring is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

Climate Monitoring is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with Climate Monitoring. If not, see http://www.gnu.org/licenses/.
