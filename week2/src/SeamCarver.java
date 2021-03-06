import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {

    private Picture p;
    private class Node implements Comparable<Node> {
        int x;
        int y;
        double energy;

        public Node(int x, int y, double energy) {
            this.x = x;
            this.y = y;
            this.energy = energy;
        }

        public int compareTo(Node that) {
            if (this.energy < that.energy) return -1;
            else if (this.energy == that.energy) return 0;
            else return 1;
        }
    }

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
            x_db = ( left        & 0xFF) - ( right        & 0xFF),
            verticalComponent = x_dr*x_dr + x_dg*x_dg + x_db*x_db;

        int up = p.getRGB(x, y - 1),
            down = p.getRGB(x, y + 1),
            y_dr = ((up >> 16) & 0xFF) - ((down >> 16) & 0xFF),
            y_dg = ((up >>  8) & 0xFF) - ((down >>  8) & 0xFF),
            y_db = ( up        & 0xFF) - ( down        & 0xFF),
            horizontalComponent = y_dr*y_dr + y_dg*y_dg + y_db*y_db;

        return Math.sqrt(verticalComponent + horizontalComponent);
    }

    public int[] findVerticalSeam() {
        if (width() == 1) return new int[height()];
        double[][] energy = new double[height()][width()];
        for (int j = 0; j < height(); j++)
            for (int i = 0; i < width(); i++)
                // energy[0] represent energies at the top row
                energy[j][i] = energy(i, j);

//        ironically, my brute force runs faster...
        return shortestPathBruteForce(energy);
//        return shortestPathDijkstra(energy);
    }

    public int[] findHorizontalSeam() {
        if (height() == 1) return new int[width()];
        double[][] energy = new double[width()][height()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                // transpose
                energy[i][j] = energy(i, j);

//        ironically, my brute force runs faster...
        return shortestPathBruteForce(energy);
//        return shortestPathDijkstra(energy);
    }

    private int[] shortestPathDijkstra(double[][] energy) {

        int width = energy[0].length;
        int height = energy.length;
        int[][] edgeTo = new int[height][width];

        // initialize distTo to infinity
        double[][] distTo = new double[height][width];
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++)
                distTo[j][i] = Double.POSITIVE_INFINITY;

        // Initialize a priority queue
        IndexMinPQ<Node> pq = new IndexMinPQ<>(width * height);
        for (int i = 0; i < width; i++) pq.insert(i, new Node(i, 0, energy[0][i]));
        int[] out = new int[height];

        while (!pq.isEmpty()) {
            // get distance and coordinates
            Node node = pq.minKey();
            int minIndex = pq.minIndex();
            pq.delete(minIndex);

//            StdOut.println("MIN> x " + node.x + " y " + node.y + " energy " + node.energy);
//            Iterator<Integer> iter = pq.iterator();
//            while (iter.hasNext()) {
//                int key = iter.next();
//                StdOut.println("     x " + pq.keyOf(key).x + " y " + pq.keyOf(key).y + " energy " + pq.keyOf(key).energy);
//            }

            // return early if min is in the bottom row - found shortest path
            if (node.y == height - 1) {
                out[height - 1] = node.x;
                break;
            }
            relaxNode(node, energy, distTo, edgeTo, pq, width);
        }

        for (int i = height - 2; i >= 0; i--) out[i] = edgeTo[i + 1][out[i + 1]];
        return out;
    }

    private void relaxNode(Node node, double[][] energy, double[][] distTo, int[][] edgeTo, IndexMinPQ<Node> pq, int width) {
        if (node.x == 0) {
            relax(node.x, node, energy, distTo, edgeTo, pq, width);
            relax(node.x + 1, node, energy, distTo, edgeTo, pq, width);
        } else if (node.x == width - 1) {
            relax(node.x - 1, node, energy, distTo, edgeTo, pq, width);
            relax(node.x, node, energy, distTo, edgeTo, pq, width);
        } else {
            relax(node.x - 1, node, energy, distTo, edgeTo, pq, width);
            relax(node.x, node, energy, distTo, edgeTo, pq, width);
            relax(node.x + 1, node, energy, distTo, edgeTo, pq, width);
        }
    }

    private void relax(int x, Node node, double[][] energy, double[][] distTo, int[][] edgeTo, IndexMinPQ<Node> pq, int width) {
        int y_down = node.y + 1;
        double new_dist = energy[y_down][x] + node.energy;
        if (new_dist < distTo[y_down][x]) {
            int key = y_down * width + x;
            if (pq.contains(key)) pq.decreaseKey(key, new Node(x, y_down, new_dist));
            else pq.insert(key, new Node(x, y_down, new_dist));
            distTo[y_down][x] = new_dist;
            edgeTo[y_down][x] = node.x;
        }
    }

    // kind of a brute force implementation
    private int[] shortestPathBruteForce(double[][] energy) {

        int width = energy[0].length;
        int height = energy.length;

        int[][] edgeTo = new int[height][width];
        double[][] distTo = new double[height][width];

        for (int i = 0; i < width; i++) {
            distTo[0][i] = energy[0][i];
        }

        for (int j = 1; j < height; j++) {
            int j_up = j - 1;
            for (int i = 0; i < width; i++) {
                // leftmost
                if (i == 0) {
                    if (distTo[j_up][i + 1] < distTo[j_up][i]) {
                        distTo[j][i] = distTo[j_up][i + 1] + energy[j][i];
                        edgeTo[j][i] = i + 1;
                    } else {
                        distTo[j][i] = distTo[j_up][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    }
                // rightmost
                } else if (i == width - 1){
                    if (distTo[j_up][i] < distTo[j_up][i - 1]) {
                        distTo[j][i] = distTo[j_up][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    } else {
                        distTo[j][i] = distTo[j_up][i - 1] + energy[j][i];
                        edgeTo[j][i] = i - 1;
                    }
                // middle
                } else {
                    // upper right is shortest
                    if (distTo[j_up][i - 1] <= distTo[j_up][i] &&
                            distTo[j_up][i - 1] < distTo[j_up][i + 1]) {
                        distTo[j][i] = distTo[j_up][i - 1] + energy[j][i];
                        edgeTo[j][i] = i - 1;
                    // up middle is shortest
                    } else if (distTo[j_up][i] <= distTo[j_up][i + 1] &&
                            distTo[j_up][i] <= distTo[j_up][i - 1]) {
                        distTo[j][i] = distTo[j_up][i] + energy[j][i];
                        edgeTo[j][i] = i;
                    // upper left is shortest
                    } else {
                        distTo[j][i] = distTo[j_up][i + 1] + energy[j][i];
                        edgeTo[j][i] = i + 1;
                    }
                }
            }
        }

        double currentMin = Double.POSITIVE_INFINITY;
        int[] out = new int[height];
        out[height - 1] = 0;
        for (int i = 0; i < width; i++) {
            if (distTo[height - 1][i] < currentMin) {
                currentMin = distTo[height - 1][i];
                out[height - 1] = i;
            }
        }

        for (int i = height - 2; i >= 0; i--) {
            out[i] = edgeTo[i + 1][out[i + 1]];
        }

        return out;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null) throw new IllegalArgumentException("null arg");
        if (seam.length != width()) throw new IllegalArgumentException("invalid seam");
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
        if (seam.length != height()) throw new IllegalArgumentException("invalid seam");
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


//       for (int j = 0; j < sc.height(); j++) {
//           for (int i = 0; i < sc.width(); i++) {
//               StdOut.print(String.format("%.2f ", sc.energy(i, j)));
//           }
//           StdOut.println();
//       }

//       StdOut.println(picture);
//
//       sc.removeHorizontalSeam(new int[] {0,0,0,0});
//       sc.removeVerticalSeam(new int[] {3,3,3,3,3});
//
//       StdOut.println(sc.picture());

//        StdOut.println();
//
        double sum = 0.0;
        int[] test1 = sc.findVerticalSeam();

        for (int i = 0; i < test1.length; i++) {
            StdOut.print(String.format("%s ", test1[i]));
            sum += sc.energy(test1[i], i);
        }
        StdOut.println();
        StdOut.println(sum);
        int[] test2 = sc.findHorizontalSeam();

        sum = 0d;
        for (int i = 0; i < test2.length; i++) {
            StdOut.print(String.format("%s ", test2[i]));
            sum += sc.energy(i, test2[i]);
        }
        StdOut.println();
        StdOut.println(sum);

    }
}
