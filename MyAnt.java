import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * 
 * @author 201304194
 * @version Version
 */

public class MyAnt extends Ant
{

    private double wallFactor = 2000;
    private double antFactor = 60;
    private double sugarFactor = 20;
    private double spiderFactor = 200;
    private int wallDistance = 25;
    private int wallDistanceMin = 15;
    private int wallDistanceMax = 38;
    private int antDistance = 8;
    private int sugarDistance = 700;
    private int spiderDistance = 25;
    private int spiderDistance2 = 100;

    /**
     * Find out which forces are effecting the ant.
     * (1) Avoid running into the wall.
     * Add as your progress...
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
     * Walls that a close should be avoided more rapidly than walls far away.
     * 
     * @return a vector that points away from the nearest walls.
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

    private Vector findSugar()
    {
        Vector sugarV = new Vector();

        List<Sugar> sugars = getSugar(sugarDistance);
        for(Sugar s : sugars)
        {
            sugarV = sugarV.add(getDirectionToSugar(s).scale(sugarFactor/getDistanceToSugar(s)));
        }
        return sugarV.scale(sugarFactor);
    }

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
