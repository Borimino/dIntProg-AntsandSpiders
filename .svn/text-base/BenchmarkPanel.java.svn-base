import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
public class BenchmarkPanel  extends Actor
{
    private int run = 0;
    private int totalRuns = 0;
    private double timePerRun = 0;
    private int round = 0;
    private long lastPrint = 0;
    
    public BenchmarkPanel(int totalRuns){
        this.totalRuns = totalRuns;
    }
    
    public void act() 
    {
        long now = System.currentTimeMillis();
        if (now - lastPrint < 100) return;
        lastPrint = now;
            
        String msg = "Run: " + run + " of " + totalRuns + " Round " + round;
        greenfoot.GreenfootImage gi;
        gi=new greenfoot.GreenfootImage(300,40);

        gi.setColor(Color.BLUE);
        if (totalRuns != 0) {
            gi.fillRect(2,2, (200*100*run/totalRuns)/100, 20);
        }
        gi.setColor(Color.BLACK);
        
        gi.drawString(msg,2,35);
        gi.drawRect(2,2, 200, 20);

        this.setImage(gi); 
    }    
    
    public void setProgress(int run, int round){
        this.run = run;
        this.round = round;
    }
}
