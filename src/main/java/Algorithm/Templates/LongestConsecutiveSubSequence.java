package Algorithm.Templates;

import Utility.MetricsLogger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSubSequence {
    public static void main(String[] args) {
//        int[] input = new int[]{100, 4, 200, 1, 3, 2};
        // Expected O/P = 4, because length of seq = 4 (1, 2, 3, 4)

//        int[] input = new int[]{1, 2, 3, 4, 7, 8, 9, 0};
        int[] input = new int[]{1, 2, 3, 4, 7, 8, 9, 0, 12, 14, 16, 17, 19, 20, 22, 11, 13, 26, 30, 44, 65, 50, 5, 3, 43};


        System.out.println(longestConsecutiveSubSequence(input));
        System.out.println(longestConsecutiveSubSequenceHashMap(input));

        MetricsLogger.measure("Sorting: ", () -> longestConsecutiveSubSequence(input));
        MetricsLogger.measure("HashSet: ", () -> longestConsecutiveSubSequenceHashMap(input));
    }

    static int longestConsecutiveSubSequence(int[] arr) {

        if (arr.length == 0) return 0;

        Arrays.sort(arr);
        int longest = 1;
        int currentLen = 1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                // skip duplicates
                continue;
            }
            if (arr[i] == arr[i - 1] + 1) {
                currentLen++;
            } else {
                longest = Math.max(longest, currentLen);
                currentLen = 1;
            }
        }
        return Math.max(longest, currentLen);
    }

    static int longestConsecutiveSubSequenceHashMap(int[] arr) {

        Set<Integer> set = new HashSet<>();
        for (int n : arr) set.add(n);

        int longest = 0;

        for (int s : set) {
            if (!set.contains(s-1)) {
                int curr = s;
                int streak = 1;

                while (set.contains(curr + 1)) {
                    curr++;
                    streak++;
                }

                longest = Math.max(longest, streak);
            }
        }
        return longest;
    }
}

/*
   There's no start or end in hash map/set, they either have the value or not. So this is working on that part.
   Therefore we're working with s-1 to find if there exist a start of this sequence.

   ex: 1, 2, 3, 4, 7, 8, 9, 0

   Here, we put all the number to the set, and set has the property that it will contain distinct number,
   it doesn't maintain order so don't think the numbers are inserted like 1, 2, 3, they might be 3, 2, 0 ... so on
   therefore we can't make an algorithm based on the position of elements unlike arrays.

   so, we're only left with two options we can use some expression with contains(), to find the start of a sequence,
   like we can keep on checking num - 1 to see if the previous number exists in the set, and if does then check num -1-1
   again. Like this we can keep in checking till we find the first in the sequence either this sequence or another.
   Then we start calculating the longest and streak, and once that sequence ends then we go to the next in set and do
   the same.

   Ex: 1, 2, 3, 4, 4, 7, 8, 9, 0

   Now we know set * Distinct Elements, No order of insertion
   Therefore set = {2, 0, 7, 8, 1, 3, 9, 4}

   Now, s = 2, has a prev? 2 - 1 = 1? in set? Yes! Therefore we move to next elem that is 0
   Now, s = 0, has a prev? 0 - 1 = -1? NO! There fore we got our sequence start, now we keep finding 0 + 1 and update
   streak.

   I.e 0, streak = 1; 1, streak = 2; 2, streak = 3; 3, streak=4; 4, streak=5, not 6? Not in Set, therefore we update
   longest with 5 and now onto next s that is 7

   s = 7, 7-1 = 6? No! new sequence, 7 + 1 = 8 in set? yes, streak = 2, s = 8;
   8 + 1 = 9 in set? Yes! streak 3
   9 + 1 = 10 in set? No! streak 3, longest 5,

   now max(longes, streak) (5, 3) i.e 5

   Therefore we get the answer as 5. We don't depend on the iteration order we depend of the membership test.

*/
