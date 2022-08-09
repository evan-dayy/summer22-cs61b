public class BinaryTree<T> {

    TreeNode<T> root;

    public BinaryTree() {
        root = null;
    }

    public BinaryTree(TreeNode<T> t) {
        root = t;
    }

    public TreeNode<T> getRoot() {
        return root;
    }

    /* Returns the height of the tree. */
    public int height() {
        // TODO: YOUR CODE HERE
        if (root == null) {
            return 0;
        }
        return root.heightHelper();
    }

    /* Returns true if the tree's left and right children are the same height
       and are themselves completely balanced. */
    public boolean isCompletelyBalanced() {
        // TODO: YOUR CODE HERE
        if(this.height() == 0 || this.height() == 1 || this.height() == 2){
            return true;
        }
        return root.balanceHelper();
    }

    /* Returns a BinaryTree representing the Fibonacci calculation for N. */
    public static BinaryTree<Integer> fibTree(int N) {
        // BinaryTree<Integer> result = new BinaryTree<Integer>();
        TreeNode<Integer> node = new TreeNode<Integer>(1, new TreeNode<Integer>(1),
                                                        new TreeNode<Integer>(0));
        if (N == 0){
            return new BinaryTree<Integer>(new TreeNode<Integer>(0));
        }
        else if(N == 1){
            return new BinaryTree<Integer>(new TreeNode<Integer>(1));
        }
        else if(N == 2){
            return new BinaryTree<Integer>(node);
        }
        else{
            for(int i = 3; i <= N; i ++){
                node = node.fibHelper();
            }
        }
        return new BinaryTree<Integer>(node);
    }

    /* Print the values in the tree in preorder: root value first, then values
       in the left subtree (in preorder), then values in the right subtree
       (in preorder). */
    public void printPreorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printPreorder();
            System.out.println();
        }
    }

    /* Print the values in the tree in inorder: values in the left subtree
       first (in inorder), then the root value, then values in the first
       subtree (in inorder). */
    public void printInorder() {
        if (root == null) {
            System.out.println("(empty tree)");
        } else {
            root.printInorder();
            System.out.println();
        }
    }

    /* Prints out the contents of a BinaryTree with a description in both
       preorder and inorder. */
    private static void print(BinaryTree t, String description) {
        System.out.println(description + " in preorder");
        t.printPreorder();
        System.out.println(description + " in inorder");
        t.printInorder();
        System.out.println();
    }

    /* Fills this BinaryTree with values a, b, and c. DO NOT MODIFY. */
    public void sampleTree1() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c"));
    }

    /* Fills this BinaryTree with values a, b, and c, d, e, f. DO NOT MODIFY. */
    public void sampleTree2() {
        root = new TreeNode("a",
                  new TreeNode("b", new TreeNode("d", new TreeNode("e"),
                  new TreeNode("f")), null), new TreeNode("c"));
    }

    /* Fills this BinaryTree with the values a, b, c, d, e, f. DO NOT MODIFY. */
    public void sampleTree3() {
        root = new TreeNode("a", new TreeNode("b"), new TreeNode("c",
               new TreeNode("d", new TreeNode("e"), new TreeNode("f")), null));
    }

    /* Fills this BinaryTree with the same leaf TreeNode. DO NOT MODIFY. */
    public void sampleTree4() {
        TreeNode leafNode = new TreeNode("c");
        root = new TreeNode("a", new TreeNode("b", leafNode, leafNode),
                                 new TreeNode("d", leafNode, leafNode));
    }

    public void sampleTreeX() {
        TreeNode leafNode = new TreeNode("c");
        root = new TreeNode("a", leafNode,
                new TreeNode("b", leafNode, new TreeNode("b", leafNode, leafNode)));
    }

    /* Creates two BinaryTrees and prints them out in inorder. */
    public static void main(String[] args) {
        BinaryTree t = new BinaryTree();
        t.sampleTree2();
        System.out.println(t.isCompletelyBalanced());


//        BinaryTree<Integer> t0 = fibTree(0);
//        print(t0, "Fib");
//        BinaryTree<Integer> t = fibTree(1);
//        print(t, "Fib");
//        BinaryTree<Integer> t1 = fibTree(2);
//        print(t1, "Fib");
//        BinaryTree<Integer> t2 = fibTree(3);
//        print(t2, "Fib");
//        BinaryTree<Integer> t3 = fibTree(4);
//        print(t3, "Fib");
//        BinaryTree<Integer> t4 = fibTree(5);
//        print(t4, "Fib");
//        BinaryTree<Integer> t5 = fibTree(10);
//        print(t5, "Fib");
//
//        BinaryTree<Integer> tn = new BinaryTree();
//        System.out.println(tn.height());
//        System.out.println(t0.height());
//        System.out.println(t.height());
//        System.out.println(t1.height());
//        System.out.println(t2.height());
//        System.out.println(t3.height());
//        System.out.println(t4.height());


//        BinaryTree t;
//        t = new BinaryTree();
//        t.sampleTreeX();
//        System.out.println(t.height());
//        print(t, "the empty tree");
//        t.sampleTree1();
//        print(t, "sample tree 1");
//        t.sampleTree2();
//        print(t, "sample tree 2");
//        t.sampleTree3();
//        print(t, "sample tree 3");
//        t.sampleTree4();
//        print(t, "sample tree 4");
    }

    /* Note: this class is public in this lab for testing purposes. However,
       in professional settings as well as the rest of your labs and projects,
       we recommend that you keep your inner classes private. */
    static class TreeNode<T> {

        private T item;
        private TreeNode<T> left;
        private TreeNode<T> right;

        TreeNode(T obj) {
            item = obj;
            left = null;
            right = null;
        }

        TreeNode(T obj, TreeNode<T> left, TreeNode<T> right) {
            item = obj;
            this.left = left;
            this.right = right;
        }

        public T getItem() {
            return item;
        }

        public TreeNode<T> getLeft() {
            return left;
        }

        public TreeNode<T> getRight() {
            return right;
        }

        void setItem(T item) {
            this.item = item;
        }

        void setLeft(TreeNode<T> left) {
            this.left = left;
        }

        void setRight(TreeNode<T> right) {
            this.right = right;
        }

        private void printPreorder() {
            System.out.print(item + " ");
            if (left != null) {
                left.printPreorder();
            }
            if (right != null) {
                right.printPreorder();
            }
        }

        private void printInorder() {
            if (left != null) {
                left.printInorder();
            }
            System.out.print(item + " ");
            if (right != null) {
                right.printInorder();
            }
        }

        private int heightHelper() {
            if (left == null && right == null) {
                return 1;
            }
            else if(left == null){
                return 1+ right.heightHelper();
            }
            else if(right == null){
                return 1+ left.heightHelper();
            }
            else {
                return 1 + Math.max(left.heightHelper(), right.heightHelper());
            }
        }

        private boolean balanceHelper(){

            if (left.heightHelper() == 1 && right.heightHelper() == 1){
                return true;
            }

            if(left.heightHelper() != right.heightHelper()){
                return false;
            }

            if (left != null && right != null){
                return left.balanceHelper() && right.balanceHelper();
            } else{
                return false;
            }

        }

        private TreeNode<T> fibHelper(){
            TreeNode<T> n;
            n = new TreeNode<T>((T)(Integer)((int)getItem() + (int)left.getItem()), this, left);
            return n;
        }


        // TODO: ADD HELPER METHODS HERE
    }
}
