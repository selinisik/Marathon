import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class project4 {
	public static Node getOrCreate(HashMap<String, Node> map, String name) {
		if (map.containsKey(name))
			return map.get(name);
		Node node = new Node(name);
		map.put(name, node);
		return node;
	}

	public static void main(String args[]) throws FileNotFoundException {

		Scanner scanner = new Scanner(new File(args[0]));
		PrintStream output = new PrintStream(new File(args[1]));

		int numOfPoints = scanner.nextInt();
		scanner.nextInt();
		scanner.nextLine();
		String[] startAndEnd = scanner.nextLine().split(" ");
		String startPoint = startAndEnd[0];
		String endPoint = startAndEnd[1];
		String[] flags = scanner.nextLine().split(" ");
		HashMap<String, Node> nodes = new HashMap<>();

		Graph graph = new Graph();

		// #region Input
		for (int i = 0; i < numOfPoints; i++) {
			String[] line = scanner.nextLine().split(" ");

			Node currentNode = project4.getOrCreate(nodes, line[0]);
			for (int j = 1; j < line.length; j = j + 2) {
				int weight = Integer.parseInt(line[j + 1]);
				Node dest = project4.getOrCreate(nodes, line[j]);
				currentNode.add(dest, weight);
				if (!dest.connectedNodes.containsKey(currentNode)) {
					dest.add(currentNode, weight);
				}

			}
			graph.addNode(currentNode);
		}
		// #endregion

		// #region Dijkstra
		graph.findShortestPath(nodes.get(startPoint));

		if (nodes.get(endPoint).getDistance() == Integer.MAX_VALUE) {
			output.println(-1);

		} else {
			output.println(nodes.get(endPoint).getDistance());

		}
		// #endregion

		// #region Mst

		Graph flagGraph = new Graph();

		// Skip last flag as we'll already have all the necessary edges by then.
		for (int k = 0; k < flags.length; k++) {
			graph.restart();
			flagGraph.addNode(nodes.get(flags[k]));
			if (k == flags.length - 1) {
				break;
			}
			graph.findShortestPath(nodes.get(flags[k]));

			for (int j = k + 1; j < flags.length; j++) {
				int dist = nodes.get(flags[j]).getDistance();
				nodes.get(flags[k]).addFlag(nodes.get(flags[j]), dist);
				if (!nodes.get(flags[j]).connectedFlags.containsKey(nodes.get(flags[k]))) {
					nodes.get(flags[j]).addFlag(nodes.get(flags[k]), dist);
				}
			}
		}
		flagGraph.restart();
		output.println(flagGraph.MST(nodes.get(flags[0])));

		// #endregion
	}

}