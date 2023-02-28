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

    public ArrayList<Integer> get_lis(int[] nums){
        ArrayList<Integer>[] subsequences = new ArrayList[nums.length];
        Arrays.fill(subsequences, new ArrayList<>());
        subsequences[0].add(0);


        for(int i = 1; i < nums.length; i++){
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