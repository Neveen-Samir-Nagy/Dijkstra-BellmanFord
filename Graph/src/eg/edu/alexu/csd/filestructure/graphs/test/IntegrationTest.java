package eg.edu.alexu.csd.filestructure.graphs.test;


import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import eg.edu.alexu.csd.filestructure.graphs.IGraph;

public class IntegrationTest {
	
    private final Class<?> graphInterfaceToTest = IGraph.class;
    
    @Test
    public void testCreationHeap() {	
    	System.out.println(graphInterfaceToTest.getPackage());
    	List<Class<?>> candidateClasses = ReflectionHelper.findClassesImplementing(graphInterfaceToTest, graphInterfaceToTest.getPackage());
    	Assert.assertNotNull("Failed to create instance using interface '" + graphInterfaceToTest.getName() + "' !", candidateClasses);
    	Assert.assertEquals("You have more than one public implementation of the interface", 1, candidateClasses.size());
    }
	
}
