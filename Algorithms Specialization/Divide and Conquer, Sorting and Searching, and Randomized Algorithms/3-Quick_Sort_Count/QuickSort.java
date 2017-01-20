import java.io.File;
import java.util.Scanner;
public class QuickSort {

    public static long countCompQuickSort(int[] arr, int method){
        if( arr == null || arr.length == 1 ) {
            return 0;
        }

        return countCompQuickSort(arr, method, 0, arr.length-1);
    }
    
    public static long countCompQuickSort(int[] arr, int method, int l, int r) {
        if( l < 0 || r >= arr.length || l >= r ) {
            return 0;
        }
        long count = 0;

        if( method == 1 ){
            // first element is used
        } else if ( method == 2 ) {
            swap(arr, l, r);
            //last element is now the first to be used as pivot
        } else {
            int m = l + (r - l) / 2;
            int a = arr[l];
            int b = arr[m];
            int c = arr[r];
            int median = getMedian(a,b,c);
            if( median == c ) {
                swap(arr, l, r);
            } else if ( median == b ) {
                swap(arr, l, m);
            }
        }

        int pivot = arr[l];
        int i = l+1;
        int j = l+1;
        while( j < r && arr[i] < pivot ) {
            i++;
            j++;
        }
        for(; j <= r; j++ ) {
            if( pivot > arr[j] ) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i-1, l);

        count += r - l;
        count += countCompQuickSort( arr, method, l, i-2);
        count += countCompQuickSort( arr, method, i, r);

        return count;
    }

    public static int getMedian(int a, int b, int c) {
        int x = Math.max(a, Math.max(b,c) );
        if( x == a ) {
            return Math.max(b,c);
        } else if( x == b ) {
            return Math.max(a,c);
        } else {
            return Math.max(a,b);
        }
    }

    public static void swap( int[] arr, int i, int j ) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void print(int[] arr, int i, int j) {
        System.out.print( "\n{" );
        for(; i < j; i++) {
            System.out.print( arr[i] + ", " );
        }
        System.out.print( arr[i] + "}\n" );
    }
  
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("QuickSort.txt"));
            for(int a0 = 0; a0 < 1; a0++){
                int n = 10000;
                int arr[] = new int[n];
                for(int arr_i=0; arr_i < n; arr_i++){
                    arr[arr_i] = in.nextInt();
                }

                int[] brr = arr.clone();
                int[] crr = arr.clone();

                System.out.println( countCompQuickSort(arr,1) );
                System.out.println( countCompQuickSort(brr,2) );
                System.out.println( countCompQuickSort(crr,3) );
            }
        } catch (Exception e){
            //
        }
    }
}