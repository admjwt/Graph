import java.io.*;
import java.util.*;

public class Digraph<V> {

    public static class Edge<V>{
        private V vertex;
        private Float cost;
        private Float minDistance = Float.POSITIVE_INFINITY;
        private V prev;

        public Edge(V v, Float c){
            vertex = v; cost = c;
        }

        public V getVertex() {
            return vertex;
        }

        public Float getCost() {
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
        SortedSet<String> Vertex = new TreeSet<String>((Collection<? extends String>) neighbors.keySet());
        for (String v : Vertex)
            s.append("\n    " + v + neighbors.get(v));
        return s.toString();
    }

    /**
     * Add a vertex to the graph. Nothing happens if vertex is already in graph.
     */
    public void addVertex(String vertex) {
        if (neighbors.containsKey(vertex))
            return;
        neighbors.put((V) vertex, new ArrayList<Edge<V>>());
    }

    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void addEdge(String from, String to, String cost) {
        this.addVertex(from);
        this.addVertex(to);
        neighbors.get(from).add(new Edge<V>((V) to, Float.valueOf(cost)));
    }

    public void removeEdge(String from, String to) {
        System.out.format("Removing edge [%s,%s]\n", from, to);
        List<Edge<V>> edgeSet = neighbors.get(from);
        for (int i = 0; i < edgeSet.size(); i++) {
            Edge<V> edge = edgeSet.get(i);
            if (edge.getVertex().equals(to)) {
                edgeSet.remove(i);
            }
        }
    }

    public void computePaths(V start) {
        Float minDistance = Float.valueOf(0);
        PriorityQueue<V> vQ = new PriorityQueue<V>();
        vQ.add(start);

        V u = null;
        while (!vQ.isEmpty()) {
            u = vQ.poll();
        }
        List<Edge<V>> edgeSet = neighbors.get(u);
        for(int i = 0; i < edgeSet.size(); i++){
            Edge<V> v = edgeSet.get(i);
            Float weight = v.getCost();
            Float distThroughU = minDistance + weight;
            if(distThroughU < v.minDistance ){
                vQ.remove(v);
                v.minDistance = distThroughU;
                minDistance = distThroughU;
                v.prev = u;
                vQ.add((V) v);
            }
        }
    }

    public List<V> ShortestPath(V to){
        List<V> path = new ArrayList<V>();
        for(Edge<V> v = (Edge<V>) to; v != null; v = (Edge<V>) v.prev){
            path.add((V) v);
        }
        Collections.reverse(path);

        return path;
    }
    public static void main (String[] args)
    {
        try
        {
            Digraph obj = new Digraph ();
            obj.run (args);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }
    public void run(String[] args) throws IOException {

        Digraph<String> graph = new Digraph<String>();

        String file1, content;
        String[] parts;
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);

        System.out.println("What is the file name and location for the file?");//gets user input for input file name and location
        System.out.println("Ex. \\Desktop\\examples\\network.txt");
        file1 = scanner.nextLine();

        try {
            Scanner s = new Scanner(new File(file1));
            while (s.hasNext()) {
                content = s.nextLine();
                parts = content.split(" ");
                graph.addVertex(parts[0]);
                graph.addVertex(parts[1]);
                graph.addEdge(parts[0], parts[1], parts[2]);
                graph.addEdge(parts[1], parts[0], parts[2]);
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        while(true) {
            System.out.println("1. add Edge\n2.remove edge\n3.print Current Graph\n4.Reachable vertices\n5.shortest path\n6.Quit");
            System.out.print("Pick an option: ");
            String choice = scanner1.nextLine();
            if(choice.equalsIgnoreCase("quit") || choice.equalsIgnoreCase("6")){
                break;
            }
            if(choice.equalsIgnoreCase("print") || choice.equalsIgnoreCase("3") || choice.equalsIgnoreCase("print current Graph")){
                System.out.println("The current graph: " + graph);
            }
            if(choice.equalsIgnoreCase("add") || choice.equalsIgnoreCase("addEdge") || choice.equalsIgnoreCase("add Edge")|| choice.equalsIgnoreCase("1")){
                System.out.print("    Enter first vertex: ");
                String v1 = scanner1.nextLine();
                System.out.print("    Enter second vertex: ");
                String v2 = scanner1.nextLine();
                System.out.print("    Enter the weight: ");
                String w = scanner1.nextLine();
                graph.addEdge(v1, v2, w);
            }
            if(choice.equalsIgnoreCase("remove") || choice.equalsIgnoreCase("removeEdge") || choice.equalsIgnoreCase("remove edge") || choice.equalsIgnoreCase("2")){
                System.out.print("    Enter first vertex: ");
                String v1 = scanner1.nextLine();
                System.out.print("    Enter second vertex: ");
                String v2 = scanner1.nextLine();
                graph.removeEdge(v1, v2);
            }
            if(choice.equalsIgnoreCase("5") || choice.equalsIgnoreCase("shortest path") || choice.equalsIgnoreCase("path")){
                System.out.print("    Enter starting vertex: ");
                String v1 = scanner1.nextLine();
                System.out.print("    Enter ending vertex: ");
                String v2 = scanner1.nextLine();
                computePaths((V) v1);
                ShortestPath((V) v2);
            }
        }

    }
}