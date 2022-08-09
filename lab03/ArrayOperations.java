public class ArrayOperations {
    /**
     * Delete the value at the given position in the argument array, shifting
     * all the subsequent elements down, and storing a 0 as the last element of
     * the array.
     */
    public static void delete(int[] values, int pos) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        // TODO: YOUR CODE HERE
        for(int i = pos; i <= values.length-1; i++){
            if (i == values.length-1) {
                values[i] = 0;
            } else {
                    values[i] = values[i+1];
            }

        }
    }

    /**
     * Insert newInt at the given position in the argument array, shifting all
     * the subsequent elements up to make room for it. The last element in the
     * argument array is lost.
     */
    public static void insert(int[] values, int pos, int newInt) {
        if (pos < 0 || pos >= values.length) {
            return;
        }
        // TODO: YOUR CODE HERE
        for(int i = values.length-1; i > pos; i--) {
            values[i] = values[i-1];
        }
        values[pos] = newInt;
    }


    /**
     * Returns a new array consisting of the elements of A followed by the
     *  the elements of B.
     */
    public static int[] catenate(int[] A, int[] B) {
        // TODO: YOUR CODE HERE
        int totalLength = A.length + B.length;
        int[] newArray = new int[totalLength];
        for (int i = 0; i < totalLength; i++){
            if (i < A.length) {
                newArray[i] = A[i];
            } else {
                newArray[i] = B[i - A.length];
            }
        }
        return newArray;
    }

}