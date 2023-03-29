import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SuccessTest {

    int LOOPS = 3;
    double MINIMUM_SCORE = 0.3;

    /*
     * Test to verify that the number of successful queries is satisfying.
     */
    @Test
    void successTest() throws IOException {

        String[] args1 = {"Networks/network.txt", "15.00", "0.5", "0.0001", "400"};

        ArrayList<Double> runResults = new ArrayList<>();

        for (int i = 0; i < LOOPS; i++) {
            Query.successCounter = 0;
            Query.queryCounter = 0;

            Simulation.main(args1);
            if (Query.queryCounter!=0) {
                runResults.add( (double)Query.successCounter/Query.queryCounter);
            } else {
                runResults.add(0.00);
            }
        }





        System.out.println("---------TEST COMPLETE----------");
        System.out.println("Number of runs     : " + LOOPS);
        System.out.println("Highest score      : " + String.format("%.2f", Collections.max(runResults)*100 ) + "%");
        System.out.println("Lowest score       : " + String.format("%.2f", Collections.min(runResults)*100 ) + "%");
        System.out.println("");
        System.out.println("Average score      : " + String.format("%.2f", average(runResults)*100 ) + "%" );
        System.out.println("");
        System.out.println("-------------------------------");

        assertTrue(average(runResults)>MINIMUM_SCORE);
    }


    private Double average(ArrayList<Double> a){
        double sum = 0;

        for(Double d : a){
            sum = sum + d;
        }
        if (a.size() != 0) {
            return sum / a.size();
        }

        return 0.0;
    }


}
