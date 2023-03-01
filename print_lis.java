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

public class print_lis{
    //helper binary search method
    static int GetCeilIndex(int nums[], int tail[], int left, int right, int goal)
    {

        while (right - left > 1) {

            int mid = left + (right - left) / 2;
            if (nums[tail[mid]] >= goal)
                right = mid;
            else
                left = mid;
        }

        return right;
    }


    //optimal method using binary search and a series of "previous" indexes"
    //https://stackoverflow.com/questions/2631726/how-to-determine-the-longest-increasing-subsequence-using-dynamic-programming
    //comprehensive python solution to look over
    static int longest_increasing_subsequence(int nums[], int n)
    {
        int N = nums.length;

        // Add boundary case, when array n is zero
        // Depend on smart pointers

        int tailIndices[] = new int[n];

        // Initialized with 0
        Arrays.fill(tailIndices, 0);

        int prevIndices[] = new int[n];

        // initialized with -1
        Arrays.fill(prevIndices, -1);

        // it will always point to empty
        // location
        int len = 1;

        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[tailIndices[0]])

                // new smallest value
                tailIndices[0] = i;

            else if (nums[i] > nums[tailIndices[len - 1]]) {

                // arr[i] wants to extend
                // largest subsequence
                prevIndices[i] = tailIndices[len - 1];
                tailIndices[len++] = i;
            }
            else {

                // arr[i] wants to be a potential
                // condidate of future subsequence
                // It will replace ceil value in
                // tailIndices
                // perform a binary search from
                int left = -1;
                int right = len - 1;
                //int pos = GetCeilIndex(nums, tailIndices, -1, len - 1, nums[i]);
                while (right - left > 1) {
                    int mid = left + (right - left) / 2;
                    if (nums[tailIndices[mid]] >= nums[i])
                        right = mid;
                    else
                        left = mid;
                }
                prevIndices[i] = tailIndices[right - 1];
                tailIndices[right] = i;
            }
        }

//        System.out.println("LIS of given input");

        for (int i = tailIndices[len - 1]; i >= 0;
             i = prevIndices[i])
            System.out.print(nums[i] + " ");

//        System.out.println();

        return len;
    }


    //non-optimal method, still cannot pass runtime
    public ArrayList<Integer> get_lis(int[] nums){
        ArrayList<Integer>[] subsequences = new ArrayList[nums.length];
        Arrays.fill(subsequences, new ArrayList<>());
        subsequences[0].add(0);
        for(int i = 1; i < nums.length; i++){
            subsequences[i] = new ArrayList<>();
            for(int j = 0; j < i; j++){
                if(nums[j] < nums[i] && subsequences[j].size() + 1 > subsequences[i].size()){
                    subsequences[i] = new ArrayList<Integer>();
                    for(int tmp : subsequences[j]){
                        subsequences[i].add(tmp);
                    }
//                 out.println(subsequences[i]);
                }
            }
            subsequences[i].add(i);
            out.println(subsequences[i]);
        }


        int max = Integer.MIN_VALUE;
        ArrayList<Integer> temp = new ArrayList<Integer>();
        for(ArrayList<Integer> subsequence : subsequences){
            if(subsequence.size() > max){
                max = subsequence.size();
                temp = subsequence;
            }
        }
        return temp;
    }
	public void run() throws Exception{
	
	    Scanner f = new Scanner(new File("print_lis.dat"));
	    //Scanner f = new Scanner(System.in);
        
        while(f.hasNext()){
            int N = f.nextInt();
            int[] nums = new int[N];
            for(int i = 0; i < N; i++){
                nums[i] = f.nextInt();
            }
            ArrayList<Integer> sub = get_lis(nums);
            out.println(sub.size());
            String s = "";
            for(int i : sub){
                s += i + " ";
            }
            out.println(s.trim());
        }
        
        f.close();
        
	}
	public static void main(String[] args) throws Exception{
	
	new print_lis().run();
	
	}
}