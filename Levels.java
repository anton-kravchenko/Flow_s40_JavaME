package flow;

public class Levels {

    PointPair firstLevel[] = {
        new PointPair (new Point (0,0, Colors.RED),     new Point (1,4, Colors.RED)),       // red 
        new PointPair (new Point (2,0, Colors.GREEN),   new Point (1,3, Colors.GREEN)),     // green
        new PointPair (new Point (2,1, Colors.BLUE),    new Point (2,4, Colors.BLUE)),      // blue 
        new PointPair (new Point (4,1, Colors.ORANGE),  new Point (3,4, Colors.ORANGE)),    // orange
        new PointPair (new Point (4,0, Colors.YELLOW),  new Point (3,3, Colors.YELLOW))     // yellow
    };
    
    PointPair[] getLevelData(int n)
    {
        // need bd call to create level
        return firstLevel;
    }
    
}
