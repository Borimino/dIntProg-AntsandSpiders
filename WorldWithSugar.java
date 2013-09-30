import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * World with a number of ants and sugar. 
 * For testing the ants ability to eat sugar.
 * 
 * @author Jakob Funder
 * @version 05-06-2008
 */
public class WorldWithSugar extends World
{
    private static final int numberOfAnts = 20;
    private static final int pcsOfSugar = 5;

    public WorldWithSugar()
    {    
        super(700, 500, 1);
      
        for (int i = 0; i < pcsOfSugar; i++) {
            addObject(new Sugar(), (int) (Math.random()*getWidth()), (int) (Math.random()*getHeight())); 
        }
        for (int i = 0; i < numberOfAnts; i++) {
            addObject(new MyAnt(), (int) (Math.random()*getWidth()), (int) (Math.random()*getHeight())); 
        }
          addObject(new GameMaster(), getWidth()-150,20); 
          
    }


}
