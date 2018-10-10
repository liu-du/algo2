import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    private Picture p;
    private MinPQ<Double> pq;

    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("null arg");
        p = new Picture(picture);
    }

    public Picture picture() {
        return new Picture(p);
    }

    public int width() {
        return p.width();
    }

    public int height() {
        return p.height();
    }

    private void validateXIndex(int x) {
        if (x < 0 || x >= p.width()) throw new IllegalArgumentException("x outside: " + x);
    }

    private void validateYIndex(int y) {
        if (y < 0 || y >= p.height()) throw new IllegalArgumentException("y outside: " + y);
    }

    public double energy(int x, int y) {
        validateXIndex(x);
        validateYIndex(y);
        if (x == 0 || x == (p.width() - 1) || y == 0 || y == (p.height() - 1))
            return 1000;

        int left = p.getRGB(x - 1, y),
            right = p.getRGB(x + 1, y),
            x_dr = ((left >> 16) & 0xFF) - ((right >> 16) & 0xFF),
            x_dg = ((left >>  8) & 0xFF) - ((right >>  8) & 0xFF),
            x_db = ((left >>  0) & 0xFF) - ((right >>  0) & 0xFF),
            verticalComponent = x_dr*x_dr + x_dg*x_dg + x_db*x_db;

        int up = p.getRGB(x, y - 1),
            down = p.getRGB(x, y + 1),
            y_dr = ((up >> 16) & 0xFF) - ((down >> 16) & 0xFF),
            y_dg = ((up >>  8) & 0xFF) - ((down >>  8) & 0xFF),
            y_db = ((up >>  0) & 0xFF) - ((down >>  0) & 0xFF),
            horizontalComponent = y_dr*y_dr + y_dg*y_dg + y_db*y_db;

        return Math.sqrt(verticalComponent + horizontalComponent);
    }

    public int[] findVerticalSeam() {
        double[][] energy = new double[height()][width()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                // energy[0] represent energies at the top row
                energy[j][i] = energy(i, j);
            }
        }
        return shortestPath(energy);
    }

    public int[] findHorizontalSeam() {
        double[][] energy = new double[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; i < height(); j++) {
                // transpose
                energy[i][j] = energy(i, j);
            }
        }
        return shortestPath(energy);
    }

    // kind of a brute force implementation
    private int[] shortestPath(double[][] energy) {
        int width = energy[0].length;
        int height = energy.length;

        int[][] edgeTo = new int[height][width];
        double[][] distTo = new double[height][width];

        for (int i = 0; i < width; i++) {
            distTo[0][i] = energy[0][i];
        }

        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {
                // leftmost
                if (i == 0) {
                    if (distTo[j - 1][i + 1] < distTo[j - 1][i]) {
                        distTo[j][i] = distTo[j - 1][i + 1] + energy[j][i];
                        edgeTo[j][i] = i + 1;
                    } else {
                        distTo[j][i] = distTo[j - 1][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    }
                // right most
                } else if (i == width - 1){
                    if (distTo[j - 1][i] < distTo[j - 1][i - 1]) {
                        distTo[j][i] = distTo[j - 1][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    } else {
                        distTo[j][i] = distTo[j - 1][i - 1] + energy[j][i];
                        edgeTo[j][i] = i - 1;
                    }
                // middle
                } else {
                    // upper right is shortest
                    if (distTo[j - 1][i + 1] < distTo[j - 1][i] &&
                            distTo[j - 1][i + 1] < distTo[j - 1][i - 1]) {
                        distTo[j][i] = distTo[j - 1][i + 1] + energy[j][i];
                        edgeTo[j][i] = i + 1;
                    // up middle is shortest
                    } else if (distTo[j - 1][i] < distTo[j - 1][i + 1] &&
                            distTo[j - 1][i] < distTo[j - 1][i - 1]) {
                        distTo[j][i] = distTo[j - 1][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    // upper left is shortes
                    } else {
                        distTo[j][i] = distTo[j - 1][i - 1] + energy[j][i];
                        edgeTo[j][i] = i - 1;
                    }
                }
            }
        }

        return new int[10];
    }

    // kind of a brute force implementation
    private int[][] shortestPathTest() {

        double[][] energy = new double[height()][width()];
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                // energy[0] represent energies at the top row
                energy[j][i] = energy(i, j);
            }
        }
        int width = energy[0].length;
        int height = energy.length;

        int[][] edgeTo = new int[height][width];
        double[][] distTo = new double[height][width];

        for (int i = 0; i < width; i++) {
            distTo[0][i] = energy[0][i];
        }

        for (int j = 1; j < height; j++) {
            for (int i = 0; i < width; i++) {
                // leftmost
                if (i == 0) {
                    if (distTo[j - 1][i + 1] < distTo[j - 1][i]) {
                        distTo[j][i] = distTo[j - 1][i + 1] + energy[j][i];
                        edgeTo[j][i] = i + 1;
                    } else {
                        distTo[j][i] = distTo[j - 1][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    }
                    // right most
                } else if (i == width - 1){
                    if (distTo[j - 1][i] < distTo[j - 1][i - 1]) {
                        distTo[j][i] = distTo[j - 1][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    } else {
                        distTo[j][i] = distTo[j - 1][i - 1] + energy[j][i];
                        edgeTo[j][i] = i - 1;
                    }
                    // middle
                } else {
                    // upper right is shortest
                    if (distTo[j - 1][i + 1] < distTo[j - 1][i] &&
                            distTo[j - 1][i + 1] < distTo[j - 1][i - 1]) {
                        distTo[j][i] = distTo[j - 1][i + 1] + energy[j][i];
                        edgeTo[j][i] = i + 1;
                        // up middle is shortest
                    } else if (distTo[j - 1][i] < distTo[j - 1][i + 1] &&
                            distTo[j - 1][i] < distTo[j - 1][i - 1]) {
                        distTo[j][i] = distTo[j - 1][i] + energy[j][i];
                        edgeTo[j][i] = i;
                        // upper left is shortes
                    } else {
                        distTo[j][i] = distTo[j - 1][i - 1] + energy[j][i];
                        edgeTo[j][i] = i - 1;
                    }
                }
            }
        }

        return edgeTo;
    }

//        for (double i: energy[0]) pq.insert(i);
//    boolean[][] marked = new boolean[height][width];
//
//        while (!pq.isEmpty()) {
//        double dist = pq.delMin();
//        for (neigbhor : dist.adj) {
//            if (!marked[j][i]) {
//                pq.insert(energy[j][i]);
//                edgeTo[j][i] = dist.edge;
//                distTo[j][i] = dist + previous_dist;
//            } else if (marked[j][i] && )
//        }
//    }
//
//    int k = Double.POSITIVE_INFINITY;
//        for (int i; i < width; i++) {
//        if (energy[height - 1][i] < k) k = i;
//    }
//
//    int[] out = new int[height];
//        for (int j; j < height; j++) {
//        out[height - j] = edgeTo(j);
//    }


    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("null arg");
        if (p.height() <= 1) throw new IllegalArgumentException("height <= 1");
        validateYIndex(seam[0]);
        for (int i = 1; i < seam.length; i++) {
            validateYIndex(seam[i]);
            int diff = seam[i] - seam[i-1];
            if (diff > 1 || diff < -1) throw new IllegalArgumentException("differ by more than 1");
        }

        Picture new_p = new Picture(width(), height() - 1);
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (j < seam[i])
                    new_p.set(i, j, p.get(i, j));
                else if (j > seam[i])
                    new_p.set(i, j - 1, p.get(i, j));
            }
        }
        p = new_p;
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("null arg");
        if (p.width() <= 1) throw new IllegalArgumentException("width <= 1");
        validateXIndex(seam[0]);
        for (int i = 1; i < seam.length; i++) {
            validateXIndex(seam[i]);
            int diff = seam[i] - seam[i-1];
            if (diff > 1 || diff < -1) throw new IllegalArgumentException("differ by more than 1");
        }

        Picture new_p = new Picture(width() - 1, height());
        for (int j = 0; j < height(); j++) {
            for (int i = 0; i < width(); i++) {
                if (i < seam[j])
                    new_p.set(i, j, p.get(i, j));
                else if (i > seam[j])
                    new_p.set(i - 1, j, p.get(i, j));
            }
        }
        p = new_p;
    }

    public static void main(String[] args) {
       Picture picture = new Picture(args[0]);
       SeamCarver sc = new SeamCarver(picture);

       StdOut.println(sc.width());
       StdOut.println(sc.height());

       for (int j = 0; j < sc.height(); j++) {
           for (int i = 0; i < sc.width(); i++) {
               StdOut.print(String.format("%.2f ", sc.energy(i, j)));
           }
           StdOut.println();
       }

//       StdOut.println(picture);
//
//       sc.removeHorizontalSeam(new int[] {0,0,0,0});
//       sc.removeVerticalSeam(new int[] {3,3,3,3,3});
//
//       StdOut.println(sc.picture());

        StdOut.println();
        StdOut.println();

       int[][] test = sc.shortestPathTest();

        for (int j = 0; j < sc.height(); j++) {
            for (int i = 0; i < sc.width(); i++) {
                StdOut.print(String.format("%s ", test[j][i]));
            }
            StdOut.println();
        }


    }
}
