public class TriangleDrawer2 {
    public static void drawTriangle(){

        for (int row = 1, size = 10; row <= size; row++) {
            for(int col = 1; row > col; col++){
                System.out.print("*");
            }
            System.out.println("*");
        }
    }

    public static void main(String[] args){
        drawTriangle();
    }
}
