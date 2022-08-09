import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Stack;
import java.util.HashSet;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. That is, adds an edge
       in ONE directions, from v1 to v2. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. That is, adds an edge
       in BOTH directions, from v1 to v2 and from v2 to v1. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        Edge e = new Edge(v1, v2, weight);
        if(this.adjLists[v1].contains(e)){
            this.adjLists[v1].remove(e);
        }
        this.adjLists[v1].add(e);
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        // TODO: YOUR CODE HERE
        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);
        if(this.adjLists[v1].contains(e1)){
            this.adjLists[v1].remove(e1);
        }

        if(this.adjLists[v2].contains(e2)){
            this.adjLists[v2].remove(e2);
        }

        this.adjLists[v1].add(e1);
        this.adjLists[v2].add(e2);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        // TODO: YOUR CODE HERE
        Edge e = new Edge(from, to, 0);
        return this.adjLists[from].contains(e);
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        // TODO: YOUR CODE HERE
        List<Integer> lst = new ArrayList<>();
        for(int i = 0; i < vertexCount; i ++){
            if(i != v){
                if((isAdjacent(v, i)) && !lst.contains(i)){
                    lst.add(i);
                }
            }
        }
        return lst;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        // TODO: YOUR CODE HERE
        int count = 0;
        for(int i = 0; i < vertexCount; i ++){
            if(i != v){
                if(isAdjacent(i, v)){
                    count ++;
                }
            }
        }
        return count;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /**
     *  A class that iterates through the vertices of this graph,
     *  starting with a given vertex. Does not necessarily iterate
     *  through all vertices in the graph: if the iteration starts
     *  at a vertex v, and there is no path from v to a vertex w,
     *  then the iteration will not include w.
     */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        public DFSIterator(Integer start) {
            fringe = new Stack<>();
            visited = new HashSet<>();
            fringe.push(start);
        }

        public boolean hasNext() {
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            int curr = fringe.pop();
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                lst.add(i);
            }
            lst.sort((Integer i1, Integer i2) -> -(i1 - i2));
            for (Integer e : lst) {
                fringe.push(e);
            }
            visited.add(curr);
            return curr;
        }

        //ignore this method
        public void remove() {
            throw new UnsupportedOperationException(
                    "vertex removal not implemented");
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    public List<Integer> dfs(int v, int stop) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);

        while (iter.hasNext()) {
            int i = iter.next();
            result.add(i);
            if(i == stop){
                break;
            }
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        // TODO: YOUR CODE

        if(start == stop){
            return true;
        }

        List<Integer> p = dfs(start, stop);

        ArrayList<Integer> rt = new ArrayList<>();
        rt.add(0,stop);
        for(int i = p.size() - 1, j = p.size() - 2; j >= 0;){
            if(isAdjacent(p.get(j), p.get(i))){
                rt.add(0,p.get(j));
                i = j ;
            }
            j --;
        }
        return rt.contains(start);
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {
        // TODO: YOUR CODE HERE
        ArrayList<Integer> rt = new ArrayList<>();
        if(start == stop){
            return new ArrayList<>(start);
        }

        if(!pathExists(start, stop)){
            return rt;
        }

        List<Integer> p = dfs(start, stop);
        rt.add(stop);
        for(int i = p.size() - 1, j = p.size() - 2; j >= 0;){
            if(isAdjacent(p.get(j), p.get(i))){
                rt.add(0,p.get(j));
                i = j ;
            }
            j --;
        }
        return rt;
    }

    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private ArrayList<Integer> currentInDegree = new ArrayList<>();
        private HashSet<Integer> visited;

        // TODO: Instance variables here!

        public TopologicalIterator() {
            fringe = new Stack<Integer>();
            visited = new HashSet<>();
            // TODO: YOUR CODE HERE
            for(int i = 0; i < vertexCount; i ++){
                currentInDegree.add(inDegree(i));
            }

            for(int i = 0; i < vertexCount; i ++){
                if(currentInDegree.get(i) == 0){
                    fringe.push(i);
                }
            }
        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE
            if (!fringe.isEmpty()) {
                int i = fringe.pop();
                while (visited.contains(i)) {
                    if (fringe.isEmpty()) {
                        return false;
                    }
                    i = fringe.pop();
                }
                fringe.push(i);
                return true;
            }
            return false;
        }

        public Integer next() {
            // TODO: YOUR CODE HERE
            int curr = fringe.pop();
            visited.add(curr);
            ArrayList<Integer> lst = new ArrayList<>();
            for (int i : neighbors(curr)) {
                currentInDegree.set(i, currentInDegree.get(i) - 1);
            }

            for(int i = 0; i < vertexCount; i ++){
                if(!visited.contains(i) && currentInDegree.get(i) == 0){
                    fringe.push(i);
                }
            }

            return curr;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

        @Override
        public boolean equals(Object o){
            if(this.getClass() != o.getClass()){return false;}
            Edge other = (Edge) o;
            return (this.to == other.to) && (this.from == other.from);
        }

    }

    private void generateG1() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 2);
        addUndirectedEdge(0, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(1, 5);
        addUndirectedEdge(2, 3);
        addUndirectedEdge(2, 6);
        addUndirectedEdge(4, 5);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = path(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    private void generateGT() {
        addEdge(0, 1);
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(2);
        g1.generateGT();
        g1.printPath(1,0);
        System.out.println(g1.pathExists(1,0));
        g1.printDFS(0);
        g1.printDFS(1);
/**
        g1.printPath(0,1);
        g1.printDFS(0);
        g1.printDFS(2);
        g1.printDFS(3);
        g1.printDFS(4);

        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(1, 3);
        g1.printPath(1, 4);
        g1.printPath(4, 0);

        Graph g2 = new Graph(5);
        g2.generateG2();
        g2.printTopologicalSort();
*/

    }
}