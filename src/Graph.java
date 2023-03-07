
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {

	public static int visitedNodes = 0;

	private HashSet<Node> nodes;
	private PriorityQueue<Node> pq;

	public Graph() {

		this.pq = new PriorityQueue<Node>();
		this.nodes = new HashSet<Node>();

	}

	public void addNode(Node node) {
		nodes.add(node);
	}

	public void restart() {
		pq.clear();
		for (Node node : nodes) {
			node.setDistance(Integer.MAX_VALUE);
			node.setVisited(false);
		}
	}
	//Dijkstra algorithm with priority queue
	public void findShortestPath(Node begin) {
		//since vidited nodes are static initialize them to zero in the beginning of program
		visitedNodes = 0;

		begin.setDistance(0);

		pq.add(begin);

		int size = nodes.size();
		// loops until number of visited nodes reaches the size of all nodes
		while (visitedNodes != size && !pq.isEmpty()) {

			Node minDistNode = pq.poll();

			if (minDistNode.getVisited())
				continue;

			minDistNode.setVisited(true);
			visitedNodes++;
			//checks all edges of a node to find min distance
			for (Map.Entry<Node, Integer> entry : minDistNode.connectedNodes.entrySet()) {

				Node adjNode = entry.getKey();

				int dist = entry.getValue();
				int minDistDist = minDistNode.getDistance();

				if (minDistDist + dist < adjNode.getDistance()) {
					adjNode.setDistance(minDistDist + dist);
					pq.add(adjNode);
				}
			}
		}
	}
	// Prims algorithm
	public int MST(Node start) {
		// stores all of nodes not in mst to find out if the tree is finished
		HashSet<Node> notInMst = new HashSet<>(nodes);
		int totalDist = 0;
		start.setDistance(0);
		// loops until notInMst is empty
		while (!notInMst.isEmpty()) {
			Node minNode = null;
			for (Node node : notInMst) {
				if (minNode == null) {
					minNode = node;
				}
				if (node.getDistance() < minNode.getDistance()) {
					minNode = node;
				}
			}

			if (minNode.getDistance() == Integer.MAX_VALUE) {
				return -1;
			}

			totalDist += minNode.getDistance();
			notInMst.remove(minNode);
			minNode.setDistance(0);
			
			for (Map.Entry<Node, Integer> entry : minNode.connectedFlags.entrySet()) {
				Node key = entry.getKey();
				int dist = entry.getValue();
				if (dist < key.getDistance()) {
					key.setDistance(dist);
				}
			}

		}
		return totalDist;

	}

}
