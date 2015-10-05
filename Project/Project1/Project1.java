import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

public class Project1 {
    private int m;
    private int n;
    private WeightedQuickUnionUF qu;
    private int[][] grid;
    private ArrayList<Point> connections;
    
    /**
     * initializes UnionFind structure, grid and connection list
     * @param m
     * @param n
     */
    public Project1(int m, int n) {   //Building constructor
        this.m = m;
        this.n = n;
        this.grid = new int[m][n];
        this.qu = new WeightedQuickUnionUF(m * n);
        this.connections = new ArrayList<Point>(m * n);
    }
    
    /**
     * Reads input from user (pair of connections presented as points), 
     * store the input in a list  
     */
    public void read_input() {
        int row;
        int col;
        //System.out.print("Enter number of pairs of connections: ");
        try {
            int connectionNum = StdIn.readInt();
            for (int i = 0; i < connectionNum * 2; i ++) {
                row = StdIn.readInt();
                col = StdIn.readInt();
                connections.add(new Point(row, col));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * converts point into an integer
     * @param p
     * @return
     */
    
    public int map(Point p) {  
    // map the point by figuring out the position of this point
        int x = (int)p.getX();
        int y = (int)p.getY();
        return n * x + y;
    }
    
    /***
      * converts integer into a point
      * @param i
      * @return
      */
    public  Point unmap(int i) {
        //unmap the integer that represent the point
        int x = i / n;
        int y = i % n;
        Point a = new Point(x, y);
        return a;
    }
    
    /***
      * scans connections and populates UnionFind structure
      */
    public void process_connections() {
        try {
            //int [] mapped = new int[connections.size()];
            //connected the point that read from the inout
            for (int i = 0; i < connections.size() - 1; i++) {
                Point p = connections.get(i);
                Point q = connections.get(++i);
                if (is_adjacent(p, q)) {
                    if (qu.connected(map(p), map(q))) continue;
                    qu.union(map(p), map(q));
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * retrieve the connected sets from the UnionFind structure
     * @return connected sets
     */
    public ArrayList<Point> retrieve_connected_sets() {
        //figutre out the points that are in one set
        ArrayList<Point> mappedPoint = new ArrayList<Point>();
        int [] mapped = qu.getParent();
        try {
            int count = 0;
            int first = mapped[0];
            mappedPoint.add(unmap(first));
            for (int i = 0; i < mapped.length; i++) {
                count = 0;
                for (int j = 0; j < mappedPoint.size(); j++) {
                    if (mapped[i] != map(mappedPoint.get(j))) {
                        count++;
                    }
                }
                if (count == mappedPoint.size())
                    mappedPoint.add(unmap(mapped[i]));
            }
            
            int [] pointCount = new int[mappedPoint.size()];
            for (int k = 0; k < pointCount.length; k++)
                pointCount[k] = 0;
            for (int a = 0; a < mappedPoint.size(); a++) {
                for (int b = 0; b < mapped.length; b++) {
                    if (mapped[b] == map(mappedPoint.get(a)))
                        pointCount[a]++;
                    else continue;
                }
            }
            for (int c = 0; c < mappedPoint.size(); c++) {
                System.out.println("Set (" + (int)mappedPoint.get(c).getX() + 
                                   "," + (int)mappedPoint.get(c).getY() + 
                                   ") with size " + pointCount[c]);
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return mappedPoint;
    }
    
    /**
     * Tests whether two Cells are connected in the grid
     * @param p1
     * @param p2
     * @return
     */
    public boolean is_adjacent(Point p1, Point p2) {
        //figure out if the two point is adjacent to each other
        int xDistance = (int)Math.abs(p1.getX() - p2.getX());
        int yDistance = (int)Math.abs(p1.getY() - p2.getY());
        if ((int)p1.getX() == (int)p2.getX()) {
            if (yDistance == 1)
                return true;
            else return false;
        }
        else if ((int)p1.getY() == (int)p2.getY()) {
            if (xDistance == 1)
                return true;
            else return false;
        } 
        else return false;
    }
    
    /**
     * outputs the boundaries and size of each connected set
     * @param sets
     */
    public void output_boundaries_size(ArrayList<Point> sets) {
        //create two int array that stores x and y value seperately
        //find the boundaries of the set of points 
        int [] xRange = new int[connections.size()];
        int [] yRange = new int[connections.size()];
        ArrayList<Point> pointArray = new ArrayList<Point>();
        for (int i = 0; i < sets.size(); i++) {
            if (!pointArray.isEmpty()) 
                pointArray.clear();
            pointArray.add(sets.get(i));
            for (int j = 0; j < connections.size() - 1; j++) {
                Point p = connections.get(j);
                Point q = connections.get(++j);
                if (pointArray.contains(p) && !(pointArray.contains(q)))
                    pointArray.add(q);
                if (pointArray.contains(q) && !(pointArray.contains(p)))
                    pointArray.add(p);
            }
            //initialize two arrays
            for (int x = 0; x < pointArray.size(); x++) {
                xRange[x] = 0;
                yRange[x] = 0;
            }
            //store coresponding value into arrays
            for (int y = 0; y < pointArray.size(); y++) {
                xRange[y] = (int)pointArray.get(y).getX();
                yRange[y] = (int)pointArray.get(y).getY();
            }
            int length = pointArray.size();
            int swap = 0;
            for (int a = 0; a < ( length - 1 ); a++) {
                for (int b = 0; b < length - a - 1; b++) {
                    if (xRange[b] > xRange[b + 1]) {
                        swap = xRange[b];
                        xRange[b]   = xRange[b + 1];
                        xRange[b + 1] = swap;
                    }
                }
            }
            swap = 0;
            for (int c = 0; c < ( length - 1 ); c++) {
                for (int d = 0; d < length - c - 1; d++) {
                    if (yRange[d] > yRange[d + 1]) {
                        swap = yRange[d];
                        yRange[d]   = yRange[d + 1];
                        yRange[d + 1] = swap;
                    }
                }
            }
            System.out.println("Boundaries for (" + (int)sets.get(i).getX() + 
                               "," + (int)sets.get(i).getY() + 
                               ") are " + yRange[0] + "<=x<=" 
                                   + yRange[length - 1] + " and " + 
                               xRange[0] + "<=y<=" + xRange[length - 1]);
        }
    }
    
    public static void main(String args[]) {
        int m, n;
        Scanner input = new Scanner(System.in);
        //System.out.print("Enter size of grid(m n): ");
        m = StdIn.readInt();
        n = StdIn.readInt();
        
        Project1 project1 = new Project1(m, n);
        project1.read_input();
        project1.process_connections();
        ArrayList<Point> sets = project1.retrieve_connected_sets();
        project1.output_boundaries_size(sets);
    }
}