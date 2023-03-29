import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AgentTest {

    @Test
    void preferredMoveTest() {
        Node node1 = new Node(new Position(0, 0));
        Node node2 = new Node(new Position(1,0));
        Node prevNode = new Node(new Position(0,1));
        node1.addNeighbour(prevNode);
        node1.addNeighbour(node2);
        ArrayList<Node> nodes = node1.getNeighbours();
        assertFalse(nodes.size() == 0);
        Collections.shuffle(nodes);
        nodes.remove(prevNode);
        assertTrue(nodes.size() == 1);
        nodes.add(nodes.size(), prevNode);
        assertTrue(nodes.get(nodes.size()-1) == prevNode);
    }

    @Test
    void spreadRumorsTest() {
        Map<Integer, Integer> info = new HashMap<>();
        Map<Integer, Integer> events = new HashMap<>();
        events.put(4, 4);
        events.put(5, 5);
        events.put(6, 6);
        Node node = new Node(new Position(1, 1));
        //node.up
    }

    @Test
     void readNodeTest(){
        Map<Integer, Integer> eventList1 = new HashMap<>();
        Map<Integer, Integer> eventList2 = new HashMap<>();

        Node currentNode = new Node(new Position(1,1));
        Node prevNode = new Node(new Position(0,1));
        eventList1.put(1, 3);
        eventList2.put(2, 4);

        currentNode.addEventInfoToNode(1,prevNode, 3 );

        for (Map.Entry<Integer, Integer> entry :
                currentNode.getDistanceMap().entrySet()) {
            Integer ID = entry.getKey();
            Integer distance = entry.getValue();
            assertTrue(eventList1.containsKey(ID));
            assertFalse(eventList2.containsKey(ID));
        }
    }

}