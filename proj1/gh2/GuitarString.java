package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class GuitarString {
    /**
     * Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday.
     */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;
    private Deque<Double> buffer1;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        buffer = new ArrayDeque();
        int capacity = (int) Math.round(SR / frequency);
        for (int i = 0; i < capacity; i++) {
            buffer.addFirst((double) 0);
        }
    }

    /** Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        buffer1 = new ArrayDeque();
        int b = buffer.size();
        for (int i = 0; i < b; i++) {
            double a = buffer.get(i) + Math.random() - 0.5;
            buffer1.addLast(a);
        }
        buffer = buffer1;
    }

    /** Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double a = DECAY * (buffer.get(0) + buffer.get(1)) / 2;
        buffer.addLast(a);
        buffer.removeFirst();
    }

    /** Return the double at the front of the buffer. */
    public double sample() {
        double x = buffer.get(0);
        return x;
    }
}
