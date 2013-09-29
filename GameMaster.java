import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * The GameMaster keeps track of how many rounds have past, the amount of sugar left
 * and the number of ants left. 
 * If there are no more ants we have lost.
 * If there is no more sugar left we have won.
 * 
 * @author Jakob Funder
 * @version 07-07-2008
 */
public class GameMaster extends Actor
{
    int rounds = 0;
    public void act() 
    {
          rounds = rounds +1;
          
        String msg = "Round: " + rounds;
        if (getWorld().getClass() == EmptyWorld.class) {  
        }
        if (getWorld().getClass() == SimpleWorld.class) {  
            msg += ", Ants left: " +  getWorld().getObjects(Ant.class).size();
        }
        if (getWorld().getClass() == WorldWithSugar.class) {  
             msg += ", Ants left: " +  getWorld().getObjects(Ant.class).size();
            msg += ", Sugar left: " + getWorld().getObjects(Sugar.class).size();  
            if ( getWorld().getObjects(Sugar.class).size() == 0) {
                msg = "You have won in " + rounds-- + " rounds!";
            }
        }
        if (getWorld().getClass() == WorldWithSugarAndSpiders.class) {
            msg += ", Ants left: " +  getWorld().getObjects(Ant.class).size();
            msg += ", Sugar left: " + getWorld().getObjects(Sugar.class).size();  
            if ( getWorld().getObjects(Sugar.class).size() == 0) {
                msg = "You have won in " + rounds-- + " rounds!";
            }
           else if ( getWorld().getObjects(Ant.class).size() == 0) {
                msg = "You have lost in " + rounds-- + " rounds!";
            }   
        }
        
        greenfoot.GreenfootImage gi;
        gi=new greenfoot.GreenfootImage(300,100);
        gi.drawString(msg,0,50);
        this.setImage(gi); 
      
    }    
}
