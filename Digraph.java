import java.io.*;
import java.util.*;

public class Digraph<V> {

    public static class Edge<V>{
        private V vertex;
        private Float cost;

        public Edge(V v, Float c){
            vertex = v; cost = c;
        }

        public V getVertex() {
            return vertex;
        }

        public float getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "\n        " + vertex + " " + cost;
        }

    }

    /**
     * A Map is used to map each vertex to its list of adjacent vertices.
     */

    private Map<V, List<Edge<V>>> neighbors = new HashMap<V, List<Edge<V>>>();

    /**
     * String representation of graph.
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (V v : neighbors.keySet())
            s.append("\n    " + v + neighbors.get(v));
        return s.toString();
    }

    /**
     * Add a vertex to the graph. Nothing happens if vertex is already in graph.
     */
    public void add(String vertex) {
        if (neighbors.containsKey(vertex))
            return;
        neighbors.put((V) vertex, new ArrayList<Edge<V>>());
    }

    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void add(String from, String to, String cost) {
        this.add(from);
        this.add(to);
        neighbors.get(from).add(new Edge<V>((V) to, Float.valueOf(cost)));
    }

    public void remove(String from, String to){
        this.add(from);
        this.add(to);
        neighbors.get(from).remove(Edge<V>((V) to, (float) 0.0));
        neighbors.remove(to, from);
    }

    public static void main(String[] args) throws IOException {

        Digraph<String> graph = new Digraph<String>();

        String file1, content;
        String[] parts;
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the file name and location for the file?");//gets user input for input file name and location
        System.out.println("Ex. \\Desktop\\examples\\network.txt");
        file1 = scanner.nextLine();

        try {
            Scanner s = new Scanner(new File(file1));
            while (s.hasNext()) {
                content = s.nextLine();
                parts = content.split(" ");
                graph.add(parts[0]);
                graph.add(parts[1]);
                graph.add(parts[0], parts[1], parts[2]);
                graph.add(parts[1], parts[0], parts[2]);
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("The current graph: " + graph);

        graph.add("Duke", "Health", "1.45");
        System.out.println("The current graph: " + graph);


    }
}