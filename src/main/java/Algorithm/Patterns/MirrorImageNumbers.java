package Algorithm.Patterns;

public class MirrorImageNumbers {
    public static void main(String[] args) {
        with2ForLoop();
//        with1ForLoop();
    }

    static void with2ForLoop() {
        for (int i = 1; i <= 6; i++) {
            for (int j = 6; j >= 1; j--) {
                if (i == j) {
                    int k = i;
                    String s2 = "";
                    while (k >= 1) {
                        s2 += "" + k;
                        k--;
                    }
                    s2 += new StringBuilder(s2).reverse().toString();
                    System.out.print(s2);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }
    }

//    static void with1ForLoop() {
//        for (int i = 0, j = 5; i <= 3, j >= 0; i++, j--) {
//            System.out.println(i);
//        }
//    }
}
