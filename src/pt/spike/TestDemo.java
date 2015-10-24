package pt.spike;

import org.jboss.weld.environment.se.Weld;

import pt.spike.nfo.HasAccess;
import pt.spike.nfo.Role;
import pt.spike.nfo.interceptor.Access;

/**
 * Tests the Access interceptor and the HasAccess 
 * custom annotation 
 * @author Marco
 *
 */
public class TestDemo {

	public static void main(String[] args) {
		Weld weld = new Weld();
		Demo demo = weld.initialize()
				.instance()
				.select(Demo.class)
				.get();
		System.out.println(demo.out1("ADMIN"));
		System.out.println(demo.out2("USER"));
		System.out.println(demo.out3("ANOMYMOUS"));
		weld.shutdown();
	}

	public static class Demo {

		@Access
		@HasAccess(value = Role.ADMIN)
		public String out1(String myParam) {
			return "User role is " + myParam + "\n---";
		}

		@Access
		@HasAccess(value = Role.USER)
		public String out2(String myParam) {
			return "User role is " + myParam;
		}

		@Access
		@HasAccess(value = Role.ANONYMOUS)
		public String out3(String myParam) {
			return "User role is " + myParam;
		}

	}

}
