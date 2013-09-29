import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.reflect.*;

/**
 * Write a description of class BenchmarkWorld her.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BenchmarkWorld  extends World
{
    private static final int numberOfAnts = 20;
    private static final int pcsOfSugar = 5;
    private static final int numberOfSpiders = 1;
    private static final int numberOfRuns = 10;
    private ArrayList<Integer> stat;
    
    private int round;
    private int run;
    private BenchmarkPanel panel;
    private String creator;
    
    
    public BenchmarkWorld(){
        super(700, 500, 1);
        Greenfoot.setSpeed(100);
        panel = new BenchmarkPanel(numberOfRuns);
        creator = new MyAnt().getCreator();
        addObject(panel, 155 ,20); 
        
        setUpBenchmark();
        nextRun();
    } 
    
    private void setUpBenchmark(){
        stat = new ArrayList<Integer>();
        run = 0;
        panel.setProgress(1, 0);
    }
    
    private void nextRun(){
        round = 0;
        run++;
        if (run > numberOfRuns){
            run = 1;
            panel.setProgress(numberOfRuns, 0);
            submitScore();
            setUpBenchmark();
            nextRun();
            Greenfoot.stop();
        }
        
        cleanWorld();
        populateWorld();
    }
    
    private void cleanWorld(){
        removeObjects(getObjects(Sugar.class));
        removeObjects(getObjects(Spider.class));
        removeObjects(getObjects(DeadAnt.class));
        removeObjects(getObjects(MyAnt.class));
    }
    
    private void populateWorld(){
        for (int i = 0; i < pcsOfSugar; i++) {
            addObject(new Sugar(), (int) (Math.random()*getWidth()), (int) (Math.random()*getHeight())); 
        }
        for (int i = 0; i < numberOfSpiders; i++) {
            addObject(new Spider(), (int) (Math.random()*getWidth()), (int) (Math.random()*getHeight()));
        }        
        for (int i = 0; i < numberOfAnts; i++) {
            addObject(new MyAnt(), (int) (Math.random()*getWidth()), (int) (Math.random()*getHeight())); 
        }

    }
    
    public void act(){
        round++;
        int sugar = getObjects(Sugar.class).size();
        int ants = getObjects(MyAnt.class).size();
        
        panel.setProgress(run, round);
        
        if (sugar == 0 || ants == 0 || round > 20000) {
            // Simulation done
            
            String won = sugar == 0 ? "Ants" : "Spider";
            System.out.println("Run " + run + ". " + won + " won in   " + round + " rounds");
            int score = (sugar == 0 ? round : 50000 - round);
            stat.add(score);
            nextRun();
        }            
    }
    
    private void submitScore(){
        if (stat.size() == 0){
            System.err.println("Kunne ikke indsende score da der ikke er målt nogen");
            return;
        }
        
        String source = readFile();
        if (source.equals("")) return;
        int sum = 0;
        for (int i : stat) sum = sum + i;
        String data = null;
        String score = ""+sum*1.0/stat.size() ;
        try {
            data = URLEncoder.encode("creator", "UTF-8") + "=" + URLEncoder.encode(creator, "UTF-8");
            data += "&" + URLEncoder.encode("score", "UTF-8") + "=" + URLEncoder.encode(score , "UTF-8");
            data += "&" + URLEncoder.encode("source", "UTF-8") + "=" + URLEncoder.encode(source , "UTF-8");
        } catch (Exception e){
            System.err.println("Fejl opstod i kodningen af data");
        }
        
        String reply = post("http://cs.au.dk/~mic/dIntProg/e12/ant.php", data);
        System.out.println(reply);
        
    }
    
    private String readFile(){
        String filename = "MyAnt.java";
        System.out.println("Løser fil: " + filename);
        try {
            BufferedReader rd = new BufferedReader(new FileReader(filename));
            StringBuffer read = new StringBuffer();
            while (rd.ready()){
                read.append(rd.readLine()+"\n");
            }
            return read.toString();
        } catch (Exception e){
            System.err.println("Kunne ikke løse "+ filename);
            return "";
        }
    }
        
    private String post(String url, String data)  {
        try {
            URL u = new URL(url);
            URLConnection c = u.openConnection();

            c.setDoOutput(true);
            if (c instanceof HttpURLConnection) {
                ((HttpURLConnection)c).setRequestMethod("POST");
            }

            OutputStreamWriter out = new OutputStreamWriter(c.getOutputStream());

            out.write(data, 0, data.length());

            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));

            StringBuffer reply = new StringBuffer();
            String s = null;
            while ((s = in.readLine()) != null) {
                reply.append(s+"\n");
            }
            in.close();
            return reply.toString();
            
        } catch (Exception e){
            System.err.println("Kunne ikke indsende score. Er du på nettet?");
            return "";
        }
    }
    


}
