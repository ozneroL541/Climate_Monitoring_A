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
        return dist.compareTo(dist);
    }
    public static Integer[] LineArray( Distance[] d ) {
        Integer[] a = new Integer[d.length];
        for (int i = 0; i < d.length; i++) {
            a[i] = d[i].getLine();
        }
        return a;
    }
}
