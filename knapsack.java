import java.io.*;
import java.util.*;
import java.text.*;
import static java.lang.System.*;
import static java.lang.Integer.*;
import static java.lang.Double.*;
import static java.lang.Character.*;
import static java.util.Collections.*;
import static java.lang.Math.*;
import static java.util.Arrays.*;

public class knapsack{
    //knapsack method where capacity is the capacity of the knapsack, weight is the array of weights,
    //and vals is the array representing the values of the items.
    //n is the length of the weight/value array.
    //must be modified to also return the indices of the values we are selecting.
    //keep a mirror matrix that contains a list of the elements you have chosen at that point
    public void knapSack(int capacity, int weight[], int vals[])
    {
        int curitem, weight_remaining, total_items = weight.length;
        //K is a matrix representing your knapsack.
        // rows denote the current item you are at,
        // and the columns representing how much weight there is left.
        int K[][] = new int[total_items + 1][capacity + 1];
        ArrayList<Integer>[][] mirror = new ArrayList[total_items + 1][capacity + 1];


        // Builds the table from the bottom up
        for (curitem = 0; curitem <= total_items; curitem++) {
            for (weight_remaining = 0; weight_remaining <= capacity; weight_remaining++) {
                mirror[curitem][weight_remaining] = new ArrayList<Integer>();
                //the 0th item does not exist (there has to be an item after all), therefore all of row 0 is filled with 0
                //When you have 0 weight (capacity) left, you cannot choose any items, therefore all of column 0 is filled with 0
                if (curitem == 0 || weight_remaining == 0){
                    K[curitem][weight_remaining] = 0;
                }

                //if your knapsack still has space for the current item, decide whether or not to choose it
                else if (weight[curitem - 1] <= weight_remaining){
                    //if you choose an item, you must add its current value to the values of the items you have already
                    //chosen. You then have to subtract from the current weight from the remaining capacity of the
                    //knapsack, as you have chosen that item and it is now taking up some space.
                    int choose = vals[curitem - 1] + K[curitem - 1][weight_remaining - weight[curitem - 1]];

                    //if you don't choose an item, the value of the items in your knapsack is the same as it was when you were
                    //deciding about the previous item
                    int dont_choose = K[curitem - 1][weight_remaining];

                    //pick the option that gives you the most value
                    if(choose > dont_choose){
                        K[curitem][weight_remaining] = choose;
                        for(int tmp : mirror[curitem - 1][weight_remaining - weight[curitem - 1]]){
                            mirror[curitem][weight_remaining].add(tmp);
                        }
                        mirror[curitem][weight_remaining].add(curitem - 1);
                    }
                    else{
                        K[curitem][weight_remaining] = dont_choose;
                        for(int tmp : mirror[curitem - 1][weight_remaining]){
                            mirror[curitem][weight_remaining].add(tmp);
                        }
                    }
//                    K[curitem][weight_remaining] = Math.max(choose, dont_choose);
//                    K[i][w] = Math.max(vals[i - 1] + K[i - 1][w - weight[i - 1]], K[i - 1][w]);

                }

                //do not choose the current item since you are over or at capacity
                else
                    K[curitem][weight_remaining] = K[curitem - 1][weight_remaining];
            }
        }

        int max = Integer.MIN_VALUE, maxr = 0, maxc = 0;


        for(int i = 0; i < K.length; i++){
            for(int j = 0; j < K[i].length; j++){
                if(K[i][j] > max){
                    maxr = i;
                    maxc = j;
                    max = K[i][j];
                }
            }
        }
        out.println(mirror[maxr][maxc].size());
        for(int tmp : mirror[maxr][maxc]){
            System.out.print(tmp + " ");
        }
        out.println();
        //the element of the matrix at your maximum capacity and
        //return K[total_items][capacity];
    }
	public void run() throws Exception{
	
	    Scanner f = new Scanner(new File("knapsack.dat"));
	    //Scanner f = new Scanner(System.in);
        while(f.hasNext()){
            int C = f.nextInt();
            int n = f.nextInt();
            int[] vals = new int[n];
            int[] weights = new int[n];
            for(int i = 0; i < n; i++){
                vals[i] = f.nextInt();
                weights[i] = f.nextInt();
            }
            knapSack(C, weights, vals);
        }

        f.close();
        
	}
	public static void main(String[] args) throws Exception{
	
	new knapsack().run();
	
	}
}