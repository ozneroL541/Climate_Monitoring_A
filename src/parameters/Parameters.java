/**************************************
 * Matricola    Cognome     Nome
 * 754530       Galimberti  Riccardo
 * 755152       Paredi      Giacomo
 * 753252       Radice      Lorenzo
 * Sede: Como
***************************************/
/*
    This file is part of Climate Monitoring.

    Climate Monitoring is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Climate Monitoring is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Climate Monitoring.  If not, see <http://www.gnu.org/licenses/>.
 */

package src.parameters;

import java.io.File;
import java.nio.file.FileSystems;
import java.util.Date;

import src.common.CSV_Utilities;
import src.geographicarea.GeographicArea;

/**
 * Un oggetto della classe <code>Parameters</code> rappresenta i parametri
 * rilevati di un'area geografica in una determinata area per un determinato
 * centro di monitoraggio sotto forma di una tabella.
 * @author Lorenzo Radice
 * @version 0.0.1
 */
public class Parameters {
    // Parameters File
    private final static File file = FileSystems.getDefault().getPath("data", "ParametriClimatici.dati.csv").toFile();
    // Header
    private final static String header = "Geoname ID,Vento,Umidità,Pressione,Temperatura,Precipitazioni,Altitudine dei ghiacciai,Massa dei ghiacciai,Note Vento,Note Umidità,Note Pressione,Note Temperatura,Note Precipitazioni,Note Altitudine dei ghiacciai,Note Massa dei ghiacciai";
    // TODO Implement
    // Date
    private Date date = null;
    // Table
    private Table table = null;
    /**
     * Aggiungi i parametri alla fine del file CSV
     * @param ga Area Geografica a cui si riferisce
     * @return true se l'esecuzione è avvenuta correttamente.
     */
    public boolean printToFile( GeographicArea ga ) {
        // Execution succeded
        return CSV_Utilities.addArraytoCSV(file, this.table.toStringsWithID(( "" + ga.getGeoname_id() )), header);
    }
}