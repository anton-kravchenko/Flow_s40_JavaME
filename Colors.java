package flow;
public class Colors {
    
    static int RED = 0xFF0000;
    static int GREEN = 0x00FF00;
    static int BLUE = 0x0000FF;
    static int ORANGE = 0xFFA500;
    static int YELLOW = 0xFFFF00;
    static int CYAN = 0x14f7ff;
    static int PINK = 0xFF1493;
    static int MAGENTA = 0xFF00FF;
    
    static int BACKGROUND = 0x111111;
    static int BACKGROUND_LINES = 0x333333; 
    static int ACTIVE_ITEM = 0xAAAAAA;
    static int ACTIVE_ITEM_BACKGROUND = 0x333333;
    

    private static int[] dotsColors = {RED, GREEN, BLUE, ORANGE, YELLOW, CYAN, PINK, MAGENTA};
    
    static int getDotColor(int n)
    {
        return dotsColors[n];
    }
}
