package flow;

public class Point {
    
    
    int x, y;
    int color;
    boolean pivotPoint = false;
    
    Point(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }
    
    void setPivot()
    {
        pivotPoint = true;
    }
}
