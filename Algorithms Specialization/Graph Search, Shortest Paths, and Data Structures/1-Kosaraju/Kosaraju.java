import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
public class Kosaraju {
    public static int n;
    public static DirectedGraph g;
    public static DirectedGraph gRev;
    public static Map<Integer, List<Integer>> SCCs;
    public static Map<Integer, Integer> SCCsizes;

    public static void main(String[] args) {
        String file;
        if(args.length == 0) {
            file = "SCC.txt";
        } else {
            file = args[0];
            System.out.println(file);
        }
        parseGraph(file);
        if( n < 1 ) {
            return;
        }
/*
        g.print();
        System.out.print("\n");
        gRev.print();
        System.out.print("\n");
*/
        int[] f = findSinkTimes(gRev);
/*
        for( int i = 1; i < f.length; i++ ) {
            System.out.println(i + " " + f[i]);
        }
*/
        findSCCs(g, f);

        //printSCCs();
        printKLargest( new ArrayList<Integer>(SCCsizes.values()) , 5);
    }
    public static void parseGraph(String file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            n = Integer.parseInt(line, 10);
            g = new DirectedGraph(n);
            gRev = new DirectedGraph(n);
            String[] nums;
            int u;
            int v;
            while( (line = br.readLine()) != null ) {
                nums = line.split("\\s+");
                u = Integer.parseInt(nums[0], 10);
                v = Integer.parseInt(nums[1], 10);
                g.addEdge(u, v);
                gRev.addEdge(v, u);
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

    public static int[] findSinkTimes(DirectedGraph g) {
        int n = g.getVertexCount();
        int[] f = new int[n+1];
        boolean[] visited = new boolean[n + 1];
                  visited[0] = true;
        int time = 1;
        int parent = n;
        Stack<Integer> dfs = new Stack<>();
        Set<Integer> temp;
        
        while( parent > 0 ) {
            if( !visited[parent] ) {
                dfs.push(parent);

                while( !dfs.empty() ) {
                    int v = dfs.peek();
                    temp = g.outEdges(v);
                    boolean fin = true;
                    visited[v] = true;
                    for( int i : temp ) {
                        if ( !visited[i] ) {
                            dfs.push(i);
                            fin = false;
                            break;
                        }
                    }
                    if( fin ) {
                        f[time++] = dfs.pop();
                    }
                }
            }
            parent--;
        }
        return f;
    }

    public static void findSCCs(DirectedGraph g, int[] f) {
        int n = g.getVertexCount();
        boolean[] visited = new boolean[n + 1];
                  visited[0] = true;
        int parent = n;
        Stack<Integer> dfs = new Stack<>();
        Set<Integer> temp;
        int s;
        SCCs= new HashMap<>();
        SCCsizes= new HashMap<>();
        
        while( parent > 0 ) {
            s = f[parent];
            if( !visited[s] ) {
                SCCsizes.put(s, 1);
                SCCs.put(s, new ArrayList<>() );
                SCCs.get(s).add(s);
                dfs.push(s);
                visited[s] = true;

                while( !dfs.empty() ) {
                    int v = dfs.pop();
                    temp = g.outEdges(v);
                    for( int i : temp ) {
                        if ( !visited[i] ) {
                            SCCsizes.put(s, SCCsizes.get(s) + 1);
                            SCCs.get(s).add(i);
                            dfs.push(i);
                            visited[i] = true;
                        }
                    }
                }
            }
            parent--;
        }
    }

    public static void printSCCs() {
        for( List<Integer> scc : SCCs.values() ) {
            System.out.println(scc);
        }
    }

    public static void printKLargest(List<Integer> values, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>(k);
        for( int i : values ) {
            if( minHeap.size() < k || minHeap.peek() < i) {
                minHeap.offer(i);
            }
            if( minHeap.size() > k ) {
                minHeap.poll();
            }
        }
        int[] topK = new int[k];
        while( minHeap.size() > 0 ) {
            topK[minHeap.size() - 1] = minHeap.poll();
        }
        System.out.print("\n");
        for( int i = 0; i < k-1 ; i++) {
            System.out.print(topK[i] + ", ");
        }
        System.out.print(topK[k-1] + "\n");
    }
}

class DirectedGraph {
    private List<Set<Integer>> adj;
    private int n;
    //private int m;

    public DirectedGraph(int n0){
        n = n0;
        adj = new ArrayList<Set<Integer>>(n+1);
        adj.add(0, null);
        for(int i = 1; i <= n; i++) {
            adj.add(i, new HashSet<Integer>());
        }
    }

    public int getVertexCount() {
        return n;
    }

    /*
    public int getEdgeCount() {
        return m;
    }
    */

    public void addEdge(int u, int v) {
        adj.get(u).add(v);
        //m++;
    }

    public Set<Integer> outEdges(int v) {
        return adj.get(v);
    }

    public void print(){
        for(int i = 1; i <= n; i++) {
            System.out.print("\n" + i);
            for( int j : adj.get(i) ) {
                System.out.print(" " + j);
            }
        }
        System.out.print("\n");
    }
}