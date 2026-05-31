package case-study-codes;
public class Prims {

    static final int V = 5;

    static int[][] graph = {
        {0, 2, 0, 6, 0},
        {2, 0, 3, 8, 5},
        {0, 3, 0, 0, 7},
        {6, 8, 0, 0, 9},
        {0, 5, 7, 9, 0}
    };

    public static void main(String[] args) {

        boolean[] selected = new boolean[V];
        selected[0] = true;

        System.out.println("Edge : Weight");

        for (int count = 0; count < V - 1; count++) {
            int minimum = Integer.MAX_VALUE;
            int x = 0, y = 0;

            for (int i = 0; i < V; i++) {
                if (selected[i]) {
                    for (int j = 0; j < V; j++) {
                        if (!selected[j] && graph[i][j] != 0) {
                            if (minimum > graph[i][j]) {
                                minimum = graph[i][j];
                                x = i;
                                y = j;
                            }
                        }
                    }
                }
            }

            System.out.println(x + " - " + y + " : " + graph[x][y]);
            selected[y] = true;
        }
    }
}

