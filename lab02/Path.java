/** A class that represents a path via pursuit curves. */
public class Path {
    public Point next;
    public Point curr;

    public Path(double x, double y) {
        // Constructor
        this.next = new Point(x,y);
        this.curr = new Point(0, 3);
    }

    public double getCurrX(){
        return curr.getX();
    }
    public double getCurrY(){
        return curr.getY();
    }
    public double getNextX(){
        return next.getX();
    }

    public double getNextY(){
        return next.getY();
    }

    public Point getCurrentPoint() {
        return this.curr;
    }

    public void setCurrentPoint(Point next) {
        this.curr = next;
    }

    public void iterate(double dx, double dy){
        this.curr.setX(next.getX());
        this.curr.setY(next.getY());
        next.setX(next.getX()+dx);
        next.setY(next.getY()+dy);

    }

}

