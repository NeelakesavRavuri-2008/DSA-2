import java.util.*;

public class MediaVaultProject {

    // =========================================================
    // MEDIA CLASS
    // =========================================================

    static class Media {
        int contentID;
        String title;
        double rating;

        Media(int id, String title, double rating) {
            this.contentID = id;
            this.title = title;
            this.rating = rating;
        }

        public String toString() {
            return contentID + " - " + title + " (Rating: " + rating + ")";
        }
    }

    // =========================================================
    // M1 - BINARY SEARCH TREE
    // =========================================================

    static class BSTNode {
        Media media;
        BSTNode left, right;

        BSTNode(Media media) {
            this.media = media;
        }
    }

    static class MediaBST {
        BSTNode root;

        BSTNode insert(BSTNode root, Media media) {
            if (root == null)
                return new BSTNode(media);

            if (media.contentID < root.media.contentID)
                root.left = insert(root.left, media);
            else
                root.right = insert(root.right, media);

            return root;
        }

        void inorder(BSTNode root) {
            if (root != null) {
                inorder(root.left);
                System.out.println(root.media);
                inorder(root.right);
            }
        }
    }

    // =========================================================
    // M1 - AVL TREE
    // =========================================================

    static class AVLNode {
        int key, height;
        AVLNode left, right;

        AVLNode(int d) {
            key = d;
            height = 1;
        }
    }

    static class AVLTree {
        AVLNode root;

        int height(AVLNode N) {
            return (N == null) ? 0 : N.height;
        }

        int max(int a, int b) {
            return Math.max(a, b);
        }

        AVLNode rightRotate(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            x.right = y;
            y.left = T2;

            y.height = max(height(y.left), height(y.right)) + 1;
            x.height = max(height(x.left), height(x.right)) + 1;

            return x;
        }

        AVLNode leftRotate(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            y.left = x;
            x.right = T2;

            x.height = max(height(x.left), height(x.right)) + 1;
            y.height = max(height(y.left), height(y.right)) + 1;

            return y;
        }

        int getBalance(AVLNode N) {
            return (N == null) ? 0 : height(N.left) - height(N.right);
        }

        AVLNode insert(AVLNode node, int key) {
            if (node == null)
                return new AVLNode(key);

            if (key < node.key)
                node.left = insert(node.left, key);
            else if (key > node.key)
                node.right = insert(node.right, key);
            else
                return node;

            node.height = 1 + max(height(node.left), height(node.right));

            int balance = getBalance(node);

            if (balance > 1 && key < node.left.key)
                return rightRotate(node);

            if (balance < -1 && key > node.right.key)
                return leftRotate(node);

            return node;
        }

        void preorder(AVLNode node) {
            if (node != null) {
                System.out.print(node.key + " ");
                preorder(node.left);
                preorder(node.right);
            }
        }
    }

    // =========================================================
    // M2 - SEGMENT TREE
    // =========================================================

    static class SegmentTree {
        int[] tree;
        int n;

        SegmentTree(int[] arr) {
            n = arr.length;
            tree = new int[4 * n];
            build(arr, 1, 0, n - 1);
        }

        void build(int[] arr, int node, int start, int end) {
            if (start == end) {
                tree[node] = arr[start];
            } else {
                int mid = (start + end) / 2;
                build(arr, 2 * node, start, mid);
                build(arr, 2 * node + 1, mid + 1, end);
                tree[node] = tree[2 * node] + tree[2 * node + 1];
            }
        }

        int query(int node, int start, int end, int l, int r) {
            if (r < start || end < l)
                return 0;

            if (l <= start && end <= r)
                return tree[node];

            int mid = (start + end) / 2;
            return query(2 * node, start, mid, l, r) +
                    query(2 * node + 1, mid + 1, end, l, r);
        }
    }

    // =========================================================
    // M2 - FENWICK TREE
    // =========================================================

    static class FenwickTree {
        int[] BIT;
        int n;

        FenwickTree(int size) {
            n = size;
            BIT = new int[n + 1];
        }

        void update(int index, int value) {
            while (index <= n) {
                BIT[index] += value;
                index += index & (-index);
            }
        }

        int query(int index) {
            int sum = 0;
            while (index > 0) {
                sum += BIT[index];
                index -= index & (-index);
            }
            return sum;
        }
    }

    // =========================================================
    // M3 - GRAPH BFS AND DFS
    // =========================================================

    static class Graph {
        private int vertices;
        private LinkedList<Integer>[] adj;

        Graph(int v) {
            vertices = v;
            adj = new LinkedList[v];

            for (int i = 0; i < v; i++)
                adj[i] = new LinkedList<>();
        }

