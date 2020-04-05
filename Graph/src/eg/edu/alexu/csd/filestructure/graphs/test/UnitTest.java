package eg.edu.alexu.csd.filestructure.graphs.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;

import eg.edu.alexu.csd.filestructure.graphs.IGraph;


public class UnitTest {

	private String[] testGraphs = {"graph_5_5.txt", "graph_50_25.txt", "graph_500_250.txt", "graph_1250_625.txt"};
	private int[] testGraphsSizes = new int[] { 11, 1045, 26217, 144583};
	private int[] testGraphsVertices = new int[] { 5, 50, 500, 1250};
	private int[] testGraphsNeighbors = new int[] { 3, 24, 76, 125};
	private final String testDir = "res\\";	
	private String solutionDir = testDir + "solution\\";
	private String dijkstraOrderDir = solutionDir + "dijkstra_order\\";
	
	
	public static Class<?> getSpecifications(){
		return IGraph.class;
	}
	
	@org.junit.Test(timeout = 2000)
	public void testInvalidGraph() {
		IGraph graph = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
		try {
			graph.readGraph(new File("" + System.currentTimeMillis()));
			fail("Accepted reading non existing file");
		} catch (Exception e) {
		}
		try {
			graph.readGraph(new File(testDir + "graph_incomplete.txt"));
			fail("Accepted invalid file with incomplete graph information");
		} catch (Exception e) {
		}
		try {
			graph.readGraph(new File(testDir + "graph_extra.txt"));
			fail("Accepted invalid file with inconsistent dimensions/edges");
		} catch (Exception e) {
		}
	}
	
	@org.junit.Test(timeout = 2000)
	public void testGraphSize() {
		for(int i=0; i<testGraphs.length; i++){
			IGraph graph = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
			graph.readGraph(new File(testDir + testGraphs[i]));
			assertEquals("Wrong graph size", testGraphsSizes[i], graph.size());
		}
	}
	
	@org.junit.Test(timeout = 2000)
	public void testGraphVertices() {
		for(int i=0; i<testGraphs.length; i++){
			IGraph graph = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
			graph.readGraph(new File(testDir + testGraphs[i]));
			assertEquals("Wrong vertices count", testGraphsVertices[i], graph.getVertices().size());
		}
	}
	
	@org.junit.Test(timeout = 2000)
	public void testGraphNeighbors() {
		for(int i=0; i<testGraphs.length; i++){
			IGraph graph = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
			graph.readGraph(new File(testDir + testGraphs[i]));
			assertEquals("Wrong vertices count", testGraphsNeighbors[i], graph.getNeighbors(0).size());
		}
	}
	
	@org.junit.Test(timeout = 2000)
	public void testNegativeEdges() {
		String testCaseFileName = "graph_negative_edges.txt";
		int src = 0;
		assertTrue(testGraphBellmanFordRunner(testCaseFileName, src));
	}

	@org.junit.Test(timeout = 2000)
	public void testNegativeCycle() {
		IGraph solver = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
		String testCaseFileName = "graph_negative_cycle.txt";
		File inputFile = new File(testDir + testCaseFileName);
		int n = GraphUtil.readGraphSize(inputFile);
		int[] distances = new int[n];
		int src = 0;
		solver.readGraph(inputFile);
		assertFalse(solver.runBellmanFord(src, distances));
	}

	@org.junit.Test(timeout = 2000)
	public void testDijkstra1() {
		assertTrue(testGraphDijkstraRunner(testGraphs[0], 0, true));
	}

	@org.junit.Test(timeout = 2000)
	public void testDijkstra2() {
		assertTrue(testGraphDijkstraRunner(testGraphs[1], 0, false));
	}

	@org.junit.Test(timeout = 2000)
	public void testDijkstra3() {
		assertTrue(testGraphDijkstraRunner(testGraphs[2], 0, false));
	}

	@org.junit.Test(timeout = 2000)
	public void testDijkstra4() {
		assertTrue(testGraphDijkstraRunner(testGraphs[3], 0, false));
	}

	@org.junit.Test(timeout = 7000)
	public void testBellmanFord1() {
		assertTrue(testGraphBellmanFordRunner(testGraphs[0], 0));
	}

	@org.junit.Test(timeout = 7000)
	public void testBellmanFord2() {
		assertTrue(testGraphBellmanFordRunner(testGraphs[1], 0));
	}

	@org.junit.Test(timeout = 7000)
	public void testBellmanFord3() {
		assertTrue(testGraphBellmanFordRunner(testGraphs[2], 0));
	}

	@org.junit.Test(timeout = 7000)
	public void testBellmanFord4() {
		assertTrue(testGraphBellmanFordRunner(testGraphs[3], 0));
	}
	
	private boolean testGraphBellmanFordRunner(String testCaseFileName, int src) {
		IGraph solver = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
		File inputFile = new File(testDir + testCaseFileName);
		File solutionFile = new File(solutionDir + testCaseFileName);
		int n = GraphUtil.readGraphSize(inputFile);
		int[] distances = new int[n];
		solver.readGraph(inputFile);
		solver.runBellmanFord(src, distances);
		return GraphUtil.validateShortestPath(distances, solutionFile);
	}

	private boolean testGraphDijkstraRunner(String testCaseFileName, int src, boolean testOrder) {
		IGraph solver = (IGraph) TestRunner.getImplementationInstanceForInterface(IGraph.class);
		File inputFile = new File(testDir + testCaseFileName);
		File solutionFile = new File(solutionDir + testCaseFileName);
		File orderFile = new File(dijkstraOrderDir + testCaseFileName);
		int n = GraphUtil.readGraphSize(inputFile);
		int[] distances = new int[n];
		solver.readGraph(inputFile);
		solver.runDijkstra(src, distances);
		if (!GraphUtil.validateShortestPath(distances, solutionFile))
			return false;
		if (testOrder && !GraphUtil.validateDijkstraProcessingOrder(solver.getDijkstraProcessedOrder(), orderFile))
			return false;
		return true;

	}
}
