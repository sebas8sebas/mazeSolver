
import java.awt.image.BufferedImage;
import java.util.*;


public class Maze {

    private MazeCode[][] grid;

    /**
     * Start and endpoints
     */
    private Cord start;
    private Cord end;

    private int width;
    private int height;
    private BufferedImage image;

    /**
     * Statistics for average color for endpoints
     */
    private IntSummaryStatistics avgR;
    private IntSummaryStatistics avgG;
    private IntSummaryStatistics avgB;

    /**
     * Path default color
     */
    private int[] defaultSolutionColor;



    /**
     * Constructor
     * @param image buffered image object
     * @throws Exception
     */
    public Maze(BufferedImage image) throws Exception {
        this.image = image;

        width = image.getWidth();
        height = image.getHeight();

        avgR = new IntSummaryStatistics();
        avgG = new IntSummaryStatistics();
        avgB = new IntSummaryStatistics();

        grid = new MazeCode[height][width];

        generateGrid();

    }


    /**
     * Solves maze with specified color
     * @return buffered image with solution
     */
    public BufferedImage solve(int[] solutionColor) throws Exception {



        List<Cord> path = calculatePath();
        if (path==null){throw new Exception("No solution found");}


        for (Cord c:
             path) {
            grid[c.getY()][c.getX()] = MazeCode.PATH;
        }



        int rgbColor = RGButils.rgbArrayToInt(solutionColor);
        BufferedImage solution = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                MazeCode code = grid[y][x];
                if (code.equals(MazeCode.PATH)){
                    solution.setRGB(x, y, rgbColor);
                } else {
                    solution.setRGB(x, y, image.getRGB(x, y));
                }

            }
        }


        return solution;
    }


    /**
     * Solves maze with default color (the color of the endpoints)
     * @return buffered image with solution
     */
    public BufferedImage solve() throws Exception {
        return solve(defaultSolutionColor);
    }

    /**
     * Creates grid based on image's pixels
     * @throws Exception
     */
    private void generateGrid() throws Exception {


        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {



                int rgbInt = image.getRGB(x, y);
                int[] rgb = RGButils.rgbIntToArray(rgbInt);

                if (grid[y][x] != null && grid[y][x].equals(MazeCode.CLEAR)){
                    continue;
                }
                else if (RGButils.isBlack(rgb)){
                    grid[y][x] = MazeCode.WALL;
                }
                else if (RGButils.isWhite(rgb)){
                    grid[y][x] = MazeCode.CLEAR;
                } else {
                    if (start == null){
                        start = new Cord(x, y);
                        clearPointsAroundEndpoint(x, y);
                    } else if (end == null){
                        end = new Cord(x, y);
                        clearPointsAroundEndpoint(x, y);

                    } else {
                        throw new Exception("Only 2 endpoints are allowed");
                    }
                }

            }

        }

        if (start == null || end == null){throw new Exception("Bad endpoints");}

        defaultSolutionColor = new int[]{(int)avgR.getAverage(), (int)avgG.getAverage(), (int)avgB.getAverage()};

    }



    /**
     * Marks the coordinates around an endpoint  as Clear
     */
    private void clearPointsAroundEndpoint(int x, int y){
        if (x < 0 || x >= this.width || y < 0 || y >= this.height){return;}
        if (grid[y][x] == null){
            int rgb[] = RGButils.rgbIntToArray(image.getRGB(x, y));
            if (RGButils.isWhite(rgb) || RGButils.isBlack(rgb)){return;}
        }
        else if (grid[y][x].equals(MazeCode.CLEAR) || grid[y][x].equals(MazeCode.WALL)){return;}

        //image.setRGB(x, y, RGButils.rgbArrayToInt(new int[]{255, 255, 255}));
        grid[y][x] = MazeCode.CLEAR;

        //Save color data to make path color the average of endpoint color
        int[] rgbPoint = RGButils.rgbIntToArray(image.getRGB(x, y));
        avgR.accept(rgbPoint[0]);
        avgG.accept(rgbPoint[1]);
        avgB.accept(rgbPoint[2]);


        clearPointsAroundEndpoint(x + 1, y);
        clearPointsAroundEndpoint(x - 1, y);
        clearPointsAroundEndpoint(x, y - 1);
        clearPointsAroundEndpoint(x, y + 1);

    }


    /**
     * Gets solution path by breadth first search
     * Reference: https://www.hackerearth.com/practice/algorithms/graphs/breadth-first-search/tutorial/
     * @return linked list or null if no path is found
     */
    private List<Cord> calculatePath(){


        Queue<SearchNode> queue = new LinkedList<>();
        HashSet<Cord> visitedCords = new HashSet<>();

        LinkedList<Cord> startingPath = new LinkedList<>();
        startingPath.add(start);

        SearchNode startNode = new SearchNode(start, startingPath, getAdjacentCords(start));
        queue.add(startNode);
        visitedCords.add(startNode.cord);


        while(!queue.isEmpty()){

            SearchNode cur = queue.remove();

            if(cur.cord.equals(end)){
                return cur.path;
            }

            for (Cord c:
                 cur.adjacent) {
                if (!visitedCords.contains(c)){

                    LinkedList<Cord> newPath = new LinkedList<>(cur.path);
                    newPath.add(c);

                    queue.add(new SearchNode(c, newPath, getAdjacentCords(c)));
                    visitedCords.add(c);
                }
            }


        }

        return null;
    }


    /**
     * gets adjacent coordinates for a coordinate, does not count walls as adjacent
     * @param c
     * @return
     */
    private ArrayList<Cord> getAdjacentCords(Cord c){
        ArrayList<Cord>  adj = new ArrayList<>();

        adj.add(new Cord(c.getX() + 1, c.getY()));
        adj.add(new Cord(c.getX() - 1, c.getY()));
        adj.add(new Cord(c.getX(), c.getY() + 1));
        adj.add(new Cord(c.getX(), c.getY() - 1));


        adj.add(new Cord(c.getX() + 1, c.getY() + 1));
        adj.add(new Cord(c.getX() + 1, c.getY() - 1));
        adj.add(new Cord(c.getX() - 1, c.getY() + 1));
        adj.add(new Cord(c.getX() - 1, c.getY() - 1));


        ArrayList<Cord>  validAdj = new ArrayList<>();
        for (Cord cord:
             adj) {
            if (cord.getX() >= width || cord.getY() >= height || cord.getX() < 0 || cord.getY() < 0){continue;}
            if (grid[cord.getY()][cord.getX()].equals(MazeCode.WALL)){continue;}
            validAdj.add(cord);
        }

        return validAdj;

    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }





}


/**
 * Nodes used for breadth first search algorithms
 */
class SearchNode{

    List<Cord> path;
    List<Cord> adjacent;
    Cord cord;

    public SearchNode(Cord c, List<Cord> path, List<Cord> adjacent) {
        this.cord = c;
        this.path = path;
        this.adjacent = adjacent;
    }
}


/**
 * Maze code
 */
enum MazeCode {

    WALL, CLEAR, PATH;
}
