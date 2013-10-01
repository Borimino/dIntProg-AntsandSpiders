import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * 
 * @author 201304194
 * @version Version
 */

public class MyAnt extends Ant
{

    /**
     * The factors to multiply the different forces with.
     */
    private double wallFactor = 2000;
    private double antFactor = 60;
    private double sugarFactor = 20;
    private double spiderFactor = 200;
    /**
     * The distances the forces work within.
     */
    private int wallDistance = 15;
    private int wallDistanceMin = 15;
    private int wallDistanceMax = 38;
    private int antDistance = 8;
    private int sugarDistance = 700;
    private int spiderDistance = 25;
    private int spiderDistance2 = 100;

    /**
     * Find out which forces are effecting the ant.
     * 
     * @return The total of the forces
     * 
     */
    public Vector getForces()
    {
        Vector f = new Vector(); 
        setWallDistance();    
        f = f.add(avoidWall());
        f = f.add(avoidAnt());
        f = f.add(findSugar());
        f = f.add(avoidSpider());
        return f;
    }

    /**
     * Avoid moving into any of the walls.
     * Only avoids the walls that are closer than <i>wallDistance</i>.
     * 
     * @return A vector that points away from the nearest walls.
     * 
     */

    private Vector avoidWall()
    {
        Vector wallV = new Vector();
        if(distanceToTopWall() <= wallDistance)
            wallV = wallV.add(new Vector(0, 1/distanceToTopWall())); // avoid top wall 

        if(distanceToBottomWall() <= wallDistance)
            wallV = wallV.add(new Vector(0, -1/distanceToBottomWall()));// avoid bottom wall, not yet implemented

        if(distanceToLeftWall() <= wallDistance)
            wallV = wallV.add(new Vector(1/distanceToLeftWall(), 0));// avoid left wall, not yet implemented

        if(distanceToRightWall() <= wallDistance)
            wallV = wallV.add(new Vector(-1/distanceToRightWall(),0)); // avoid right wall

        return wallV.scale(wallFactor);
    }   

    /**
     * Avoid moving into any other ants.
     * Only avoids ants that are closer than <i>antDistance</i>.
     * 
     * @return A vector that points away from all ants within range
     * 
     */
    private Vector avoidAnt()
    {
        Vector antV = new Vector();

        List<Ant> ants = getAnts(antDistance);
        for(Ant a : ants)
        {
            antV = antV.add(getDirectionToAnt(a).scale(-antFactor/getDistanceToAnt(a)));
        }
        return antV.scale(antFactor);
    }

    /**
     * Move towards the nearest sugar.
     * Only considers sugars within <i>sugarDistance</i>.
     * 
     * @return A vector that points towards sugar
     * 
     */
    private Vector findSugar()
    {
        Vector sugarV = new Vector();

        List<Sugar> sugars = getSugar(sugarDistance);
        for(Sugar s : sugars)
        {
            //if(getObjectsAtOffset(getX()-s.getX(), getY()-s.getY(), Ant.class) == null)
                sugarV = sugarV.add(getDirectionToSugar(s).scale(sugarFactor/getDistanceToSugar(s)));
        }

        /*
        if(sugarV.getX() == 0 && sugarV.getY() == 0)
        {
            for(Sugar s : sugars)
            {
                sugarV = sugarV.add(getDirectionToSugar(s).scale(sugarFactor/getDistanceToSugar(s)));
            }
        }*/
        return sugarV.scale(sugarFactor);
    }

    /**
     * Avoid the spider.
     * Only considers the spider if it is within <i>spiderDistance</i>.
     * 
     * @return A vector that points away from the spider
     * 
     */
    private Vector avoidSpider()
    {
        Vector spiderV = new Vector();

        List<Spider> spiders = getSpider(spiderDistance);
        for(Spider s : spiders)
        {
            spiderV = spiderV.add(getDirectionToSpider(s).scale(-spiderFactor/getDistanceToSpider(s)));
        }
        return spiderV.scale(spiderFactor);
    }

    /**
     * Sets the <i>wallDistance</i> depending on the closenes of the spider.
     */
    private void setWallDistance()
    {
        List<Spider> spiders = getSpider(spiderDistance);
        for(Spider s : spiders)
        {
            if(getDistanceToSpider(s) < spiderDistance2)
            {
                wallDistance = wallDistanceMax;
            } else
            {
                wallDistance = wallDistanceMin;
            }
        }
    }

    public String getCreator()
    {
        return "Markus Andreassen 201304194";
    }

}   
