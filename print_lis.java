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
    static int get_ceil_index(int[] nums, int[] tail, int left, int right, int goal) {
        //ensures that right is > left
        while (right - left > 1) {
            //find midpoint
            int mid = left + (right - left) / 2;
            //if the number in the sequence pointed to by mid is greater than or equal to the target number that
            //we are searching for, set the right boundary to mid
            if (nums[tail[mid]] >= goal)
                right = mid;
                //otherwise, set the left boundary equal to the midpoint
            else
                left = mid;
        }
        return right;
    }

    //optimal method using binary search and a series of "previous" indexes
    //https://stackoverflow.com/questions/2631726/how-to-determine-the-longest-increasing-subsequence-using-dynamic-programming
    //comprehensive python solution to look over
    public void longest_increasing_subsequence(int[] nums)
    {
        int n = nums.length;
        //tail index will be used to find the ending point of the longest increasing subsequence.
        //the value at the end of the tail index array will always be the INDEX OF the largest number in the
        //sequence, and the value at the beginning will always be the INDEX OF the smallest number in the sequence
        //tail indices will always be sorted because of the way the algorithm adds elements to it
        int[] tail_index = new int[n];
        Arrays.fill(tail_index, 0);
        //prev index is used to keep track of our "active" longest increasing subsequence.
        int[] prev_index = new int[n];
        Arrays.fill(prev_index, -1);

        int len = 1;

        for (int i = 1; i < n; i++) {
            //if the current element is smaller than the element pointed to by the beginning of
            //tail index, change the beginning of tail index to point towards the index of the new
            //smallest element
            if (nums[i] < nums[tail_index[0]])
                tail_index[0] = i;
                //if the current element is greater than the previous maximum, we attempt to add the element
                // to the current longest increasing subsequence
            else if (nums[i] > nums[tail_index[len - 1]]) {
                prev_index[i] = tail_index[len - 1];
                tail_index[len++] = i;
            }
            //if the element is neither a maximum or minimum, it still has
            // potential to be chosen as part of the longest increasing subsequence.
            //We have it replace the current ceiling value in tail index.
            //The ceiling value is the next value in the sequence that is larger than or equal to the current value.
            //To find the ceiling value, we perform a binary search to find its index within the tail_index array
            //A binary search can be done because tail_index is always sorted and we must have O(nlogn) time complexity
            //in order to avoid exceeding the time limit
            else {
                int pos = get_ceil_index(nums, tail_index, -1, len - 1, nums[i]);
                if(pos != 0){
                    prev_index[i] = tail_index[pos - 1];
                    tail_index[pos] = i;
                }
            }
        }
        //output the size and indices of the longest increasing subsequence.
        System.out.println(len);
        Stack<Integer> s = new Stack<>();
        for (int i = tail_index[len - 1]; i >= 0; i = prev_index[i])
            s.push(i);
        while(!s.isEmpty()){
            out.print(s.pop() + " ");
        }
        System.out.println();
    }
    public void run() throws Exception{

//        Scanner f = new Scanner(new File("lis.dat"));
        Scanner f = new Scanner(System.in);

        while(f.hasNext()){
            int N = f.nextInt();
            int[] nums = new int[N];
            for(int i = 0; i < N; i++){
                nums[i] = f.nextInt();
            }
            longest_increasing_subsequence(nums);
        }

        f.close();

    }
    public static void main(String[] args) throws Exception{

        new print_lis().run();

    }
}