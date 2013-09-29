import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * World containing a number of ants. 
 * Good for testing behavior among the ants
 * 
 * @author Jakob Funder
 * @version 05-06-2008
 */
public class SimpleWorld extends World
{
    private static final int numberOfAnts = 10;

    public SimpleWorld()
    {    
        super(700, 500, 1);
        
        for (int i = 0; i < numberOfAnts; i++) {
            addObject(new MyAnt(), (int) (Math.random()*getWidth()), (int) (Math.random()*getHeight())); 
        }
         addObject(new GameMaster(), getWidth()-150,20);
    }

}
    