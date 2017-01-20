import java.io.File;
import java.util.Scanner;
public class Inversions {

    public static long countInversions(int[] arr){
        if( arr == null || arr.length == 1 ) {
            return 0;
        }
        int[] aux = arr.clone();
        return countInversions(arr, aux, 0, arr.length-1);
    }
    
    public static long countInversions(int[] arr, int[] aux, int first, int last ) {
        if( first < 0 || last > arr.length || first >= last ) {
            return 0;
        }
        long inversions = 0;
        int mid = first + (last - first) / 2;
        
        inversions += countInversions( aux, arr, first, mid  );
        inversions += countInversions( aux, arr, mid+1, last );
        
        inversions += countSplitandMerge( arr, aux, first, last );
        
        return inversions;
    }
    
    public static long countSplitandMerge(int[] arr, int[] aux, int first, int last) {
        if( first < 0 || last > arr.length || first >= last ) {
            return 0;
        }
        
        long inversions = 0;
        int mid = first + (last - first) / 2;
        int i = first;
        int k = first;
        int j = mid + 1;
        while( i <= mid && j <= last ) {
            if( aux[i] <= aux[j] ) {
                arr[k] = aux[i++];
            } else {
                arr[k] = aux[j++];
                inversions += mid-i+1;
            }
            k++;
        }
        System.arraycopy(aux, i, arr, k,  mid-i+1);
        System.arraycopy(aux, j, arr, k, last-j+1);
        
        return inversions;
    }
  
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("IntegerArray.txt"));
            for(int a0 = 0; a0 < 1; a0++){
                int n = 100000;
                int arr[] = new int[n];
                for(int arr_i=0; arr_i < n; arr_i++){
                    arr[arr_i] = in.nextInt();
                }
                System.out.println(countInversions(arr));
            }
        } catch (Exception e){
            //
        }
    }
}