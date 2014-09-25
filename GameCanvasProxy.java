package flow;


import java.nio.ByteBuffer;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public class GameCanvasProxy extends GameCanvas implements Runnable{
    
    int canvasHeight = getHeight();
    KeyCodeAdapter keyCode = KeyCodeAdapter.getInstance();
    Graphics graphics = getGraphics();
    
    
    LevelInitializator level = new LevelInitializator(1, 5);     
    int gridSize = level.getDotsAmount();
    
    int keyStates = 0;
    int prevKey = 0;
    
    int canvasWidth = getWidth();
    int itemSize = canvasWidth / gridSize;
    
    Point activeItem = new Point(0,0, DotsIndex.BLANK_DOT_INDEX);
    Point prevActiveItem = new Point(0,0, DotsIndex.BLANK_DOT_INDEX);
    int okPressCounter = 0;
    
    boolean makeLine = false;
    
    protected GameCanvasProxy() {
        super(true);
        keyCode.setCanvas(this);
    }

    public void start(){
        Thread t = new Thread(this);
        t.start();
    }
    
    public void run() {
        while(true)
        {
            handleKeys();
            try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {}
        }
    }
    
    public void setCommandListener(CommandListener c) {
        super.setCommandListener(c);
    }
    
    protected void keyReleased(int keyCode) {
        super.keyReleased(keyCode); 
    }
    
    void drawGrid() {
        
        graphics.setColor(Colors.BACKGROUND);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(Colors.BACKGROUND_LINES);

        for (int i = 0; i <= canvasWidth; i += itemSize) {
            graphics.drawLine(i, 0, i, itemSize * gridSize);
        }
        graphics.drawLine(canvasWidth - 1, 0, canvasWidth - 1, itemSize * gridSize);

        for (int i = 0; i < itemSize * (gridSize+1); i += itemSize) {
            graphics.drawLine(0, i, canvasWidth, i);
        }
        
        drawActiveItem();
        drawDots();
        flushGraphics();
        System.out.println("draw grid");
    }

    void drawDots(){    
    //draw using list not array of grid
    //to draw right forms of lines 
        int dotsMargin = 6;
        int dotsSize = 0;
        Node[] allDots  = level.getDots();
        List[] lists    = level.getLists();
        
        // for(int i = 0; i < gridSize; i++)
        // {
        //     Node start = new Node();
        //     if (null != lists[i].head.next)
        //     {
        //         start.current = lists[i].head.current;
        //     }
        //     if(null != lists[i].tail.prev)
        //     {
        //         start.current = lists[i].tail.current;
        //     }
            
            // if(null != start.current)
            // {
            //     Node iterator = new Node();
            //     iterator.current = new Point(0,0,0);
            //     iterator.current = start.current;
                
            //     Node temp = new Node();
            //     Node tempPrev = new Node();
                
            //     boolean VERT = false;
            //     boolean HORI = false;
            //     boolean G_LN = false;
                
            //     while(null != iterator.next)
            //     {
            //         tempPrev.current = iterator.current;
            //         temp.current     = iterator.next;
                    
            //         if(tempPrev.current.x < temp.current.x)
            //         {
            //             HORI = true;
            //         }
            //         if(tempPrev.current.y < temp.current.y)
            //         {
            //             VERT = true;
            //         }
            //         if(HORI & VERT)
            //         {
            //             G_LN = true;
            //         }
                    
            //     }
            // }
            
        // }
        
       for(int i = 0; i < gridSize * gridSize; i++)
       {
           if(null != allDots[i].current)
           {       
               int x, y;
               x = allDots[i].current.x * itemSize;
               y = allDots[i].current.y * itemSize;
               
               if(true == allDots[i].current.pivotPoint)
               {
                   dotsSize = itemSize;
               }
               else
               {
                   dotsSize = itemSize/2;
               }
                   
               if(-1 != allDots[i].current.color)
               {
                   if(true == allDots[i].current.pivotPoint)
                   {
                       graphics.setColor(allDots[i].current.color);
                       graphics.fillRoundRect(x + dotsMargin/2, y + dotsMargin/2, dotsSize-dotsMargin , dotsSize-dotsMargin, dotsSize, dotsSize);
                   }
                   else
                   {
                       graphics.setColor(allDots[i].current.color);
                       graphics.fillRoundRect( x + dotsMargin/2 + itemSize/2 - dotsSize/2, 
                                               y + dotsMargin/2 + itemSize/2 - dotsSize/2, 
                                               dotsSize-dotsMargin , 
                                               dotsSize-dotsMargin, 
                                               dotsSize, dotsSize);
                   }
               }
           }
       }
        flushGraphics();
    }
    
    void drawActiveItem()
    {
        graphics.setColor(Colors.ACTIVE_ITEM_BACKGROUND);
        if(true == makeLine)
        {
            graphics.fillRect(activeItem.x * itemSize, activeItem.y * itemSize, itemSize, itemSize);
        }
        
        graphics.setColor(Colors.ACTIVE_ITEM);
        graphics.drawRect(activeItem.x * itemSize, activeItem.y * itemSize, itemSize, itemSize);
        System.out.println("drawaActiveItem");
    }
    
    protected void keyPressed(int key) {
        System.out.println("key listener ");
        int keyStates = keyCode.adoptKeyCode(key);
    }

    public Graphics getGraphics() {
        return super.getGraphics();
    }
    
    public void handleKeys() 
    {
        prevKey = keyStates;
        keyStates = getKeyStates();
       
        if (prevKey != keyStates)
        {
            if ((keyStates & LEFT_PRESSED) != 0) 
            {
                move(Canvas.LEFT);
            } 
            else if ((keyStates & RIGHT_PRESSED) != 0) 
            {
                move(Canvas.RIGHT);
            } 
            else if ((keyStates & UP_PRESSED) != 0) 
            {
                move(Canvas.UP);
            } 
            else if ((keyStates & DOWN_PRESSED) != 0) 
            {
                move(Canvas.DOWN);
            }
        }
        
    }
    
    public void move(int dir)        
    {
        int index = activeItem.y * gridSize + activeItem.x;
        Node[] allDots  = level.getDots();
        
        if(true == makeLine)
        { 
            prevActiveItem = activeItem;
        }
        
        if (Canvas.RIGHT == dir)
            if(activeItem.x < (gridSize-1))
                index++;
        
        if (Canvas.LEFT == dir)
            if(activeItem.x > 0)
                index--;
        
        if (Canvas.UP == dir)
            if(activeItem.y > 0)
                index -= gridSize;
    
        if( Canvas.DOWN == dir)
            if(activeItem.y < (gridSize-1))
                index += gridSize;
        
        activeItem = allDots[index].current;
        
        if(true == activeItem.pivotPoint)
        {
            makeLine = false;
        }
        
        if(true == makeLine)
        {
            activeItem.color = prevActiveItem.color;// not need

            activeItem = allDots[index].current;

            allDots[prevActiveItem.y * gridSize + prevActiveItem.x].next = activeItem;
            allDots[activeItem.y * gridSize + activeItem.x].prev = prevActiveItem;
        }
            
        drawGrid();
    }

    void okPressed() 
    {
        int index = activeItem.y * gridSize + activeItem.x;
        Node[] allDots  = level.getDots();
        boolean canChange = true;
        
        if(false == makeLine )
        {
            canChange = false;
            makeLine = true; 
            activeItem = allDots[index].current;
        }
        
        if(true == canChange)
        {
            makeLine = false;
        }
    }
}
