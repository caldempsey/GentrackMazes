package sys;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * <p>The GentrackMaze class provides topological information of a maze utilising the given information provided by a GenTrac file.
 * The GentrackMaze class is a bespoke class developed within time constraints to submit the application (approximately an hour or two of work overall).
 * As such the class does not feature client friendly error handling, but makes meaningful assertions.
 * </p>
 * <p>
 * As a bespoke class (written at speed) the GenTrack maze class provides a constructor to populate topological information of the maze contained within the file format of a GenTrack maze file. To this end GentrackMaze file validation is not provided to maintain solution simplicity.
 * </p>
 * <p>
 * The approach further aims to improve solution speeds and simplify the requirements of the solution by caching only useful information to solving the maze (passages and their connections), which is performed via a depth first search.
 * This further aims to achieve looser coupling from the GentrackMaze file format by providing a maze solution using only the following details...
 * <ul>
 * <li>What passages exist (and what those passages have connections to).</li>
 * <li>Start and end coordinates (for start and end points of the maze).</li>
 * <li>Width and height of the maze.</li>
 * </ul>
 * The solution would thus be able to import a number of file formats (which would require their own import rule-set).
 * There are a number of ways this could be achieved, including detection on construction, a getInstance (static factory) class to return a new instance of the object, or explicitly providing details of the file.
 * The solution could then be modulated into a number of interfaces or abstract class hierarchies.
 * Notes have been made in JavaDoc methods highlighting where improvement and changes could be made.
 * </p>
 * <p>
 * It is possible to set the start and endpoints of the solution using the setter methods provided.
 * The maze can be printed using the solutionToString method (which assumes the solution has been solved).
 * </p>
 */
public class GentrackMaze {

    private Map<String, Passage> passageMap = new HashMap<>();
    // File input params.
    private int width;
    private int height;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    // Configuration parameters (in-case you want to make your maze look different).
    private final static char WALL = '#';
    private final static char START = 'S';
    private final static char END = 'E';
    private final static char PATH = 'X';
    private final static char PASSAGE = ' ';
    // Holds whether a solution exists on the manifold.
    private boolean solved;

    /**
     * The GentrackMaze constructor parses a valid GentrackMaze file into an a GentrackMaze object and contained passage set.
     *
     * @param file Input file.
     * @throws FileNotFoundException Throws a file not found execption if the file cannot be found.
     */
    public GentrackMaze(File file) throws FileNotFoundException {
        Scanner input = new Scanner(file);
        /* Load the parameters from the file format...
        <WIDTH> <HEIGHT>
         <START_X> <START_Y>
         <END_X> <END_Y>.*/
        String[] boundaries = input.nextLine().split(" ");
        this.width = Integer.parseInt(boundaries[0]);
        this.height = Integer.parseInt(boundaries[1]);
        // Set start coordinate and end coordinate passage.
        String[] startCoords = input.nextLine().split(" ");
        this.startX = Integer.parseInt(startCoords[0]);
        this.startY = Integer.parseInt(startCoords[1]);
        String[] endCoords = input.nextLine().split(" ");
        this.endX = Integer.parseInt(endCoords[0]);
        this.endY = Integer.parseInt(endCoords[1]);
        // Read passages and build their neighbors during the process in one fell swoop.
        // This is so we don't have to go back over the array twice which is the case if we store the whole file in memory and try to work with the individual character.
        // We can know the y axis (or position) in the maze by keeping track of what line (horizontal segment of the maze) we are on.
        // We can know the x axis (or position) in the maze by keeping track of what index the split string is on.
        // This is sufficient information to build a set of passages (after some validation).
        int yAxis = 0;
        String[] previousYSegment = null;
        while (input.hasNextLine() && input.hasNextInt()) {
            String[] ySegment = input.nextLine().split(" ");
            for (int xAxis = 0; xAxis < ySegment.length; xAxis++) {
                if (ySegment[xAxis].equals("0")) {
                    Passage passage = new Passage(xAxis, yAxis);
                    passageMap.put(passage.toString(), passage);
                    // If a passage exists directly above the passage we just added then we can connect to it.
                    // The code should be written such that it is type-safe to retrieve the requested Passage.
                    if (previousYSegment != null) {
                        if (previousYSegment[xAxis].equals(ySegment[xAxis])) {
                            // Assertions help debug sanity.
                            assert passageExists(xAxis, yAxis - 1);
                            Passage storedPassage = getPassage(xAxis, yAxis - 1);
                            passage.addNeighbor(storedPassage);
                            // We want this relationship to be symmetric (up and down traversal) so make sure we add it both ways.
                            storedPassage.addNeighbor(passage);
                        }
                    }
                    // If a passage exists directly to the left of a passage we just added then we can connect to it.
                    if (ySegment[xAxis - 1] != null && ySegment[xAxis - 1].equals(ySegment[xAxis])) {
                        assert passageExists(xAxis - 1, yAxis);
                        Passage storedPassage = getPassage(xAxis - 1, yAxis);
                        passage.addNeighbor(storedPassage);
                        // We want this relationship to be symmetric (left and right traversal) so make sure we add it both ways.
                        storedPassage.addNeighbor(passage);
                    }
                }
            }
            previousYSegment = ySegment;
            yAxis++;
        }
        // Close the scanner (we're done reading the file).
        input.close();
    }

    /**
     * getPassage is a helper method to simplify acquiring passages from the map of passages.
     * This could be better coded as a getInstance method in after modularising the solution to more classes (depending on how many variations of passage objects you desire).
     *
     * @param xCoord x coordinate of the passage.
     * @param yCoord y coordinate of the passage.
     * @return Returns the associated passage.
     */
    private Passage getPassage(int xCoord, int yCoord) {
        assert passageExists(xCoord, yCoord);
        return passageMap.get("Passage{" + "x=" + xCoord + ", y=" + yCoord + '}');
    }

