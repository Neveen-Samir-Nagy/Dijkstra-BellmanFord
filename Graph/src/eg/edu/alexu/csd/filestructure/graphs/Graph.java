package eg.edu.alexu.csd.filestructure.graphs;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.management.RuntimeErrorException;

import javafx.util.Pair;

public class Graph implements IGraph {

	int[][] graph;
	int vertices = 0;
	int size = 0;
	int edges = 0;
	int[][] predecessor;

	@SuppressWarnings("resource")
	@Override
	public void readGraph(File file) {
		// TODO Auto-generated method stub
		if (file == null || !file.exists()) {
			throw new RuntimeErrorException(null);
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			String first = "^([0-9][0-9]*|1[0-9]+)+(\\s)+([0-9][0-9]*|1[0-9]+)+$";
			String second = "^([0-9][0-9]*|1[0-9]+)+(\\s)+([0-9][0-9]*|1[0-9]+)+(\\s)+([-+])?+([0-9][0-9]*|1[0-9]+)+$";
			Pattern rFirst = Pattern.compile(first);
			Pattern rSecond = Pattern.compile(second);
			try {
				while ((line = reader.readLine()) != null) {
					Matcher m1 = rFirst.matcher(line);
					Matcher m2 = rSecond.matcher(line);
					if (m1.find()) {
						vertices = Integer.valueOf(m1.group(1));
						edges = Integer.valueOf(m1.group(3));
						graph = new int[vertices][vertices];
						for (int i = 0; i < vertices; i++) {
							for (int j = 0; j < vertices; j++) {
								graph[i][j] = 0;
							}
						}
					} else if (m2.find()) {
						size++;
						int weight = Integer.valueOf(m2.group(6));
						if (m2.group(5) != null) {
							weight = -1 * weight;
						}
						graph[Integer.valueOf(m2.group(1))][Integer.valueOf(m2.group(3))] = weight;
					}
				}
				if (size < edges) {
					throw new RuntimeErrorException(null);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return edges;
	}

	@Override
	public ArrayList<Integer> getVertices() {
		// TODO Auto-generated method stub
		ArrayList<Integer> vertex = new ArrayList<Integer>();
		for (int i = 0; i < vertices; i++) {
			vertex.add(i);
		}
		return vertex;
	}

	@Override
	public ArrayList<Integer> getNeighbors(int v) {
		// TODO Auto-generated method stub
		ArrayList<Integer> adjacent = new ArrayList<Integer>();
		if (v >= vertices || v < 0) {
			throw new RuntimeErrorException(null);
		}
		for (int i = 0; i < vertices; i++) {
			if (graph[v][i] != 0) {
				adjacent.add(i);
			}
		}
		return adjacent;
	}

	@Override
	public void runDijkstra(int src, int[] distances) {
		// TODO Auto-generated method stub
		predecessor = new int[vertices][2];
		Arrays.fill(distances, Integer.MAX_VALUE / 2);
		distances[src] = 0;
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		ArrayList<Integer> vertice = getVertices();
		for (int i = 0; i < vertice.size(); i++) {
			pq.add(vertice.get(i));
		}
		pq.add(src);
		while (!pq.isEmpty()) {
			int vertex = pq.poll();
			ArrayList<Integer> adjacent = new ArrayList<Integer>();
			adjacent = getNeighbors(vertex);
			for (int i = 0; i < adjacent.size(); i++) {
				int distance = distances[vertex] + graph[vertex][adjacent.get(i)];
				if (distance < distances[adjacent.get(i)]) {
					pq.remove(adjacent.get(i));
					distances[adjacent.get(i)] = distance;
					predecessor[adjacent.get(i)][0] = distance;
					predecessor[adjacent.get(i)][1] = adjacent.get(i);
					pq.add(adjacent.get(i));
				}
			}
		}
	}

	@Override
	public ArrayList<Integer> getDijkstraProcessedOrder() {
		// TODO Auto-generated method stub
		ArrayList<Integer> pre = new ArrayList<Integer>();
		for (int i = 0; i < vertices; i++) {
			for (int j = i + 1; j < vertices; j++) {
				if (predecessor[i][0] > predecessor[j][0]) {
					int[] temp = predecessor[j];
					predecessor[j] = predecessor[i];
					predecessor[i] = temp;
				}
			}
			pre.add(predecessor[i][1]);
		}
		return pre;
	}

	@Override
	public boolean runBellmanFord(int src, int[] distances) {
		Arrays.fill(distances, Integer.MAX_VALUE / 2);
		distances[src] = 0;
		ArrayList<Pair<Integer, Integer>> edges = new ArrayList<Pair<Integer, Integer>>();
		for (int i = 0; i < vertices; i++) {
			for (int j = 0; j < vertices; j++) {
				if (graph[i][j] != 0) {
					Pair<Integer, Integer> p = new Pair<Integer, Integer>(i, j);
					edges.add(p);
				}
			}
		}
		for (int i = 1; i < vertices; i++) {
			for (int j = 0; j < edges.size(); j++) {
				Pair<Integer, Integer> p = edges.get(j);
				if (distances[(int) p.getKey()]
						+ graph[(int) p.getKey()][(int) p.getValue()] < distances[(int) p.getValue()]) {
					distances[(int) p.getValue()] = distances[(int) p.getKey()]
							+ graph[(int) p.getKey()][(int) p.getValue()];
				}
			}
		}
		for (int j = 0; j < edges.size(); j++) {
			Pair<Integer, Integer> p = edges.get(j);
			if (distances[(int) p.getKey()]
					+ graph[(int) p.getKey()][(int) p.getValue()] < distances[(int) p.getValue()]) {
				return false;
			}
		}
		return true;
	}

}
