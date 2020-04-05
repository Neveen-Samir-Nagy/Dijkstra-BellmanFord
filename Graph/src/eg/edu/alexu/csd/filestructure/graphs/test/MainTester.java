package eg.edu.alexu.csd.filestructure.graphs.test;

import java.util.ArrayList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTester {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(IntegrationTest.class);
		Result result2 = JUnitCore.runClasses(UnitTest.class);
		
		int totalNumOfTests = result.getRunCount() + result2.getRunCount();
		int totalFailures = result.getFailureCount() + result2.getFailureCount();
		
		System.out.println("Total tests passed: " + (totalNumOfTests - totalFailures) + "/" + totalNumOfTests);
		
		ArrayList<Failure> failures = new ArrayList<>();
		failures.addAll(result.getFailures());
		failures.addAll(result2.getFailures());
		
		for (Failure failure : failures) {
			System.out.println(failure.toString());
		}
		
	}
}