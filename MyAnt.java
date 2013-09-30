import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * 
 * @author 201304194
 * @version Version
 */

public class MyAnt extends Ant
{

    private double wallFactor;
    private double antFactor = 60;
    private double sugarFactor;
    private double emptySugarFactor;
    private double spiderFactor;
    private double spiderFactor2;
    private int wallDistance;
    private int antDistance;
    private int sugarDistance;
    private int spiderDistance;
    private int spiderDistance2;
    private int changeDistance = 250;

    private boolean isLurer = false;
    private int number;

    private static final int maxLurers = 8;
    private static int numLurers = 0;
    private static int numAnts = 0;
    private static int[][] sugars = new int[5][22];

    public MyAnt()
    {
        super();
        MyAnt.numLurers = 0;
        if(MyAnt.numAnts >= 20)
        {
            MyAnt.numAnts = 0;
            this.sugars = new int[5][22];
        }
        number = MyAnt.numAnts;
        MyAnt.numAnts++;
        if(number <= 5)
        {
            setLurer();
        } else
        {
            setWorker();
        }
    }

    private void setWorker()
    {
        if(isLurer)
            MyAnt.numLurers--;

        sugarFactor = 20;
        emptySugarFactor = 75;
        spiderFactor = 200;
        spiderFactor2 = 0;
        sugarDistance = 700;
        spiderDistance = 50;
        spiderDistance2 = 700;
        antDistance = 8;
        wallDistance = 25;
        wallFactor = 1000;

        isLurer = false;
    }

    private void setLurer()
    {
        if(!isLurer)
            MyAnt.numLurers++;

        sugarFactor = -50;
        emptySugarFactor = 0;
        spiderFactor = 150;
        spiderFactor2 = 150;
        sugarDistance = 700;
        spiderDistance = 25;
        spiderDistance2 = 50;
        antDistance = 10;
        wallDistance = spiderDistance*5;
        wallFactor = 2000;

        isLurer = true;
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

    private int numAnts(int dist)
    {
        List<Ant> ants = getAnts(dist);
        return ants.size();
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
    
    private Vector findEmptySugar()
    {
        Vector sugarV = new Vector();
        
        List<Sugar> sugars = getSugar(sugarDistance);
        for(Sugar s : sugars)
        {
            Vector sV = getDirectionToSugar(s);
            List<Ant> ants = getAnts(sugarDistance);
            for(Ant a : ants)
            {
                Vector aV = getDirectionToAnt(a);
                if(!(Math.abs(aV.getX()-sV.getX()) <= 25 && Math.abs(aV.getY()-sV.getY()) <= 25))
                {
                    sugarV = sugarV.add(sV.scale(emptySugarFactor/getDistanceToSugar(s)));
                }
            }
        }
        return sugarV.scale(Math.abs(emptySugarFactor));
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
            /*if(getDistanceToSpider(s) <= changeDistance && MyAnt.numLurers < MyAnt.maxLurers)
            {
            setLurer();
            } else
            {
            setWorker();
            }*/
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
        return "Markus Andreassen 201304194";
    }

}   
