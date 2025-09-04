package Algorithm.MyWeirdAlgos;

import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        LCS longestCommonSubSequence = new LCS();
        longestCommonSubSequence.lcs();
    }
}

class LCS {
    public void lcs() {
        Scanner sc = new Scanner(System.in);

        int testCases = Integer.parseInt(sc.nextLine());

        while (testCases--> 0) {
            String s = sc.nextLine().trim();

            String[] ss = s.split("\\s+");

            char[] a = ss[0].toCharArray();
            char[] b = ss[1].toCharArray();

            StringBuilder sb = new StringBuilder();

            int i = 0;

            for (char x: a) {
                for (int j = i; j < b.length; j++) {
                    // System.out.println(x + " - " + b[j]);
                    if (x == b[j]) {
                        sb.append(b[j]);
                        i = j + 1;
                        break;
                    }

                }
            }

            System.out.println(sb.length());
        }

        sc.close();

    }
}

/* Test Cases and Output
 7
abcde ace
hello world
aa aa
xmjyauz mzjawxu
axyb abyxb
abdc acbd
aabb abab
axbyc abc

3
1
2
2
3
3
3
 */