import java.security.Key;
import java.util.Iterator;
import java.util.Objects;
import java.util.LinkedList;

public class HashMap<K, V> implements Map61BL<K, V> {
    private LinkedList<Entry<K, V>>[] arr;
    private int size = 0;
    private double loadFactor = 0.75;

    // TODO: Constructors
    public HashMap(){
        this.arr = (LinkedList<Entry<K, V>>[]) new LinkedList[16];
        for(int i = 0; i < arr.length; i ++){
            this.arr[i] = new LinkedList<Entry<K, V>>();
        }
    }
    public HashMap(int l){
        this.arr = (LinkedList<Entry<K, V>>[]) new LinkedList[l];
        for(int i = 0; i < arr.length; i ++){
            this.arr[i] = new LinkedList<Entry<K, V>>();
        }
    }
    public HashMap(int l, double g){
        this.arr = (LinkedList<Entry<K, V>>[]) new LinkedList[l];
        for(int i = 0; i < arr.length; i ++){
            this.arr[i] = new LinkedList<Entry<K, V>>();
        }
        loadFactor = g;
    }

//    public int hash(String key){
//        return (int) (key.charAt(0) - 'A');
//    }
    @Override
    public void clear() {
        this.arr = (LinkedList<Entry<K, V>>[]) new LinkedList[this.arr.length];
        this.size = 0;
    }

    @Override
    public boolean containsKey(K key) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        if (index > arr.length){
            return false;
        }
        if (arr[index] == null){
            return false;
        } else{
            for (Entry<K, V> e : arr[index]){
                if(e.getKey() == key){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(K key) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        if (containsKey(key)){
            for (Entry<K, V> e : arr[index]){
                if(e.getKey() == key){
                    return e.getValue();
                }
            }
        }
        return null;
    }

    public Entry<K, V> getKeyEntry(K key) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        if (containsKey(key)){
            for (Entry<K, V> e : arr[index]){
                if(e.getKey() == key){
                    return e;
                }
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        if (containsKey(key)) {
            if (this.get(key) != value) {
                Entry<K, V> temp = this.getKeyEntry(key);
                temp.setValue(value);
            }
        } else{
            Entry<K, V> pair = new Entry<K, V>(key, value);
            size ++;
            int index = Math.floorMod(key.hashCode(), arr.length);
            arr[index].addFirst(pair);
        }

        if((double)size / (double)arr.length > loadFactor){
            this.arr = resize(this.arr);
        }
    }

    public LinkedList<Entry<K, V>>[] resize(LinkedList<Entry<K, V>>[] arrO){
        LinkedList<Entry<K, V>>[] newArr = (LinkedList<Entry<K, V>>[]) new LinkedList[2 * arrO.length];
        System.arraycopy(arrO, 0, newArr, 0, arrO.length);
        return newArr;
    }


    @Override
    public V remove(K key) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        if(containsKey(key)){
            Entry<K, V> temp = this.getKeyEntry(key);
            V value = temp.getValue();
            arr[index].remove(temp);
            size --;
            return value;
        }
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        int index = Math.floorMod(key.hashCode(), arr.length);
        if(containsKey(key)){
            Entry<K, V> temp = this.getKeyEntry(key);
            if(temp.getValue() == value){
                arr[index].remove(temp);
                size --;
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.arr.length;
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    /* TODO: Instance variables here */

    /* TODO: Constructors here */

    /* TODO: Interface methods here */

    private static class Entry<K, V> {

        private K key;
        private V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }
        public void setValue(V value) {
           this.value = value;
        }
        public K getKey() {
            return key;
        }

        /* Returns true if this key matches with the OTHER's key. */
        public boolean keyEquals(Entry other) {
            return key.equals(other.key);
        }

        /* Returns true if both the KEY and the VALUE match. */
        @Override
        public boolean equals(Object other) {
            return (other instanceof Entry
                    && key.equals(((Entry) other).key)
                    && value.equals(((Entry) other).value));
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }
}