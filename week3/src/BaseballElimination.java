import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BaseballElimination {

    private int numberOfTeams;
    private String[] teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;
    private FordFulkerson ff;
    private RedBlackBST<String, Integer> lookup;
    private Stack<Integer> rest_teams;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = Integer.parseInt(in.readLine());
        teams = new String[numberOfTeams];
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];

        for (int i = 0; i < numberOfTeams; i++) {
            teams[i] = in.readString();
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();

            for (int j = 0; j < numberOfTeams; j++) {
                g[i][j] = in.readInt();
            }
        }
    }

    public int numberOfTeams() {
        return numberOfTeams;
    }

    public Iterable<String> teams() {
        Stack<String> s = new Stack<>();
        for (int i = 0; i < numberOfTeams; i++) {
            s.push(teams[i]);
        }
        return s;
    }

    private int getTeamIndex(String team) {
        for (int i = 0; i < numberOfTeams; i++) {
            if (teams[i].equals(team)) return i;
        }
        throw new IllegalArgumentException("Invalid team name: " + team);
    }

    public int wins(String team) {
        return w[getTeamIndex(team)];
    }

    public int losses(String team) {
        return l[getTeamIndex(team)];
    }

    public int remaining(String team) {
        return r[getTeamIndex(team)];
    }

    public int against(String team1, String team2) {
        return g[getTeamIndex(team1)][getTeamIndex(team2)];
    }

    private int leader() {
        int leader = 0;
        for (int i = 1; i < numberOfTeams; i++) {
            if (w[i] > w[leader]) leader = i;
        }
        return leader;
    }

    private boolean isTriviallyEliminated(String team) {
        int leader = leader();
        int idx = getTeamIndex(team);
        return w[idx] + r[idx] < w[leader];
    }

    public boolean isEliminated(String team) {
        int leader = leader();
        int idx = getTeamIndex(team);
        rest_teams = new Stack<>();
        for (int i = 0; i < numberOfTeams; i++) {
            if (i != idx) rest_teams.push(i);
        }

        // if it's the leader or numberOfTeams == 1, trivially not eliminated
        if (getTeamIndex(team) == leader) return false;
        // trivially eliminated
        else if (isTriviallyEliminated(team)) return true;

        // initialize lookup table
        lookup = new RedBlackBST<>();
        int k = 2; // count number for vertices
        for (Integer i: rest_teams) {
            // add team vertices
            lookup.put(i.toString(), k);
            k++;
            // add game vertices
            for (Integer j : rest_teams) {
                if (j > i) { // ensure traverse all game combinations only once
                    lookup.put(i + "_" + j, k);
                    k++;
                }
            }
        }

        // initialize flow network
        FlowNetwork f = new FlowNetwork(k);
        for (Integer i: rest_teams) {
            for (Integer j : rest_teams) {
                if (j > i) { // ensure traverse all games combination only once
                    // add edges from source to game vertices
                    f.addEdge(new FlowEdge(0, get_idx(i, j, lookup), g[i][j]));
                    // add edges from game vertices to team vertices
                    f.addEdge(new FlowEdge(get_idx(i, j, lookup), get_idx(i, lookup), Double.POSITIVE_INFINITY));
                    f.addEdge(new FlowEdge(get_idx(i, j, lookup), get_idx(j, lookup), Double.POSITIVE_INFINITY));
                }
            }
            // add edges from team vertices to target
            f.addEdge(new FlowEdge(get_idx(i, lookup), 1, w[idx] + r[idx] - w[i]));
        }

        // run FordFulkerson
        ff = new FordFulkerson(f, 0, 1);
        for (FlowEdge e: f.adj(0)) {
            // if any of the edges is not filled up, the team is mathematically eliminated
            if (e.residualCapacityTo(e.to()) != 0) return true;
        }
        return false;
    }

    private int get_idx(Integer i, RedBlackBST<String, Integer> lookup) {
        return lookup.get(i.toString());
    }

    private int get_idx(Integer i, Integer j, RedBlackBST<String, Integer> lookup) {
        return lookup.get(i.toString() + "_" + j.toString());
    }

   public Iterable<String> certificateOfElimination(String team) {
        Stack<String> s = new Stack<>();
        if (!isEliminated(team)) return null;
        else if (isTriviallyEliminated(team)) {
            s.push(teams[leader()]);
        } else {
            for (Integer i : rest_teams) {
                if (ff.inCut(get_idx(i, lookup))) s.push(teams[i]);
            }
        }
       return s;
   }

   public static void main(String[] args) {
       BaseballElimination division = new BaseballElimination(args[0]);
       for (String team : division.teams()) {
           if (division.isEliminated(team)) {
               StdOut.print(team + " is eliminated by the subset R = { ");
               for (String t : division.certificateOfElimination(team)) {
                   StdOut.print(t + " ");
               }
               StdOut.println("}");
           }
           else {
               StdOut.println(team + " is not eliminated");
           }
       }
   }

}
