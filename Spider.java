import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 *  A spider that tries to eat the ants
 * 
 * @author Jakob Funder
 * @version 05/22/2008
 */
public class Spider extends MovingActor
{
    private double wallFactor = 5;
    private double antFactor = 12;
    public Spider() {
       
        super(new Vector(1,-0.4), 2);
    }
    
    public void act() {
        super.act();
        eatAnts();
    }
    /**
     * Get forces effecting the spider.
     * Theres only one: ants.
     * @return a vector that repersents the force effecting the spider.
     */
    public Vector getForces() {
        Vector f = new Vector(0,0);
        f = f.add(avoidWall());
        f = f.add(chaseAnts());
        return f;
    }
    
    /**
     * Look for all ants within a certain distance and chase them. 
     * Ants that a close are more important than distant ants.
     * @return a Vector pointing towards the ants
     */
    private Vector chaseAnts() {
        Vector tarV = new Vector(0,0);
        List<Ant> tarB = getAnts(10000);
        for (Ant a: tarB) {
            double distanceFactor = 1/getDistanceToAnt(a); // the closest ants are the most important ants.
            tarV = tarV.add(getDirectionToAnt(a).scale(distanceFactor));
        } 
        return tarV.scale(antFactor);
    }
    
    /**
     * Look for an ant close to the spider and eat it.
     */
    private void eatAnts() {
        if (getObjectsInRange(5, Ant.class).size() != 0)
            ((List<Ant>) getObjectsInRange(5, Ant.class)).get(0).imDead(); 
    }
    /**
     * Get all ants with in a certain distance
     * @param distance get all spiders within this distance.
     */
    private List<Ant> getAnts(int distance) {
        return (List<Ant>) getObjectsInRange(distance, Ant.class);   
    }  
    
    /**
     * Avoid moving into any of the walls.
     * Walls that a close should be avoided more rapidly than walls far away.
     * @return a vector that points away from the nearest walls.
     */
    
    private Vector avoidWall() {
        double x = 0; double y = 0; 
        y += 1/distanceToTopWall(); 
        y -= 1/distanceToBottomWall();
        x -= 1/distanceToRightWall();
        x += 1/distanceToLeftWall();
        Vector wallV = new Vector(x,y);
        return wallV.scale(wallFactor);
    }
     /**
     * Find the distance to a Ant
     * @param a Ant we want the distance to
     * @return distance to Ant a
     */
    private double getDistanceToAnt(Ant a) {
        return Math.sqrt(Math.pow(getX() - a.getX(), 2) + Math.pow(getY() - a.getY(), 2));
    }

    
    
    
    /**
     * Gives a vector in the direction of Ant a.
     * @param a Ant we want the direction to.
     * @return a Vector in the direction of Ant a.
     */
    private Vector getDirectionToAnt(Ant a) {
        return new Vector((a.getX() - getX())/(getDistanceToAnt(a)) ,(a.getY() - getY())/getDistanceToAnt(a)) ;
    } 
    
    public double getAcceleration(){
        return 40;
    }

    public double getVelocity(){
        return 1;
    }


}
