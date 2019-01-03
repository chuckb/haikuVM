/**
 * 
 */
package haikuvm.bench.from.darjeeling;

/**
 * @author Michael Maaser
 *
 */
public interface BenchmarkImplementation {

	void runTest(int times);
	
	String getName();

}