    /**
     * Solves the maze from only file input to string. The method should not be ran without solving the maze first.
     * Future would will include making some minor adjustments so the tracePath method is agnostic as to whether the route from start to finish is solved.
     *
     * @return Returns a string containing the solution.
     */
    public String solutionToString() {
        assert this.solved;
        final List<Passage> routePassages = tracePath(startX, startY, endX, endY);
        // Begin filling rows with walls in the range of the maze width and height adding the right notation as needed.
        StringBuilder outString = new StringBuilder();
        for (int yCoord = 0; yCoord < height; yCoord++) {
            for (int xCoord = 0; xCoord < width; xCoord++) {
                if (passageExists(xCoord, yCoord)) {
                    if (routePassages.contains(getPassage(xCoord, yCoord))) {
                        if (xCoord == startX && yCoord == startY) {
                            outString.append(START);
                        } else if (xCoord == endX && yCoord == endY) {
                            outString.append(END);
                        } else {
                            outString.append(PATH);
                        }
                    } else {
                        outString.append(PASSAGE);
                    }
                    // If not a passage then must be a wall.
                } else outString.append(WALL);
            }
            // New line (end of X).
            outString.append("\n");
        }
        return outString.toString();

    }

    /**
     * The string representation of the maze (does not include a solution).
     *
     * @return Returns the string representation of the maze in the format GentrackMaze{
     */
    @Override
    public String toString() {
        return "GentrackMaze{" +
                "width=" + width +
                ", height=" + height +
                ", startX=" + startX +
                ", startY=" + startY +
                ", endX=" + endX +
                ", endY=" + endY +
                ", maze=" + passageMap.toString() +
                '}';
    }

    /**
     * Solves the maze using input parameters. Flags the maze as solved if successful.
     * The maze is solved by performing a bespoke depth first search through the object nodes and neighbours existing in memory.
     * To preserve simplicity an overloaded method has not been provided.
     * Future work could include creating a new set of interfaces providing details of each kinds of algorithm (perhaps providing an Algorithm class which provides meaningful functionality).
     * * The method could be overloaded to provide start and end coordinates as an input parameter or by object handling.
     *
     * @return Returns true if the maze has been solved, otherwise returns false.
     */
    public boolean solve() {
        assert passageExists(startX, startY);
        assert passageExists(endX, endY);
        Passage start = getPassage(startX, startY);
        Passage end = getPassage(endX, endY);
        // Begin a depth first search to solve the maze.
        // Get the start node.
        // Stack for backtracking.
        // New stack of unseen passages.
        Stack<Passage> unseenPassages = new Stack<>();
        // Add to the stack the first passage.
        unseenPassages.push(start);
        // New set of seen passages which will define what we have visited..
        Set<Passage> seenPassages = new HashSet<>();
        // While the unseen passages is not empty.
        while (!unseenPassages.isEmpty()) {
            // Assume no knowledge of the next passage.
            boolean routeEnd = true;
            Passage currentPassage = unseenPassages.pop();
            // Get neighbours from top of the stack.
            Set<Passage> neighbors = currentPassage.getNeighbors();
            // We know that we haven't reached the end of a route if there are neighbours unseen.
            if (!seenPassages.containsAll(neighbors)) {
                routeEnd = false;
            }
            // Append the current passage to seenPassages (this must take place before we check the neighbours).
            seenPassages.add(currentPassage);
            // If this is not the end of a route investigate whether passages in the route have been seen.
            // If passages in the route have been seen then do not add to the stack, otherwise add to the stack.
            if (!routeEnd) {
                for (Passage passageNeighbour : neighbors) {
                    if (!seenPassages.contains(passageNeighbour)) {
                        //If this is the passage we need to get to then we need to keep track of that object.
                        //We achieve this by assigning the object knowledge of what route was used to get there.
                        passageNeighbour.setSign(currentPassage);
                        if (passageNeighbour == end) {
                            // If we reach the end then we have completed the search and can backtrack.
                            this.solved = true;
                            return true;
                        }
                        // If we don't, then push the neighbour to the stack to be searched.
                        unseenPassages.push(passageNeighbour);
                    }
                }
            }
        }
        this.solved = false;
        return false;
    }

    /**
     * Traces passage signs to produce a complete list of routes from start to finish.
     *
     * @return Returns an empty route if the maze has not been solved. Returns a route if the maze has been solved.
     */
    private List<Passage> tracePath(int fromXCoord, int fromYCoord, int toXCoord, int toYCoord) {
        // Asserts input parameters are true.
        assert passageExists(fromXCoord, fromYCoord);
        assert passageExists(toXCoord, toYCoord);
        List<Passage> route = new LinkedList<>();
        if (!solved) {
            return route;
        }
        Passage startPassage = getPassage(fromXCoord, fromYCoord);
        Passage currPassage = getPassage(toXCoord, toYCoord);
        while (currPassage != startPassage) {
            assert currPassage.hasSign();
            // Add the current passage to the front of the list.
            route.add(0, currPassage);
            currPassage = currPassage.getSign();
        }
        route.add(0, startPassage);
        return route;
    }

    /**
     * Identifies whether the package exists.
     *
     * @param xCoord The x coordinate of the package.
     * @param yCoord The y coordinate of the package.
     * @return Returns whether the package exists.
     */
    private boolean passageExists(int xCoord, int yCoord) {
        return passageMap.containsKey("Passage{" + "x=" + xCoord + ", y=" + yCoord + '}');
    }
}