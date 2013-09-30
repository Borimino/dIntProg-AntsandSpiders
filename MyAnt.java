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
        if(Greenfoot.getRandomNumber(100) <= 25)
        {
            setLurer();
        }
    }

    private void setWorker()
    {
        if(isLurer)
            MyAnt.numLurers--;

        sugarFactor = 20;
        spiderFactor = 100;
        spiderFactor2 = 0;
        sugarDistance = 700;
        spiderDistance = 100;
        spiderDistance2 = 700;
        antDistance = 8;
        wallDistance = 10;
        wallFactor = 1000;

        isLurer = false;
    }

    private void setLurer()
    {
        if(!isLurer)
            MyAnt.numLurers++;

        sugarFactor = -50;
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

        for(int j = 0; j < 5; j++)
        {
            //this.sugars[j][number] = 0;
        }

        int i = 0;
        List<Sugar> sugars = getSugar(sugarDistance);
        for(Sugar s : sugars)
        {
            if(this.sugars[i][0] == 0)
            {
                this.sugars[i][0] = s.getX();
                this.sugars[i][1] = s.getY();
            }

            double dist = getDistanceToSugar(s);
            for(int j = 0; j < 5; j++)
            {
                if(this.sugars[j][0] == s.getX() && this.sugars[j][1] == s.getY())
                {
                    int sum = 0;
                    for(int k = 2; k < 22; k++)
                    {
                        sum += this.sugars[j][k];
                    }
                    //System.out.println(j + " ; " + sum);
                    if(sum <= 5)
                    {
                        sugarV = sugarV.add(getDirectionToSugar(s).scale(sugarFactor/dist));
                        if(number == 1) System.out.println(s.getX() + ";" + s.getY());
                        if(dist <= 50/2)
                        {
                            this.sugars[j][number+2] = 1;
                            //System.out.println(j + " ; " + "Sets");
                        }
                    } else
                    {
                        this.sugars[j][number+2] = 0;
                        if(number == 1) System.out.println("Too many");
                    }
                }
            }
            i++;
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
