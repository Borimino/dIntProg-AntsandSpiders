import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A piece of sugar, that grows smaller and finally disapears as ants eats it.
 * 
 * @author Jakob Funder
 * @version 07/07/2008
 */
public class Sugar extends Actor
{
    GreenfootImage[] images = new  GreenfootImage[]{
        new GreenfootImage("sukker1.png"), 
        new GreenfootImage("sukker2.png"), 
        new GreenfootImage("sukker3.png"), 
        new GreenfootImage("sukker4.png")};
    {
        for (GreenfootImage i : images){
            i.scale(50,50);
        }
    }
    int sugar = 4000;
    public Sugar() {
        setImage(images[3]);
    }
    public void act() 
    {
        // If enough ants near, eat a bit of the sugar.
        if (getObjectsInRange(30, Ant.class).size() != 0) {
            sugar = sugar - getObjectsInRange(30, Ant.class).size()-1 ;
            
            if (sugar < 1) {
                // No more sugar
                getWorld().removeObject(this);
            } else {
                setImage(images[(sugar-1)/1000]);
            }
        }     
    }    
}
