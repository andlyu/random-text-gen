import java.util.ArrayList;

public class FunctionToTest {
	public static void main(String[] args) {
		ArrayList<String> nouns = NewUserNouns();
		ArrayList<String> verbs = NewUserVerbs();
		ArrayList<String> adjs = NewUserAdjs();

		for (int i = 0; i < 100; i++) {
			System.out.println(new Clause(nouns, verbs, adjs).toString());
		}

	}

	static ArrayList<String> NewUserNouns() {
		String[] arr = new String[] { "1.2.1.1", "1.2.1.2", "1.2.1.3", "1.2.1.4",
				"1.2.1.5", "1.2.1.6", "1.2.1.7",
				"1.2.1.8", "1.2.1.9", "1.2.1.10", "1.2.1.11", "1.2.1.12",
				"1.2.1.13", "1.2.2", "1.2.2.1", "1.2.2.2",
				"1.2.2.3", "1.2.2.4", "1.2.2.5", "1.2.2.6", "1.2.2.7",
				"1.2.2.8", "1.2.2.9", "1.2.2.10", "1.2.2.11",
				"1.2.2.12", "1.2.2.13", "2", "-3", "3.1", "-3.2",
				"3.2.1.4", "-3.2.2", "3.2.2.2", "4", "-5", "-5.1",
				"5.1.1", "7.1.5.1" };
		ArrayList<String> out = new ArrayList<String>();
		for (String a : arr) {
			out.add(a);
		}
		return out;
	}

	/**
	 * @return an arrayList of nouns that a user begins with
	 */
	static ArrayList<String> NewUserAdjs() {
		ArrayList<String> out = new ArrayList();
		out.add("-1");
		out.add("1.1");
		return out;
	}

	/**
	 * @return an arrayList of nouns that a user begins with
	 */
	static ArrayList<String> NewUserVerbs() {
		String[] arr = new String[] { "-1.", "-1.1", "-1.1.1",
				"1.1.1.1", "-1.2.2", "-0", "1.2.2.1", "-2",
				"-2.1" };
		ArrayList<String> out = new ArrayList();
		for (String a : arr) {
			out.add(a);
		}
		return out;
	}
}
