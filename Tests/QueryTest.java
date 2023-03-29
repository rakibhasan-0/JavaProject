import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class QueryTest {
    @Test
    void preferredMoveTest() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 0);
        Position pos3 = new Position(2, 0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);
        Node node3 = new Node(pos3);

        node1.addNeighbour(node2);
        node2.addNeighbour(node3);

        Event e = new Event(1, 1, node3);
        Query q = new Query(node1, 1, 50);

        assertEquals(q.preferredMove().size(), 2);
    }

    @Test
    void wantsToDieTest() {
        Position pos1 = new Position(0, 0);
        Position pos2 = new Position(1, 0);

        Node node1 = new Node(pos1);
        Node node2 = new Node(pos2);

        node1.addNeighbour(node2);

        Query q = new Query(node1, 1, 5);

        for (int i = 0; i < 5; i++) {
            assertFalse(q.wantsToDie());
        }
        assertTrue(q.wantsToDie());
    }

    @Test
    void checkUpdatingQueries() throws IOException {
        Scanner scan = new Scanner(new File("Networks/network.txt"));
        Network network = new Network(scan, 0.5, 0.0001, 15, 100, 50, 45);
        // we let network to update for 100 times, after 100 times we should've get qurery nodes.
        for (int i = 0; i < 500; i++) {
            network.updateNetwork();
        }

        // we will save these query node's event number in a list.
        ArrayList<Integer> eventsId = new ArrayList<>();
        HashMap<Query, Integer> queryList = network.getQueryNode();

        //we will loop through the hash map to get each query and its id numbers.
        for (Map.Entry<Query, Integer> entry : queryList.entrySet()) {
            Query q = entry.getKey();
            int val = entry.getValue();
            //  System.out.println("list1::"+val);
            eventsId.add(val);
        }
        // we will remove all elements from the hash map.
        network.removeElementfromTheQuery();

        // we will update network for another 100 times so that new queries will be created in the netowork.
        for (int j = 0; j < 500; j++) {
            network.updateNetwork();
        }

        // each queries event id number will be saved in a list.
        ArrayList<Integer> temp = new ArrayList<>();
        HashMap<Query, Integer> tempqueryList = network.getQueryNode();
        //System.out.println("Size = "+tempqueryList.size());

        for (Map.Entry<Query, Integer> entry : tempqueryList.entrySet()) {
            Query q = entry.getKey();
            int val = entry.getValue();
            //   System.out.println("list2::"+val);
            temp.add(val);
        }

        // since, we know that both lists size is 4. Both lists elements should not be identical at same index.
        int num = 0;
        for (int i = 0; i < 4; i++) {
            if (temp.get(i).equals(eventsId.get(i))) {
                num++;
            }
        }
        assertNotEquals(4, num);
    }
}