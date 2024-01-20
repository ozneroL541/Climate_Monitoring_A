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

package src.common;

import src.maxpq.MaxPQ;

/**
 * Classe di appoggio per la ricerca delle coordinate.
 * Memorizza al suo interno la distanza di una coordinata dalla coordinata cercata e la linea di riferimento.
 * @author Lorenzo Radice
 * @version 0.30.0
 */
class Distance implements Comparable<Distance>{
    /**
     * Dato un array di oggetti della classe <code>Distance</code> il metodo ritorna l'array delle righe.
     * @param a2 array delle distanze
     * @return array delle linee
     */
    public static Integer[] toLineArray( Comparable<Distance>[] a2 ) {
        // Lines Array
        Integer[] a = new Integer[a2.length];
        // Support Distance
        Distance d = null;
        for (int i = 0; i < a2.length; i++) {
            if ( a2[i] instanceof Distance ) {
                d = (Distance) a2[i];
                a[i] = d.getLine();
            }
        }
        return a;
    }
    /**
     * Dato un array di oggetti della classe <code>Distance</code> il metodo ritorna l'array delle righe.
     * @param a2 array delle distanze
     * @return array delle linee
     */
    public static Integer[] toLines( MaxPQ<Distance> a2 ) {
        // Lines Array
        Integer[] a = new Integer[a2.size()];
        // For every element of the array from the back
        for( int i = a2.size()-1; i >= 0; i-- )
            // Assign the item with maximum priority
            // to the following element of the array
            a[i] = ((Distance) a2.delete()).getLine();
        return a;
    }
    /**
     * Distanza delle coordinate dal punto cercato.
     */
    private Double dist = 0.0;
    /**
     * Linea dove si trova l'elemento di cui si è calcolata la distanza.
     */
    private int line = 0;
    /**
     * Costruttore dell'oggetto Distance.
     * Assegna la distanza e la linea di riferimento nel file.
     * @param dist distanza
     * @param line riga
     */
    public Distance(double dist, int line) {
        this.dist = dist;
        this.line = line;
    }
    /**
     * Restituisce il campo distanza come <code>double</code>
     * @return distanza
     */
    public double getDist() {
        return dist;
    }
    /**
     * Restituisce il campo riga come <code>int</code>
     * @return riga
     */
    public int getLine() {
        return line;
    }
    /**
     * Compara un oggetto Distance con un altro oggetto della stessa classe.
     */
    @Override
    public int compareTo(Distance arg0) {
        return dist.compareTo(arg0.dist);
    }
}
