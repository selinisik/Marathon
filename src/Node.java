import java.util.HashMap;

public class Node implements Comparable<Node> {

	String name;
	//used in dijkstra
	HashMap<Node, Integer> connectedNodes;
	// used in Mst
	HashMap<Node, Integer> connectedFlags;
	private int distance;
	private boolean visited;
	private boolean inMst;

	public Node(String name) {
		this.name = name;
		this.connectedNodes = new HashMap<>();
		this.connectedFlags = new HashMap<>();
		this.distance = Integer.MAX_VALUE;
		this.visited = false;
		this.inMst = false;

	}

	public void add(Node node, int weight) {
		connectedNodes.put(node, weight);
	}

	public void addFlag(Node node, int weight) {
		connectedFlags.put(node, weight);
	}

	public int compareTo(Node otherNode) {
		return this.distance - otherNode.distance;
	}

	public int getDistance() {
		return this.distance;
	}

	public void setDistance(int dist) {
		this.distance = dist;
	}

	public boolean getVisited() {
		return this.visited;
	}

	public void setVisited(boolean val) {
		this.visited = val;

	}

	public boolean getInMst() {
		return this.inMst;
	}

	public void setInMst(boolean b) {
		this.inMst = b;
	}
}