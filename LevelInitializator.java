package flow;

import java.util.Vector;
public class LevelInitializator {
    
    private int levelNumber;
    public int dotsAmount;
    
    Node dots[];    // Node dots[da* da]
    List lists[];    
    
    LevelInitializator(int levelNumber, int dotsAmount)
    {
        this.levelNumber = levelNumber;
        this.dotsAmount = dotsAmount;
        
        Levels level = new Levels();
        PointPair dotsPairs[] = new PointPair[dotsAmount];
        dotsPairs = level.getLevelData(1);      // level number
        
        dots    = new Node  [dotsAmount * dotsAmount];
        lists   = new List  [dotsAmount];       // not need because Point know pivot point
        
        for(int i = 0; i < dotsAmount; i++)
        {
            lists[i] = new List();
            lists[i].head.current = dotsPairs[i].head;
            lists[i].tail.current = dotsPairs[i].tail;
        }
        
        for(int i = 0; i < dotsAmount * dotsAmount; i++)
        {
            dots[i] = new Node();
            dots[i].current = new Point(i % dotsAmount, i / dotsAmount, DotsIndex.BLANK_DOT_INDEX);
        }
        
        for(int i = 0; i < dotsAmount; i++)
        {
            dots[dotsPairs[i].head.x + dotsPairs[i].head.y * dotsAmount].current = dotsPairs[i].head;
            dots[dotsPairs[i].tail.x + dotsPairs[i].tail.y * dotsAmount].current = dotsPairs[i].tail;
            
            dots[dotsPairs[i].tail.x + dotsPairs[i].tail.y * dotsAmount].current.setPivot();
            dots[dotsPairs[i].head.x + dotsPairs[i].head.y * dotsAmount].current.setPivot();
        }
    }
    
    int getDotsAmount()
    {
        return dotsAmount;
    }
   
    Node [] getDots()
    {
        return dots;
    }
    
    List [] getLists()
    {
        return lists;
    }
    //Node get set dot
    
}
