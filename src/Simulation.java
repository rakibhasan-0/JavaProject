import java.io.File;
import java.io.IOException;
import java.util.Scanner;


/**
 * To run this simulation, enter parameters "filePath, Maxdistance, probAgent, probEvent, querytimestep "
 */
public class Simulation {
    static int TIMESTEPS = 10_000;

    static double MAX_DISTANCE;
    static double PROB_AGENT;
    static double PROB_EVENT;
    static int QUERY_TIME;
    static int AGENT_STEPS = 50;
    static int QUERY_STEPS = 45;


    public static void main(String[] args) throws IOException {

        // Take in parameters
        if (args.length == 5){
            MAX_DISTANCE = Double.parseDouble(String.valueOf(args[1]));
            PROB_AGENT = Double.parseDouble(String.valueOf(args[2]));
            PROB_EVENT = Double.parseDouble(String.valueOf(args[3]));
            QUERY_TIME = Integer.parseInt(String.valueOf(args[4]));
        } else {
            throw new IOException("Wrong number of program arguments.");
        }


        Scanner in = new Scanner(new File(args[0]));

        Network network = new Network(in, PROB_AGENT, PROB_EVENT, MAX_DISTANCE, QUERY_TIME, AGENT_STEPS, QUERY_STEPS);



        for(int i = 0; i < TIMESTEPS; i++) {
            network.updateNetwork();
        }

        // Somewhat "hardcoded" but mainly for testing purposes.



        System.out.println("---------RUN COMPLETE----------");
        System.out.println("Number of queries  : " + Query.queryCounter);
        System.out.println("Successful queries : " + Query.successCounter);
        System.out.println("");
        System.out.println("Successrate        : " + String.format("%.2f", results() ) +"%" );
        System.out.println("-------------------------------");
    }

    public static double results(){
        return (double)Query.successCounter/Query.queryCounter * 100;
    }

}


