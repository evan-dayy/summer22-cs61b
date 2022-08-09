public class RedBlackTree<T extends Comparable<T>> {

    /* Root of the tree. */
    RBTreeNode<T> root;

    static class RBTreeNode<T> {

        final T item;
        boolean isBlack;
        RBTreeNode<T> left;
        RBTreeNode<T> right;

        /* Creates a RBTreeNode with item ITEM and color depending on ISBLACK
           value. */
        RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        /* Creates a RBTreeNode with item ITEM, color depending on ISBLACK
           value, left child LEFT, and right child RIGHT. */
        RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left,
                   RBTreeNode<T> right) {
            this.isBlack = isBlack;
            this.item = item;
            this.left = left;
            this.right = right;
        }
    }

    /* Creates an empty RedBlackTree. */
    public RedBlackTree() {
        root = null;
    }

    /* Creates a RedBlackTree from a given 2-3 TREE. */
    public RedBlackTree(TwoThreeTree<T> tree) {
        Node<T> ttTreeRoot = tree.root;
        root = buildRedBlackTree(ttTreeRoot);
    }

    /* Builds a RedBlackTree that has isometry with given 2-3 tree rooted at
       given node R, and returns the root node. */
    RBTreeNode<T> buildRedBlackTree(Node<T> r) {
        if (r == null) {
            return null;
        }
        if (r.getItemCount() == 1) {
            // TODO: Replace with code to create a 2-node equivalent
            T item = r.getItemAt(0);
            RBTreeNode<T> left;
            RBTreeNode<T> right;
            if(r.getChildAt(0) == null){
                return new RBTreeNode<>(true, item, null, null);
            }
            else if(r.getChildAt(0).getItemCount() == 1){
                left = new RBTreeNode<>(true, r.getChildAt(0).getItemAt(0));
            } else{
                left = buildRedBlackTree(r.getChildAt(0));
            }
            if(r.getChildAt(1).getItemCount() == 1){
                right = new RBTreeNode<>(true, r.getChildAt(1).getItemAt(0));
            } else{
                right = buildRedBlackTree(r.getChildAt(1));
            }
            return new RBTreeNode<>(true, item, left, right);
        } else {
            // TODO: Replace with code to create a 3-node equivalent
            T item = r.getItemAt(1);
            RBTreeNode<T> left;
            RBTreeNode<T> right;
            if(r.getChildAt(0) == null){
                return new RBTreeNode<>(true, item, new RBTreeNode<>(false, r.getItemAt(0)), null);
            }
            else if(r.getChildAt(0).getItemCount() == 1 && r.getChildAt(1).getItemCount() == 1){
                left = new RBTreeNode<>(false, r.getItemAt(0),
                        new RBTreeNode<>(true, r.getChildAt(0).getItemAt(0)),
                        new RBTreeNode<>(true, r.getChildAt(1).getItemAt(0)));
            } else if(r.getChildAt(0).getItemCount() == 2 && r.getChildAt(1).getItemCount() == 2){
                left = new RBTreeNode<>(false, r.getItemAt(0),
                        buildRedBlackTree(r.getChildAt(0)),
                        buildRedBlackTree(r.getChildAt(1)));
            } else if(r.getChildAt(0).getItemCount() == 1 && r.getChildAt(1).getItemCount() == 2){
                left = new RBTreeNode<>(false, r.getItemAt(0),
                        new RBTreeNode<>(true, r.getChildAt(0).getItemAt(0)),
                        buildRedBlackTree(r.getChildAt(1)));
            } else {
                left = new RBTreeNode<>(false, r.getItemAt(0),
                        buildRedBlackTree(r.getChildAt(0)),
                        new RBTreeNode<>(true, r.getChildAt(1).getItemAt(0)));
            }

            if(r.getChildAt(2).getItemCount() == 1){
                right = new RBTreeNode<>(true, r.getChildAt(2).getItemAt(0));
            }
            else {
                right = buildRedBlackTree(r.getChildAt(2));
            }
            return new RBTreeNode<>(true, item, left, right);
        }
    }

    /* Flips the color of NODE and its children. Assume that NODE has both left
       and right children. */
    void flipColors(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /* Rotates the given node NODE to the right. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        T item;
        RBTreeNode<T> left;
        RBTreeNode<T> right;
        item = node.left.item;
        right = new RBTreeNode<>(node.left.isBlack, node.item, node.left.right, node.right);
        left =  node.left.left;
        return new RBTreeNode<>(node.isBlack, item, left, right);
    }

    /* Rotates the given node NODE to the left. Returns the new root node of
       this subtree. */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        // TODO: YOUR CODE HERE
        T item;
        RBTreeNode<T> left;
        RBTreeNode<T> right;
        item = node.right.item;
        left = new RBTreeNode<>(node.right.isBlack, node.item, node.left, node.right.left);
        right =  node.right.right;
        return new RBTreeNode<>(node.isBlack, item, left, right);
    }

    public void insert(T item) {
        root = insert(root, item);
        root.isBlack = true;
    }

    /* Inserts the given node into this Red Black Tree*/
    private RBTreeNode<T> insert(RBTreeNode<T> node, T item) {
        // Insert (return) new red leaf node.
        if (node == null) {
            return new RBTreeNode<>(false, item);
        }

        // Handle normal binary search tree insertion.
        int comp = item.compareTo(node.item);
        if (comp == 0) {
            return node; // do nothing.
        } else if (comp < 0) {
            node.left = insert(node.left, item);
        } else {
            node.right = insert(node.right, item);
        }


        // handle "middle of three" and "right-leaning red" structures

        if(!isRed(node) && isRed(node.right)){
            node = rotateLeft(node);
        } //black right red

        if(isRed(node) && isRed(node.right)){
            node = rotateLeft(node);
        } //red right red


        // red right red
        // handle "smallest of three" structure
        if(!isRed(node) && isRed(node.left) && isRed(node.left.left)){
            node = rotateRight(node);
        }

        // handle "largest of three" structure

        if(!isRed(node) && isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }

        return node;

        // TODO: YOUR CODE HERE//fix this return statement
    }

    /* Returns whether the given node NODE is red. Null nodes (children of leaf
       nodes are automatically considered black. */
    private boolean isRed(RBTreeNode<T> node) {
        return node != null && !node.isBlack;
    }

}
