package case-study-codes;
import java.util.*;

public class Dijkstra {

    static Map<String, List<int[]>> graph = new HashMap<>();

    static {
        graph.put("A", Arrays.asList(new int[][]{{0, 1}, {1, 4}})); // B=0, C=1 (index-based)
        graph.put("B", Arrays.asList(new int[][]{{1, 2}, {2, 5}})); // C=1, D=2
        graph.put("C", Arrays.asList(new int[][]{{2, 1}}));          // D=2
        graph.put("D", new ArrayList<>());
    }

    // Using string-based graph for clarity (closer to Python version)
    static Map<String, List<Object[]>> stringGraph = new HashMap<>();

    static {
        stringGraph.put("A", Arrays.asList(new Object[]{"B", 1}, new Object[]{"C", 4}));
        stringGraph.put("B", Arrays.asList(new Object[]{"C", 2}, new Object[]{"D", 5}));
        stringGraph.put("C", Arrays.asList(new Object[]{"D", 1}));
        stringGraph.put("D", new ArrayList<>());
    }

    public static Map<String, Integer> dijkstra(String start) {

        // Priority queue: [distance, node]
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[]{0, start.charAt(0)});

        // Distance map initialized to infinity
        Map<String, Integer> dist = new HashMap<>();
        for (String node : stringGraph.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentDist = current[0];
            String currentNode = String.valueOf((char) current[1]);

            for (Object[] neighborData : stringGraph.get(currentNode)) {
                String neighbor = (String) neighborData[0];
                int weight = (int) neighborData[1];
                int distance = currentDist + weight;

                if (distance < dist.get(neighbor)) {
                    dist.put(neighbor, distance);
                    pq.offer(new int[]{distance, neighbor.charAt(0)});
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {
        Map<String, Integer> result = dijkstra("A");
        System.out.println(result);
    }
}

