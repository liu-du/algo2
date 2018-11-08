import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

    private int numberOfTeams;
    private String[] teams;
    private int[] w;
    private int[] l;
    private int[] r;
    private int[][] g;

    public BaseballElimination(String filename) {
        In in = new In(filename);
        numberOfTeams = Integer.parseInt(in.readLine());
        teams = new String[numberOfTeams];
        w = new int[numberOfTeams];
        l = new int[numberOfTeams];
        r = new int[numberOfTeams];
        g = new int[numberOfTeams][numberOfTeams];

        int i = 0;
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] a = line.split("\\s+");

            teams[i] = a[0];
            w[i] = Integer.parseInt(a[1]);
            l[i] = Integer.parseInt(a[2]);
            r[i] = Integer.parseInt(a[3]);

            for (int j = 0; j < numberOfTeams; j++) {
                g[i][j] = Integer.parseInt(a[4 + j]);
            }
            i++;
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
        throw new IllegalArgumentException("invalid team name " + team);
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

//   public boolean isEliminated(String team) {
//
//   }
//
//   public Iterable<String> certificateOfElimination(String team) {
//
//   }

//   public static void main(String[] args) {
//       BaseballElimination division = new BaseballElimination(args[0]);
//       for (String team : division.teams()) {
//           if (division.isEliminated(team)) {
//               StdOut.print(team + " is eliminated by the subset R = { ");
//               for (String t : division.certificateOfElimination(team)) {
//                   StdOut.print(t + " ");
//               }
//               StdOut.println("}");
//           }
//           else {
//               StdOut.println(team + " is not eliminated");
//           }
//       }
//   }

    public static void main(String[] args) {
        BaseballElimination b = new BaseballElimination("teams4.txt");
        for (int i = 0; i < b.g.length; i++) {
            for (int j = 0; j < b.g[0].length; j++) {
                StdOut.print(b.g[i][j] + " ");
            }
            StdOut.println();
        }

        for (int i = 0; i < b.teams.length; i++) {
            StdOut.print(b.teams[i] + " ");
            StdOut.print(b.w[i] + " ");
            StdOut.print(b.l[i] + " ");
            StdOut.print(b.r[i] + " ");
            StdOut.println();
        }

        StdOut.println(b.numberOfTeams());
        StdOut.println(b.teams());

        assert b.wins("Montreal") == 77;
        assert b.losses("New_York") == 78;
        assert b.remaining("Atlanta") == 8;
        assert b.against("Atlanta", "New_York") == 6;

    }
}
