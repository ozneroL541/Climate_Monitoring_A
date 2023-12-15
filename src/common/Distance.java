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
// TODO JD
class Distance implements Comparable<Distance>{
    private Double dist = 0.0;
    private int line = 0;
    public Distance(double dist, int line) {
        this.dist = dist;
        this.line = line;
    }
    public double getDist() {
        return dist;
    }
    public int getLine() {
        return line;
    }
    @Override
    public int compareTo(Distance arg0) {
        return dist.compareTo(arg0.dist);
    }
    public static Integer[] LineArray( Distance[] d ) {
        Integer[] a = new Integer[d.length];
        for (int i = 0; i < d.length; i++) {
            a[i] = d[i].getLine();
        }
        return a;
    }
    public static Integer[] LineArrayC( Comparable[] arr ) {
        Integer[] a = new Integer[arr.length];
        Distance d = null;
        for (int i = 0; i < arr.length; i++) {
            if ( arr[i] instanceof Distance ) {
                d = (Distance) arr[i];
                a[i] = d.getLine();
            }
        }
        return a;
    }
}
