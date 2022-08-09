import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;

public class MyTrieSet implements TrieSet61BL{
    private Node root;
    public class Node{
        private char c;
        private boolean isLeaf; // whether the end of a String
        private LinkedHashMap<Character, Node> child = new LinkedHashMap<>();
        private Node(){}  // empty lead Node
        private Node(char c){ // with char
            this.c = c;
        }

        public LinkedHashMap<Character, Node> getChild(){
            return child;
        }
        public String getChar(){
            return String.valueOf(c);
        }
        public void isLeaf(){
            this.isLeaf = true;
        }

        public boolean testLeaf(){
            return this.isLeaf;
        }

    }
    public MyTrieSet() {
        root = new Node();
    }


    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        root.getChild().clear();
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     *
     * @param key
     */
    @Override
    public boolean contains(String key) {
        LinkedHashMap<Character, Node> child = root.getChild();
        Node curr = root;
        for(int i = 0; i < key.length(); i ++){
            if(child.containsKey(key.charAt(i))){
                curr = child.get(key.charAt(i));
                child = curr.getChild();
            } else{
                return false;
            }
            if(curr.testLeaf()){
                if(i == key.length() - 1){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Inserts string KEY into Trie
     *
     * @param key
     */
    @Override
    public void add(String key) {
        LinkedHashMap<Character, Node> child = root.getChild();
        Node curr = root;
        for(int i = 0; i < key.length(); i ++){
            if(child != null && child.containsKey(key.charAt(i))){
                curr = child.get(key.charAt(i));
            } else{
                curr = new Node(key.charAt(i));
                child.put(key.charAt(i), curr);
            }
            child = curr.getChild();
            if(i ==  key.length() - 1){ // reach the end of the word
                curr.isLeaf();
            }
        }
    }

    /**
     * Returns a list of all words that start with PREFIX
     *
     * @param prefix
     */
    @Override
    public List<String> keysWithPrefix(String prefix) {

        String cat;
        LinkedHashMap<Character, Node> child = root.getChild();
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (child.containsKey(prefix.charAt(i))) {
                curr = child.get(prefix.charAt(i));
                child = curr.getChild();
            } else{
                return null;
            }
        }
        return helper(curr, new ArrayList<>(), prefix);
    }

    public List<String> helper(Node curr, List<String> lst, String prefix) {
        LinkedHashMap<Character, Node> child = curr.getChild();
        if(curr.getChild().size() == 0){
            return null;
        }
        for(char x: child.keySet()){
            curr = child.get(x);
            if(curr.testLeaf() && curr.getChild().size() == 0){
                lst.add(prefix + String.valueOf(x));
            }
            else{
                String prefix1 = prefix + String.valueOf(x);
                helper(curr, lst, prefix1);
                lst.add(prefix + String.valueOf(x));
            }
        }
        return lst;
    }



    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 16. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }
}
