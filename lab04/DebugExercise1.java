/**
 * Exercise for learning how the debug, breakpoint, and step-into features
 * work.
 */
public class DebugExercise1 {
    public static float divideThenRound(float top, float bottom) {
        float quotient = top / bottom;
        float result = Math.round(quotient);
        return result;
    }

    public static void main(String[] args) {
        float t = 10;
        float b = 2;
        float result = divideThenRound(t, b);
        System.out.println("round(" + t + "/" + b + ")=" + result);

        float t2 = 9;
        float b2 = 4;
        float result2 = divideThenRound(t2, b2);
        System.out.println("round(" + t2 + "/" + b2 + ")=" + result2);

        float t3 = 3;
        float b3 = 4;
        float result3 = divideThenRound(t3, b3);
        System.out.println("round(" + t3 + "/" + b3 + ")=" + result3);
    }
}
