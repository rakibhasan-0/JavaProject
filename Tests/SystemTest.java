import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;


public class SystemTest {

    int LOOPS = 4;

    /*
     * Test to verify that if no events are created, there are no successful queries in the network.
     */
    @Test
    void noEventTest() throws IOException {

        int success = 0;

        String[] args1 = {"Networks/network.txt", "15.00", "0.05", "0", "400"};

        for (int i = 0; i < LOOPS; i++) {
            Query.successCounter = 0;
            Simulation.main(args1);
            success = success + Query.successCounter;
        }

        assertEquals(0,success);

    }

    /*
     * Test to make sure that if we send queries more often, we should also find more events.
     */
    @Test
    void doubleQueryTest() throws IOException {

        String[] args1 = {"Networks/network.txt", "15.00", "0.5", "0.0001", "200"};
        String[] args2 = {"Networks/network.txt", "15.00", "0.5", "0.0001", "400"};

        int success1;
        int success2;

        Query.successCounter = 0;
        for (int i = 0; i < LOOPS; i++) {
            Simulation.main(args1);
        }
        success1 = Query.successCounter;

        Query.successCounter = 0;
        for (int i = 0; i < LOOPS; i++) {
            Simulation.main(args2);
        }
        success2 = Query.successCounter;

        assertTrue(success1 > success2);

    }

    /*
     * Test to verify that higher agent probability lead to more events found.
     * (i.e. there will be more paths to events)
     */
    @Test
    void doubleAgentProbabilityTest() throws IOException {

        int success1;
        int success2;

        String[] args1 = {"Networks/network.txt", "15.00", "0.75", "0.0001", "400"};
        String[] args2 = {"Networks/network.txt", "15.00", "0.25", "0.0001", "400"};

        Query.successCounter = 0;
        for (int i = 0; i < LOOPS; i++) {
            Simulation.main(args1);
        }
        success1 = Query.successCounter;

        Query.successCounter = 0;
        for (int i = 0; i < LOOPS; i++) {
            Simulation.main(args2);
        }
        success2 = Query.successCounter - success1;

        assertTrue(success1 > success2);

    }



}
