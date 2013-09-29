
public class Vector
{
    private double x,y;
    /**
     * Contructor for the Vector class. 
     * @param x x coordinate.
     * @param y y coordinate.
     */
    public Vector(double x, double y)
    {
        this.x = x; this.y = y;
    }
    public Vector()
    {
        this.x = 0; this.y = 0;
    }
    /**
     * Get the x coordinate of the vector
     * @return x coordinate
     */
    public double getX() {
        return x;
    }
     /**
     * Get the y coordinate of the vector
     * @return y coordinate.
     */
    public double getY() {
        return y;
    }

    public Vector add(Vector v){
        return new Vector(x+v.x, y+v.y);
    }
    
    public Vector sub(Vector v){
        return new Vector(x-v.x, y-v.y);
    }
    
    public Vector scale(double m){
        return new Vector(x*m, y*m);
    }
            
    /**
     * Return a new version of this vector with length 1.
     * I.e. a normalized version of this vector
     */
    public Vector normalize() {
        double len = length();
        if (len == 0) return new Vector();
        return new Vector(x/len,y/len);
    } 
    
    /**
     * Get the length of the vector
     * @return length of the vector
     */
    public double length() {
        return Math.sqrt(x*x+y*y);
    }   
}
