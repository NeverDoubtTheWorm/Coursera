import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.PriorityQueue;
public class Dijkstra {
    public static int n;
    public static WeightedDirectedGraph g;
    public static int[] A;
    public static int[] B;

    public static void main(String[] args) {
        BufferedReader br = null;
        String file;
        if(args.length == 0) {
            file = "dijkstraData.txt";
        } else {
            file = args[0];
        }
        System.out.println(file);
        parseGraph(file);
        //g.print();
        if( n < 1 ) {
            return;
        }

        shortestDistance();
        /*
        for( int i = 1; i < A.length; i++ ) {
            System.out.println(i + " " + A[i]);
        }

        System.out.println(pathTo(5));
        */
        int[] quiz = {7,37,59,82,99,115,133,165,188,197};

            System.out.println();
        for( int i : quiz ) {
            System.out.print(A[i] + ",");
        }
            System.out.println();
    }
    public static void parseGraph(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            n = Integer.parseInt(line, 10);
            g = new WeightedDirectedGraph(n);
            String[] nums;
            String[] edge;
            int u;
            int v;
            int weight;
            while( (line = br.readLine()) != null ) {
                nums = line.split("\\s+");
                u = Integer.parseInt(nums[0], 10);
                for( int i = 1; i < nums.length; i++ ) {
                    edge = nums[i].split(",");
                    v = Integer.parseInt(edge[0], 10);
                    weight = Integer.parseInt(edge[1], 10);
                    g.addEdge(u, v, weight);
                }
            }
        } catch (Exception e){
            System.out.println("Error parsing file: " + file + "\n");
            e.printStackTrace();
        } finally {
            if( br != null ) {
                try {
                    br.close();
                } catch(Exception e) { 
                    //
                }
            }
        }
    }

    public static int[] shortestDistance() {
        A = new int[n+1];
        B = new int[n+1];
        Set<Integer> X = new HashSet<>();
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>(10, (x,y) -> x.value - y.value);
        for(int i = 0; i < A.length; i++) {
            A[i] = Integer.MAX_VALUE;
            B[i] = Integer.MAX_VALUE;
        }
        Pair cur;
        int dijk;
        A[1] = 0;
        B[1] = 0;
        pq.add(new Pair(1, 0));

        while(pq.size() > 0) {
            cur = pq.poll();
            if( X.contains(cur.key) ) {
                continue;
            } else {
                X.add(cur.key);
            }

            for( Pair p : g.outEdges(cur.key) ) {
                dijk = p.value + cur.value;
                if( dijk < A[p.key] ) {
                    A[p.key] = dijk;
                    B[p.key] = cur.key;
                    pq.add(new Pair(p.key, dijk));
                }
            }
        }

        return A;
    }

    public static String pathTo(int t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t);
        while( t > 1 ) {
            t = B[t];
            sb.insert(0,"->");
            sb.insert(0,t);
        }
        if( t != 1 ) {
            return "ERROR: PATH NOT FOUND";
        } else {
            return sb.toString();
        }
    }

}

class Pair { 
    public final int key;
    public final int value;
    public Pair(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class WeightedDirectedGraph {
    // Could use Map/HashMap instead of Set/HashSet but meh
    private List<Set<Pair>> adj;
    private int n;
    //private int m;

    public WeightedDirectedGraph(int n0){
        n = n0;
        adj = new ArrayList<Set<Pair>>(n+1);
        adj.add(0, null);
        for(int i = 1; i <= n; i++) {
            adj.add(i, new HashSet<Pair>());
        }
    }

    public int getVertexCount() {
        return n;
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Pair(v, weight));
        //m++;
    }

    public Set<Pair> outEdges(int v) {
        return adj.get(v);
    }

    public void print(){
        for(int i = 1; i <= n; i++) {
            System.out.print("\n" + i + "  ");
            for( Pair p : adj.get(i) ) {
                System.out.print(p.key + "," + p.value + " ");
            }
        }
        System.out.print("\n");
    }
}