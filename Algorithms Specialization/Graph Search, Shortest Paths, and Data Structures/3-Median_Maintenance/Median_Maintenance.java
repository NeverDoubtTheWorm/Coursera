import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
public class Median_Maintenance {
    public static MedianHeap medHeap;

    public static void main(String[] args) {
        String file;
        if(args.length == 0) {
            file = "Median.txt";
        } else {
            file = args[0];
        }
        System.out.println(file);

        long medianSum = getMedianSum(file);
        medHeap.printRunningSums();
        System.out.println("\n\n" + medianSum % 10000 );

    }
    public static long getMedianSum(String file) {
        BufferedReader br = null;
        long sum = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            MedianHeap medHeap = new MedianHeap();
            String line = null;
            int in = 0;

            while( (line = br.readLine()) != null ) {
                in = Integer.parseInt(line, 10);
                sum += medHeap.add(in);
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

        return sum;
    }
}

class MedianHeap {
    private static PriorityQueue<Integer> topHalf;
    private static PriorityQueue<Integer> botHalf;
    private static List<Integer> runningMedians;

    public MedianHeap() {
        topHalf = new PriorityQueue<>(10, (x,y) -> x - y);
        botHalf = new PriorityQueue<>(10, (x,y) -> y - x);
        runningMedians = new ArrayList<>();
    }

    public int add(int i){
        if( (botHalf.size() == 0) || (botHalf.peek() > i) ) {
            botHalf.add(i);
        } else {
            topHalf.add(i);
        }

        if( botHalf.size() > (topHalf.size() + 1) ) {
            topHalf.add( botHalf.poll() );
        }
        if( topHalf.size() > (botHalf.size() + 1) ) {
            botHalf.add( topHalf.poll() );
        }

        if( topHalf.size() > botHalf.size() ) {
            runningMedians.add( topHalf.peek() );
        } else {
            runningMedians.add( botHalf.peek() );
        }

        return runningMedians.get( runningMedians.size() - 1 );
    }

    public static void printRunningSums() {
        int j = 1;
        for( int i : runningMedians ) {
            System.out.println(j++ + ": " + i);
        }
    }

}