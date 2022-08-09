import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.io.IOException;

/* A mutable and finite Graph object. Edge labels are stored via a HashMap
   where labels are mapped to a key calculated by the following. The graph is
   undirected (whenever an Edge is added, the dual Edge is also added). Vertices
   are numbered starting from 0. */
public class Graph {

    /* Maps vertices to a list of its neighboring vertices. */
    private HashMap<Integer, Set<Integer>> neighbors = new HashMap<>();
    /* Maps vertices to a list of its connected edges. */
    private HashMap<Integer, Set<Edge>> edges = new HashMap<>();
    /* A sorted set of all edges. */
    private TreeSet<Edge> allEdges = new TreeSet<>();

    /* Returns the vertices that neighbor V. */
    public TreeSet<Integer> getNeighbors(int v) {
        return new TreeSet<Integer>(neighbors.get(v));
    }

    /* Returns all edges adjacent to V. */
    public TreeSet<Edge> getEdges(int v) {
        return new TreeSet<Edge>(edges.get(v));
    }

    /* Returns a sorted list of all vertices. */
    public TreeSet<Integer> getAllVertices() {
        return new TreeSet<Integer>(neighbors.keySet());
    }

    /* Returns a sorted list of all edges. */
    public TreeSet<Edge> getAllEdges() {
        return new TreeSet<Edge>(allEdges);
    }

    /* Adds vertex V to the graph. */
    public void addVertex(Integer v) {
        if (neighbors.get(v) == null) {
            neighbors.put(v, new HashSet<Integer>());
            edges.put(v, new HashSet<Edge>());
        }
    }

    /* Adds Edge E to the graph. */
    public void addEdge(Edge e) {
        addEdgeHelper(e.getSource(), e.getDest(), e.getWeight());
    }

    /* Creates an Edge between V1 and V2 with no weight. */
    public void addEdge(int v1, int v2) {
        addEdgeHelper(v1, v2, 0);
    }

    /* Creates an Edge between V1 and V2 with weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        addEdgeHelper(v1, v2, weight);
    }

    /* Returns true if V1 and V2 are connected by an edge. */
    public boolean isNeighbor(int v1, int v2) {
        return neighbors.get(v1).contains(v2) && neighbors.get(v2).contains(v1);
    }

    /* Returns true if the graph contains V as a vertex. */
    public boolean containsVertex(int v) {
        return neighbors.get(v) != null;
    }

    /* Returns true if the graph contains the edge E. */
    public boolean containsEdge(Edge e) {
        return allEdges.contains(e);
    }

    /* Returns if this graph spans G. */
    public boolean spans(Graph g) {
        TreeSet<Integer> all = getAllVertices();
        if (all.size() != g.getAllVertices().size()) {
            return false;
        }
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> vertices = new ArrayDeque<>();
        Integer curr;

        vertices.add(all.first());
        while ((curr = vertices.poll()) != null) {
            if (!visited.contains(curr)) {
                visited.add(curr);
                for (int n : getNeighbors(curr)) {
                    vertices.add(n);
                }
            }
        }
        return visited.size() == g.getAllVertices().size();
    }

    /* Overrides objects equals method. */
    public boolean equals(Object o) {
        if (!(o instanceof Graph)) {
            return false;
        }
        Graph other = (Graph) o;
        return neighbors.equals(other.neighbors) && edges.equals(other.edges);
    }

    /* A helper function that adds a new edge from V1 to V2 with WEIGHT as the
       label. */
    private void addEdgeHelper(int v1, int v2, int weight) {
        addVertex(v1);
        addVertex(v2);

        neighbors.get(v1).add(v2);
        neighbors.get(v2).add(v1);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        edges.get(v1).add(e1);
        edges.get(v2).add(e2);
        allEdges.add(e1);
    }

    public Graph prims(int start) {
        // TODO: YOUR CODE HERE
        int n = 0;
        PriorityQueue<Edge> fringe = new PriorityQueue<>();
        fringe.addAll(getEdges(start));
        TreeSet<Edge> target = new TreeSet<>();
        TreeSet<Integer> visited = new TreeSet<>();
        visited.add(start);


        while(true){
            Edge curr = fringe.poll();
            if(curr == null){ // failure case when there is only one vetrix
                break;
            }
            if(!visited.contains(curr.getDest())){
                target.add(curr); // add to the target
            }
            visited.add(curr.getDest()); // add to visit point
            n ++;
            int nextV = curr.getDest();
            for(Edge e : getEdges(nextV)){ // add the next processing edges
                if(!target.contains(e) && !visited.contains(e.getDest())){
                    fringe.add(e);
                }
            }

        }
        // create the graph
        Graph x = new Graph();
        for (int i: getAllVertices()){
            x.addVertex(i);
        }
        for (Edge e: target){
            x.addEdge(e);
        }
        return x;
    }

