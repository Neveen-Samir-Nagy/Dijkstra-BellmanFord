package eg.edu.alexu.csd.filestructure.graphs.test;

public class GraphUtil {

	public static int readGraphSize(java.io.File graphFile) {
		int n = -1;
		try {
			java.util.Scanner sc = new java.util.Scanner(graphFile);
			n = sc.nextInt();
			sc.close();
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		}
		return n;
	}

	public static boolean validateShortestPath(int[] distances, java.io.File solFile) {
		int n = distances.length;
		int usedInf = Integer.MAX_VALUE / 2;
		int minInf = 10000;
		try {
			java.util.Scanner sc = new java.util.Scanner(solFile);
			for (int i = 0; i < n; i++) {
				int dist = sc.nextInt();
				if ((dist == usedInf && distances[i] < minInf) || (dist != distances[i])) {
					sc.close();
					return false;
				}
			}
			sc.close();
		} catch (java.io.FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static boolean validateDijkstraProcessingOrder(java.util.ArrayList<Integer> order, java.io.File orderFile) {
		int[] orderArr = new int[order.size()];
		for (int i = 0; i < orderArr.length; ++i)
			orderArr[i] = order.get(i);
		return validateShortestPath(orderArr, orderFile);
	}

}
