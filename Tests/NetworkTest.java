import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NetworkTest {


    @Test
    void calculateNeighbours() {
        Node node1 = new Node(new Position(0, 0));
        Node node2 = new Node(new Position(4, 5));
        Node node3 = new Node(new Position(2, 3));

        if (node1.getDistance(node2) < 4) {
            node1.addNeighbour(node2);
        }
        if (node1.getDistance(node3) < 4) {
            node1.addNeighbour(node3);
        }

        ArrayList<Node> neigh1 = node1.getNeighbours();

        assertTrue(neigh1.size() == 1);
        assertTrue(neigh1.contains(node3));
        assertFalse(neigh1.contains(node2));
    }

    @Test
    void createAgent() {
        Node node = new Node(new Position(1, 1));
        Event event = new Event(1, 400, node);
        Agent agent = new Agent(node, event, 50);
        node.receive(agent);
        assertFalse(node.isAvailable());
        assertFalse(agent.wantsToDie());
        for (int i = 1; i < 50; i++) {
            assertFalse(agent.wantsToDie());
        }
        assertTrue(agent.wantsToDie());
    }

    @Test
    void createQuery() {
        Node node = new Node(new Position(1, 1));
        Query query = new Query(node, 1, 45);
        node.receive(query);
        assertFalse(node.isAvailable());
        assertFalse(query.wantsToDie());
        for (int i = 0; i < 45; i++) {
            query.wantsToDie();
        }
        assertTrue(query.wantsToDie());
    }


    @Test
    void testWalkInNetwork() {
        ArrayList<Node> nodeList = new ArrayList<>();
        Node node1 = new Node(new Position(0, 0));
        Node node2 = new Node(new Position(1, 0));
        node1.addNeighbour(node2);
        node2.addNeighbour(node1);
        Node node3 = new Node(new Position(2, 0));
        node2.addNeighbour(node3);
        node3.addNeighbour(node2);
        Node node4 = new Node(new Position(3, 0));
        node3.addNeighbour(node4);
        node4.addNeighbour(node3);
        Node node5 = new Node(new Position(4, 0));
        node4.addNeighbour(node5);
        node5.addNeighbour(node4);
        Node node6 = new Node(new Position(5, 0));
        node5.addNeighbour(node6);
        node6.addNeighbour(node5);
        Event event1 = new Event(1, 8, node6);
        Agent agent1 = new Agent(node6, event1, 8);

        ArrayList<Node> nodes1 = agent1.preferredMove();
        agent1.moveAndShare(nodes1.get(0));
        assertEquals(agent1.getCurrentNode(),node5);
        nodes1 = agent1.preferredMove();
        agent1.moveAndShare(nodes1.get(0));
        assertEquals(agent1.getCurrentNode(),node4);
        nodes1 = agent1.preferredMove();
        agent1.moveAndShare(nodes1.get(0));
        nodes1 = agent1.preferredMove();
        agent1.moveAndShare(nodes1.get(0));
        nodes1 = agent1.preferredMove();
        agent1.moveAndShare(nodes1.get(0));
        agent1.moveAndShare(nodes1.get(0));
        assertEquals(agent1.getCurrentNode(),node1);

    }


    @Test
    void testQuerySearchRoute() {
        ArrayList<Node> nodeList = new ArrayList<>();
        Node node1 = new Node(new Position(0, 0));
        Node node11 = new Node(new Position(0, 1));
        Node node2 = new Node(new Position(1, 0));
        Node node12 = new Node(new Position(0, 1));
        node1.addNeighbour(node11);
        node2.addNeighbour(node12);
        node1.addNeighbour(node2);
        node2.addNeighbour(node1);
        Node node3 = new Node(new Position(2, 0));
        Node node13 = new Node(new Position(0, 1));
        node3.addNeighbour(node13);
        node2.addNeighbour(node3);
        node3.addNeighbour(node2);
        Node node4 = new Node(new Position(3, 0));
        Node node14 = new Node(new Position(0, 1));
        node4.addNeighbour(node14);
        node3.addNeighbour(node4);
        node4.addNeighbour(node3);
        Node node5 = new Node(new Position(4, 0));
        Node node15 = new Node(new Position(0, 1));
        node5.addNeighbour(node15);
        node4.addNeighbour(node5);
        node5.addNeighbour(node4);
        Node node6 = new Node(new Position(5, 0));
        Node node16 = new Node(new Position(0, 1));
        node6.addNeighbour(node16);
        node5.addNeighbour(node6);
        node6.addNeighbour(node5);
        Event event1 = new Event(1, 8, node6);
        Node node17 = new Node(new Position(0, 1));

        Query query1 = new Query(node1, 1, 8);
        node1.addEventInfoToNode(1, node6, 5);
        node2.addEventInfoToNode(1, node6, 4);
        node3.addEventInfoToNode(1, node6, 3);
        node4.addEventInfoToNode(1, node6, 2);
        node5.addEventInfoToNode(1, node6, 1);

        ArrayList<Node> prefNode = query1.preferredMove();



    }

}
