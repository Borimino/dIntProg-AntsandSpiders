import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * 
 * @author 201304194
 * @version Version
 */

public class MyAnt extends Ant
{

    private double wallFactor = 1000;
    private double antFactor = 25;
    private double sugarFactor = 20;
    private double spiderFactor = 100;
    private double spiderFactor2 = 0;
    private int wallDistance = 10;
    private int antDistance = 8;
    private int sugarDistance = 700;
    private int spiderDistance = 100;
    private int spiderDistance2 = 700;

    public MyAnt()
    {
        super();
        if(Greenfoot.getRandomNumber(100) <= 25)
        {
            sugarFactor = -50;
            spiderFactor = 150;
            spiderFactor2 = 150;
            sugarDistance = 700;
            spiderDistance = 25;
            spiderDistance2 = 50;
            antDistance = 10;
            wallDistance = spiderDistance*3;
            wallFactor = wallFactor*2;
        }
    }

    /**
     * Find out which forces are effecting the ant.
     * 
     * @return The total of the forces
     * 
     */
    public Vector getForces()
    {
        Vector f = new Vector();     
        f = f.add(avoidWall());
        f = f.add(avoidAnt());
        f = f.add(findSugar());
        f = f.add(avoidSpider());
        f = f.add(findSpider());
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
        {
            wallV = wallV.add(new Vector(0, 1/distanceToTopWall()));
        }

        if(distanceToBottomWall() <= wallDistance)
        {
            wallV = wallV.add(new Vector(0, -1/distanceToBottomWall()));
        }

        if(distanceToLeftWall() <= wallDistance)
        {
            wallV = wallV.add(new Vector(1/distanceToLeftWall(), 0));
        }

        if(distanceToRightWall() <= wallDistance)
        {
            wallV = wallV.add(new Vector(-1/distanceToRightWall(),0));
        }

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
        return sugarV.scale(Math.abs(sugarFactor));
    }

    private Vector findSpider()
    {
        Vector spiderV = new Vector();

        List<Spider> spiders = getSpider(700);
        for(Spider s : spiders)
        {
            if(getDistanceToSpider(s) >= spiderDistance2)
            {
                spiderV = spiderV.add(getDirectionToSpider(s).scale(spiderFactor2*getDistanceToSpider(s)));
            }
        }
        return spiderV.scale(spiderFactor2);
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

    public String getCreator()
    {
        return "201304194";
    }

}   