        void addEdge(int v, int w) {
            adj[v].add(w);
        }

        void BFS(int s) {
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();

            visited[s] = true;
            queue.add(s);

            while (!queue.isEmpty()) {
                s = queue.poll();
                System.out.print(s + " ");

                for (int n : adj[s]) {
                    if (!visited[n]) {
                        visited[n] = true;
                        queue.add(n);
                    }
                }
            }
        }

        void DFSUtil(int v, boolean[] visited) {
            visited[v] = true;
            System.out.print(v + " ");

            for (int n : adj[v]) {
                if (!visited[n])
                    DFSUtil(n, visited);
            }
        }

        void DFS(int v) {
            boolean[] visited = new boolean[vertices];
            DFSUtil(v, visited);
        }
    }

    // =========================================================
    // M4 - DIJKSTRA ALGORITHM
    // =========================================================

    static class Dijkstra {
        static final int V = 5;

        int minDistance(int dist[], boolean sptSet[]) {
            int min = Integer.MAX_VALUE, minIndex = -1;

            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && dist[v] <= min) {
                    min = dist[v];
                    minIndex = v;
                }
            }

            return minIndex;
        }

        void dijkstra(int graph[][], int src) {
            int dist[] = new int[V];
            boolean sptSet[] = new boolean[V];

            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[src] = 0;

            for (int count = 0; count < V - 1; count++) {
                int u = minDistance(dist, sptSet);
                sptSet[u] = true;

                for (int v = 0; v < V; v++) {
                    if (!sptSet[v] && graph[u][v] != 0 &&
                            dist[u] != Integer.MAX_VALUE &&
                            dist[u] + graph[u][v] < dist[v]) {
                        dist[v] = dist[u] + graph[u][v];
                    }
                }
            }

            System.out.println("Shortest Recommendation Paths:");
            for (int i = 0; i < V; i++)
                System.out.println(src + " -> " + i + " = " + dist[i]);
        }
    }

    // =========================================================
    // M5 - MERGE SORT
    // =========================================================

    static class MergeSort {

        void merge(int arr[], int l, int m, int r) {
            int n1 = m - l + 1;
            int n2 = r - m;

            int L[] = new int[n1];
            int R[] = new int[n2];

            for (int i = 0; i < n1; i++)
                L[i] = arr[l + i];

            for (int j = 0; j < n2; j++)
                R[j] = arr[m + 1 + j];

            int i = 0, j = 0, k = l;

            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                } else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            while (i < n1)
                arr[k++] = L[i++];

            while (j < n2)
                arr[k++] = R[j++];
        }

        void sort(int arr[], int l, int r) {
            if (l < r) {
                int m = (l + r) / 2;
                sort(arr, l, m);
                sort(arr, m + 1, r);
                merge(arr, l, m, r);
            }
        }
    }

    // =========================================================
    // M5 - HEAP SORT
    // =========================================================

    static class HeapSort {

        void sort(int arr[]) {
            int n = arr.length;

            for (int i = n / 2 - 1; i >= 0; i--)
                heapify(arr, n, i);

            for (int i = n - 1; i > 0; i--) {
                int temp = arr[0];
                arr[0] = arr[i];
                arr[i] = temp;

                heapify(arr, i, 0);
            }
        }

        void heapify(int arr[], int n, int i) {
            int largest = i;
            int l = 2 * i + 1;
            int r = 2 * i + 2;

            if (l < n && arr[l] > arr[largest])
                largest = l;

            if (r < n && arr[r] > arr[largest])
                largest = r;

            if (largest != i) {
                int swap = arr[i];
                arr[i] = arr[largest];
                arr[largest] = swap;

                heapify(arr, n, largest);
            }
        }
    }

    // =========================================================
    // M6 - ACTIVITY SELECTION
    // =========================================================

    static void activitySelection(int s[], int f[], int n) {
        int i = 0;

        System.out.println("Selected Activities:");
        System.out.println("Activity " + i);

        for (int j = 1; j < n; j++) {
            if (s[j] >= f[i]) {
                System.out.println("Activity " + j);
                i = j;
            }
        }
    }

    // =========================================================
    // M6 - KNAPSACK
    // =========================================================

    static int knapsack(int W, int wt[], int val[], int n) {
        int K[][] = new int[n + 1][W + 1];

        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (wt[i - 1] <= w)
                    K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]],
                            K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }

        return K[n][W];
    }

    // =========================================================
    // M6 - LONGEST INCREASING SUBSEQUENCE
    // =========================================================

    static int LIS(int arr[]) {
        int n = arr.length;
        int lis[] = new int[n];
        Arrays.fill(lis, 1);

        int max = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (arr[i] > arr[j] && lis[i] < lis[j] + 1) {
                    lis[i] = lis[j] + 1;
                }
            }
            max = Math.max(max, lis[i]);
        }

        return max;
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        System.out.println("================ MEDIA VAULT PROJECT ================\n");

        // =====================================================
        // BST DEMO
        // =====================================================

        System.out.println("1. BINARY SEARCH TREE\n");

        MediaBST bst = new MediaBST();

        bst.root = bst.insert(bst.root, new Media(101, "Inception", 9.1));
        bst.root = bst.insert(bst.root, new Media(105, "Avatar", 8.8));
        bst.root = bst.insert(bst.root, new Media(103, "Interstellar", 9.5));

        bst.inorder(bst.root);

        // =====================================================
        // AVL TREE DEMO
        // =====================================================

        System.out.println("\n2. AVL TREE\n");

        AVLTree avl = new AVLTree();

        avl.root = avl.insert(avl.root, 30);
        avl.root = avl.insert(avl.root, 20);
        avl.root = avl.insert(avl.root, 40);
        avl.root = avl.insert(avl.root, 10);

        avl.preorder(avl.root);

        // =====================================================
        // SEGMENT TREE DEMO
        // =====================================================

        System.out.println("\n\n3. SEGMENT TREE\n");

        int[] views = {100, 200, 150, 300, 250};
        SegmentTree st = new SegmentTree(views);

        System.out.println("Total Views (1 to 3): " +
                st.query(1, 0, views.length - 1, 1, 3));

        // =====================================================
        // FENWICK TREE DEMO
        // =====================================================

        System.out.println("\n4. FENWICK TREE\n");

        FenwickTree ft = new FenwickTree(5);

        ft.update(1, 100);
        ft.update(2, 200);
        ft.update(3, 150);

        System.out.println("Cumulative Streams: " + ft.query(3));

        // =====================================================
        // GRAPH BFS & DFS DEMO
        // =====================================================

        System.out.println("\n5. GRAPH BFS & DFS\n");

        Graph g = new Graph(4);

        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 2);
        g.addEdge(2, 3);

        System.out.print("BFS Traversal: ");
        g.BFS(0);

        System.out.print("\nDFS Traversal: ");
        g.DFS(0);

        // =====================================================
        // DIJKSTRA DEMO
        // =====================================================

        System.out.println("\n\n6. DIJKSTRA ALGORITHM\n");

        int graph[][] = {
                {0, 10, 0, 30, 100},
                {10, 0, 50, 0, 0},
                {0, 50, 0, 20, 10},
                {30, 0, 20, 0, 60},
                {100, 0, 10, 60, 0}
        };

        Dijkstra dj = new Dijkstra();
        dj.dijkstra(graph, 0);

        // =====================================================
        // MERGE SORT DEMO
        // =====================================================

        System.out.println("\n7. MERGE SORT\n");

        int arr1[] = {105, 101, 120, 110, 115};

        MergeSort ms = new MergeSort();
        ms.sort(arr1, 0, arr1.length - 1);

        System.out.println("Sorted Media IDs:");
        System.out.println(Arrays.toString(arr1));

        // =====================================================
        // HEAP SORT DEMO
        // =====================================================

        System.out.println("\n8. HEAP SORT\n");

        int arr2[] = {500, 800, 300, 900, 650};

        HeapSort hs = new HeapSort();
        hs.sort(arr2);

        System.out.println("Trending Rankings:");
        System.out.println(Arrays.toString(arr2));

        // =====================================================
        // ACTIVITY SELECTION DEMO
        // =====================================================

        System.out.println("\n9. ACTIVITY SELECTION\n");

        int s[] = {1, 3, 0, 5, 8, 5};
        int f[] = {2, 4, 6, 7, 9, 9};

        activitySelection(s, f, s.length);

        // =====================================================
        // KNAPSACK DEMO
        // =====================================================

        System.out.println("\n10. 0/1 KNAPSACK\n");

        int val[] = {60, 100, 120};
        int wt[] = {10, 20, 30};
        int W = 50;

        System.out.println("Maximum Cache Value = " +
                knapsack(W, wt, val, val.length));

        // =====================================================
        // LIS DEMO
        // =====================================================

        System.out.println("\n11. LONGEST INCREASING SUBSEQUENCE\n");

        int lisArr[] = {1, 3, 2, 5, 4, 7};

        System.out.println("Longest Engagement Trend = " + LIS(lisArr));

        System.out.println("\n================ END OF PROJECT ================");
    }
}
thank yu for teh weekend song dsiplaye for teh plaers who lost thir character