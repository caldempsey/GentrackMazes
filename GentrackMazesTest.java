import org.junit.jupiter.api.Test;
import sys.GentrackMaze;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import static junit.framework.TestCase.assertTrue;

/**
 * A helper class to help with tests to simplify code execution for an invigilator.
 * You can easily test your own GentrackMaze files by using the testCustomInput() method.
 * You can test provided files by changing the customInput field.
 */
class GentrackMazesTest {
    private static String CUSTOM_INPUT = "bin/large_input.txt";

    @Test
    void testCustomInput() throws FileNotFoundException, URISyntaxException {
        GentrackMaze input = new GentrackMaze(new File(getClass().getClassLoader().getResource(CUSTOM_INPUT).toURI()));
        assertTrue(input.solve());
        System.out.println(input.solutionToString());
    }

    @Test
    void testInput() throws FileNotFoundException, URISyntaxException {
        GentrackMaze input = new GentrackMaze(new File(getClass().getClassLoader().getResource("bin/input.txt").toURI()));
        assertTrue(input.solve());
        System.out.println(input.solutionToString());
    }

    @Test
    void testMediumInput() throws FileNotFoundException, URISyntaxException {
        GentrackMaze input = new GentrackMaze(new File(getClass().getClassLoader().getResource("bin/medium_input.txt").toURI()));
        assertTrue(input.solve());
        System.out.println(input.solutionToString());
    }

    @Test
    void testSmallInput() throws FileNotFoundException, URISyntaxException {
        GentrackMaze input = new GentrackMaze(new File(getClass().getClassLoader().getResource("bin/small.txt").toURI()));
        assertTrue(input.solve());
        System.out.println(input.solutionToString());
    }

    @Test
    void testSparseMediumInput() throws FileNotFoundException, URISyntaxException {
        GentrackMaze input = new GentrackMaze(new File(getClass().getClassLoader().getResource("bin/sparse_medium.txt").toURI()));
        assertTrue(input.solve());
        System.out.println(input.solutionToString());
    }

    @Test
    void testLargeInput() throws FileNotFoundException, URISyntaxException {
        GentrackMaze input = new GentrackMaze(new File(getClass().getClassLoader().getResource("bin/large_input.txt").toURI()));
        assertTrue(input.solve());
        System.out.println(input.solutionToString());
    }
}

