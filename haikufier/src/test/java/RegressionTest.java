
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;

public class RegressionTest {

	private static String directory;

	/**
	 * @param args
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException {
		Date today;
		String dateOut;
		DateFormat dateFormatter;

		dateFormatter = DateFormat.getDateInstance();
		today = new Date();
		dateOut = dateFormatter.format(today);
		for (int i = 0; i < args.length; i++) {
			if (false) {
			} else if (args[i].equals("-o")) {
				++i;
			} else if (args[i].equals("-d")) {
			      directory=args[++i]; 
			} else if (args[i].equals("-stubs")) {
			} else if (args[i].equals("-verbose")) {
			} else if (args[i].equals("-help")) {
			} else if (args[i].equals("-version")) {
			} else if (args[i].equals("-jni")) {
			} else if (args[i].equals("-classpath")) {
				++i;
			} else if (args[i].equals("-bootclasspath")) {
				++i;
			} else if (args[i].equals("-force")) {
			} else if (args[i].equals("-r")) { // regression
			} else {
				String className = args[i];

				jprintf("/** ****************************************************\n");
				jprintf(" * VM: %s, %s, %s\n", System
						.getProperty("java.vm.name"), System
						.getProperty("java.version"), System
						.getProperty("java.vm.info"));
				jprintf(" * Computer: %s, %s, %s, %s\n", InetAddress
						.getLocalHost().getHostName(), System
						.getProperty("os.arch"), System.getProperty("os.name"),
						System.getProperty("sun.arch.data.model"));
				jprintf(" * Date: %s\n", dateOut);
				jprintf(" * Program: %s\n", className);
				jprintf(" * Arguments: null\n");
				jprintf(" * #####################################################\n");
				long t0 = System.currentTimeMillis();
				try {
					Class c = Class.forName(className);
					Class[] argTypes = { args.getClass(), // array is Object!
					};

					// Now find the method
					Method m = c.getMethod("main", argTypes);

					// Create the actual argument array
					Object passedArgv[] = { args };

					// Now invoke the method.
					m.invoke(null, passedArgv);

				} catch (Exception e) {
					e.printStackTrace();
				}
				long t1 = System.currentTimeMillis();
				jprintf(" * #####################################################\n");
				jprintf(" * End: elapsed time "+((double) (t1 - t0) / 1000.0)+" sec\n");
				jprintf(" *\n");
				jprintf(" *\n");
				jprintf(" *\n");
				jprintf(" */\n");
				jprintf("\n");
				jprintf("\n");
			}
		}
//		for (Map.Entry<Object, Object> entry : System.getProperties()
//				.entrySet()) {
//			Haikout.println(entry.getKey() + ":=" + entry.getValue());
//		}
	}

	private static void jprintf(String format, Object... args) {
		System.out.printf(format, args);
	}

}
