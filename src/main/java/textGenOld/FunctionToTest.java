package textGen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FunctionToTest {
	public static void main(String[] args) {
		ArrayList<String> nouns = NewUserNouns();
		ArrayList<String> verbs = new ArrayList();
		verbs.add("1");
		verbs.add("2");
		ArrayList<String> adjs = NewUserAdjs();
		String [] hiNounsAr = new String[]{"1.2.1.2"};
		ArrayList<String> hiNouns = new ArrayList<String>(Arrays.asList(hiNounsAr));
		String [] loNounsAr = new String[]{"1.2.2.1"};
		ArrayList<String> loNouns = new ArrayList<String>(Arrays.asList(loNounsAr));
		String [] hiAdjsAr = new String[]{"1.1"};
		ArrayList<String> hiAdjs = new ArrayList<String>(Arrays.asList(hiAdjsAr));
		String [] loAdjsAr = new String[]{"2.2"};
		ArrayList<String> loAdjs = new ArrayList<String>(Arrays.asList(loAdjsAr));
		
		for (int i = 0; i < 100; i++) {
			Clause now = new Clause(nouns, null, adjs, hiNouns, hiPriVerbs(), hiAdjs, loNouns, loPriVerbs(), loAdjs);
			System.out.println(now);
		}
	}

	static ArrayList<String> NewUserNouns() {
		String[] arr = new String[] { "1.2.1.2", "1.2.2", "1.2.2.1", "2", "-3", "3.1", "-3.2", "3.2.1.4", "-3.2.2", "3.2.2.2", "4", "-5", "-5.1",
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
		out.add("2.1.1");
		out.add("2.2");
		out.add("3.1");
		out.add("3.2");	
		out.add("4.1");
		out.add("4.2");
		out.add("6.1.1");
		out.add("7.1.1");
		return out;
	}

	/**
	 * @return an arrayList of nouns that a user begins with
	 */
	static ArrayList<String> newUserVerbs() {
		String[] arr = new String[] {"1.1.1.1" };
		ArrayList<String> out = new ArrayList();
		for (String a : arr) {
			out.add(a);
		}
		return out;
	}

	/**
	 * @return an arrayList of nouns that a user begins with
	 */
	static ArrayList<String> hiPriVerbs() {
		String[] arr = new String[] { "1.2.1.1.1" };
		ArrayList<String> out = new ArrayList();
		for (String a : arr) {
			out.add(a);
		}
		return out;
	}

	/**
	 * @return an arrayList of nouns that a user begins with
	 */
	static ArrayList<String> loPriVerbs() {
		String[] arr = new String[] { "1.1.1.1" };
		ArrayList<String> out = new ArrayList();
		for (String a : arr) {
			out.add(a);
		}
		return out;
	}
}
