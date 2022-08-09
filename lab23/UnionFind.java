import java.util.ArrayList;

public class UnionFind {

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
