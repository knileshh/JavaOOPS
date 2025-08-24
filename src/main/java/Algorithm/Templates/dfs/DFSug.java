package Algorithm.Templates.dfs;

public class DFSug {
    public static void main(String[] args) {
        int n = 5;
//        int[][] edges = {{0, 1}, {1, 2}, {3, 4}};





        int[][] edges = {{0,1}, {2,3}};
        int connection = 0, temp1 = 0, temp2 = 0;

        for (int i = 0; i < edges.length - 1; i++) {
            temp1 = edges[i][1];
            temp2 = edges[i+1][0];

            if (temp1 == temp2) {
                connection++;
            }
        }

        //Additionally to get correct answer we can do, if connectio == 0 then return edges.length, else  return connection + 1;

        System.out.println(connection);

    }
}