    public Graph kruskals() {
        // TODO: YOUR CODE HERE
        int n = 0;
        TreeSet<Edge> sortedFringe = getAllEdges();
        // TreeSet<Integer> visited = new TreeSet<>();
        TreeSet<Edge> target = new TreeSet<>();
        UnionFind connectedDisjointSet = new UnionFind(getAllVertices().size());

        if(getAllVertices().size() == 1){ // failure case
            return this;
        }

        for(Edge e: sortedFringe){
            if(!connectedDisjointSet.connected(e.getSource(), e.getDest())){
                connectedDisjointSet.union(e.getSource(), e.getDest());
                target.add(e);
            }
        }

        // create the graph
        Graph x = new Graph();
        for (int i: getAllVertices()){
            x.addVertex(i);
        }
        for (Edge e: target){
            x.addEdge(e);
        }
        return x;
    }

    /* Returns a randomly generated graph with VERTICES number of vertices and
       EDGES number of edges with max weight WEIGHT. */
    public static Graph randomGraph(int vertices, int edges, int weight) {
        Graph g = new Graph();
        Random rng = new Random();
        for (int i = 0; i < vertices; i += 1) {
            g.addVertex(i);
        }
        for (int i = 0; i < edges; i += 1) {
            Edge e = new Edge(rng.nextInt(vertices), rng.nextInt(vertices), rng.nextInt(weight));
            g.addEdge(e);
        }
        return g;
    }

    /* Returns a Graph object with integer edge weights as parsed from
       FILENAME. Talk about the setup of this file. */
    public static Graph loadFromText(String filename) {
        Charset cs = Charset.forName("US-ASCII");
        try (BufferedReader r = Files.newBufferedReader(Paths.get(filename), cs)) {
            Graph g = new Graph();
            String line;
            while ((line = r.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields.length == 3) {
                    int from = Integer.parseInt(fields[0]);
                    int to = Integer.parseInt(fields[1]);
                    int weight = Integer.parseInt(fields[2]);
                    g.addEdge(from, to, weight);
                } else if (fields.length == 1) {
                    g.addVertex(Integer.parseInt(fields[0]));
                } else {
                    throw new IllegalArgumentException("Bad input file!");
                }
            }
            return g;
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    public static class UnionFind {

        /* TODO: Add instance variables here. */
        ArrayList<Integer> arr = new ArrayList<>();

        /* Creates a UnionFind data structure holding N items. Initially, all
           items are in disjoint sets. */
        public UnionFind(int N) {
            // TODO: YOUR CODE HERE
            for(int i = 0; i < N; i ++){
                arr.add(-1);
            }
        }

        /* Returns the size of the set V belongs to. */
        public int sizeOf(int v) {
            // TODO: YOUR CODE HERE
            if (arr.get(v) < 0){
                return Math.abs(arr.get(v)); // base case
            } else {
                return sizeOf(arr.get(v)); // recursive call
            }
        }

        /* Returns the parent of V. If V is the root of a tree, returns the
           negative size of the tree for which V is the root. */
        public int parent(int v) {
            // TODO: YOUR CODE HERE
            if(arr.get(v) < 0){
                return -sizeOf(v); // return the negative size of v if < 0
            } else {
                return arr.get(v); // return
            }
        }

        /* Returns true if nodes V1 and V2 are connected. */
        public boolean connected(int v1, int v2) {
            // TODO: YOUR CODE HERE
            return find(v1) == find(v2);
        }



        /* Returns the root of the set V belongs to. Path-compression is employed
           allowing for fast search-time. If invalid items are passed into this
           function, throw an IllegalArgumentException. */
        public int find(int v) {
            // TODO: YOUR CODE HERE
            if(v >= arr.size()){
                throw new IllegalArgumentException();
            }

            if(arr.get(v) < 0){
                return v;
            } else {
                if(arr.get(arr.get(v)) < 0){
                    return arr.get(v);
                }
                arr.set(v, arr.get(arr.get(v)));
                return find(arr.get(v));
            }
        }

        /* Connects two items V1 and V2 together by connecting their respective
           sets. V1 and V2 can be any element, and a union-by-size heuristic is
           used. If the sizes of the sets are equal, tie break by connecting V1's
           root to V2's root. Union-ing a item with itself or items that are
           already connected should not change the structure. */
        public void union(int v1, int v2) {
            // TODO: YOUR CODE HERE
            int r1 = find(v1);
            int r2 = find(v2);
            int s1 = sizeOf(v1);
            int s2 = sizeOf(v2);
            if(r1 == r2){
                return;
            }
            if(s1 > s2){
                arr.set(r1, arr.get(r1) + arr.get(r2));
                arr.set(r2, r1);
            } else{
                arr.set(r2, arr.get(r1) + arr.get(r2));
                arr.set(r1, r2);
            }
        }
    }
}
