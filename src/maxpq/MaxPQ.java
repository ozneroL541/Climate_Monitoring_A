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

package src.maxpq;

/**
 * Maximum Priority Queue
 * <br>Coda a priorità massima
 * @author Lorenzo Radice
 * @version 0.28.0
 */
public class MaxPQ<T extends Comparable<T>> {
    /** Maximum Priority Queue array */
    private T[] pq; 
    /** Lunghezza Massima */
    private int max = 0;
    /** Lunghezza corrente array */
    private int n = 0;
    /**
     * Costruttore della coda a priorità massima.
     * @param dim lunghezza coda
     */
    @SuppressWarnings (value = { "unchecked" })
    public MaxPQ ( int dim ){
        this.pq = (T[]) new Comparable[dim+1];
        this.max = dim;
    }
    /**
     * Controlla se la coda è vuota
     * @return true se la coda è vuota
     */
    public boolean isEmpty(){
        return n == 0;
    }
    /**
     * Ritorna la dimensione della coda
     * @return lunghezza della coda
     */
    public int size(){
        return n;
    }
    /**
     * Inserisce l'oggetto nello primo spazio vuoto della coda e la riordina.
     * <br><br>Complessità
     * <br>Caso migliore
     * <br>T = O(1)
     * <br>S = O(1)
     * <br>Caso peggiore
     * <br>T = O(log(n))
     * <br>S = O(n)
     * @param v oggetto da inserire
     */
    public void insert(T v) {
        // If the current elemets reached the maximum
        if ( (this.max - 1) < n) {
            if ( pq[1].compareTo(v) >= 0 ) {
                delete();
                // Increment the size of heap
                // Assign v to the first void space of the array
                pq[++n] = v;
                // Reorder the array
                swim(n);
            }
        } else {
            // Increment the size of heap
            // Assign v to the first void space of the array
            pq[++n] = v;
            // Reorder the array
            swim(n);
        }
    }
    /**
     * Elimina l'elemento della coda con la priorità
     * più alta e lo ritorna.
     * <br><br>Complessità
     * <br>Caso migliore
     * <br>T = O(1)
     * <br>Caso peggiore
     * <br>T = O(log(n))
     * @return primo elemento della coda
     */
    public T delete(){
        // Copy the first element of the heap
        T max = pq[1];
        // Exchange the first element with the last one
        // and decrease the lenght of heap
        exch(1,n--);
        // Delete the last element of the array
        pq[n+1] = null;
        // Sink first element
        sink(1);
        // Return first element
        return max;
    }
    /**
     * Costruzione Bottom Up.
     * <br><br>Complessità
     * <br>T = θ(n)
     * Non stabile
     * @param a array da riordinare
     */
    public void buildBU( T[] a){
        // If the lenght of a is smaller than pq
        if( a.length < pq.length ){
            // Assign the lenght of a to n
            n = a.length;
            // For every element of a
            for( int i = 0; i < a.length; i++ )
                // Assign a[i] to pq[i+1]
                pq[i+1] = a[i];
            // From n/2 to 1
            for( int i = n/2; i >= 1; i-- )
                // Sink i
                sink(i);
        }
    }
    /**
     * Ritorna l'array della coda riordinata e svuotandola.
     * <br>Complessità
     * <br>T = O(log(n))
     * @return array ordinato
     */
    @SuppressWarnings (value = { "unchecked" })
    public T[] sort() {
        T[] a = (T[]) new Comparable[n];
        // For every element of the array from the back
        for( int i = n-1; i >= 0; i-- )
            // Assign the item with maximum priority
            // to the following element of the array
            a[i] = this.delete();
        return a;
    }
    /**
     * Legge l'elemento della coda con maggiore priorità.
     * <br>Complessità
     * <br>T = O(1)
     * @return primo elemento
     */
    public T read(){
        // Return the first element of the array
        return pq[1];
    }
    /**
     * Check if pq[i] is less than pq[j]
     * @param i first index
     * @param j second index
     * @return true if pq[i] is less than pq[j]
     */
    private boolean less(int i,int j){
        return pq[i].compareTo(pq[j]) < 0;
    }
    /**
     * Exchange pq[i] with pq[j]
     * @param i first index
     * @param j second index
     */
    private void exch(int i,int j){
        // Auxiliary variable
        T t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    /**
     * Sort the priority of the array.
     * <br>Complexity
     * <br>O(1) best
     * <br>O(log(n)) worst
     * @param k starting point
     */
    private void swim(int k){
        // While k is more than 1
        // and k/2 is less than k
        while( k > 1 && less( k/2, k ) ){
            // Exchange k/2 with k
            exch(k/2, k);
            // Halve k
            k = k/2;
        }
    }
    /**
     * Sort the array starting with k
     * Complexity
     * O(1) best
     * O(log(n)) worst
     * @param k starting point
     */
    private void sink(int k){
        // While 2*k is less than n
        while( 2*k <= n ){
            // Assign 2*k to j
            int j = 2*k;
            // If j is less than n and
            // j is less than j+1
            if( j < n && less( j, j+1 ) )
                // Increase j
                j++;
            // If k is not less than j
            if( !less( k, j ) )
                // Exit
                break;
            // Exchange k with j
            exch( k, j );
            // Assign j to k
            k = j;
        }
    }
}
