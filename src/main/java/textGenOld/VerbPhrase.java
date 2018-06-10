package textGen;

import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.util.*;
import java.io.*;

// Mistake : only one verb
public class VerbPhrase {
	private String classVerb;// long, with all of the information
	private String phrase;

	public static final String VERB_FILE = "lib/Verb.txt";

	public void setClassVerb(String a) {
		classVerb = a;
	}

	public void setPhrase(String a) {
		phrase = a;
	}

	public String getClassVerb() {
		return classVerb;
	}

	public String getPhrase() {
		return phrase;
	}

	public String toString() {
		return realiser.realiseSentence(nlgFactory.createClause(null, phrase));
	}

	public boolean isTransitive() { // if Trasitive returns true
		if (classVerb.split(" ")[2].split("/")[0].contains("-"))
			return true;
		return false;
	}

	// part NLG
	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);

	public VerbPhrase() {
		try {
			phrase = oneVerb();
		} catch (IOException ex) {
			ex.printStackTrace();
			System.out.println("manyNounSbjs() is Wrong");
		}
	}

	public VerbPhrase(ArrayList<String> limVocab) {
		try {
			phrase = oneVerb(limVocab);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("manyNounSbjs() is Wrong");
		}
	}

	public VerbPhrase(ArrayList<String> limVocab, ArrayList<String> hiPri, ArrayList<String> loPri) {
		try {
			phrase = oneVerb(limVocab, hiPri, loPri);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("manyNounSbjs() is Wrong");
		}
	}

	// pre:; a = whole verb
	public VerbPhrase(String a) {
		classVerb = a;
		phrase = a.split(" ")[1];

	}

	public static void main(String[] args) throws IOException {
		for (int i = 0; i < 100; i++) {
			VerbPhrase a = new VerbPhrase();
			a.verbOnNoun("7.3.1 one");
			SPhraseSpec p = nlgFactory.createClause(null, a.getPhrase());
			System.out.println(i + "   " + realiser.realiseSentence(p));
		}
	}

	private String oneVerb() throws IOException {
		classVerb = ranVerb(new String[] { "1", "2" }, true);
		return classVerb.split(" ")[1];
	}

	/**
	 * @param limVocab
	 *            a list of verbs that could get used
	 * @return
	 */
	private String oneVerb(ArrayList<String> limVocab) {
		try {
			classVerb = ranVerb(new String[] { "1", "2" }, true, limVocab);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classVerb.split(" ")[1];

	}

	private String oneVerb(ArrayList<String> limVocab, ArrayList<String> hiPri, ArrayList<String> loPri) {
		try {
			classVerb = ranVerb(new String[] { "1", "2" }, true, limVocab, hiPri, loPri);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (classVerb.charAt(0) == ('-'))
			System.out.println("faluer");
		return classVerb.split(" ")[1];
	}

	// Pre: a is a whole noun whihc a verb can describe doing an action
	// Post: randomly chooses a verb which that thing can be doing
	/**
	 * @param a
	 *            is a whole Noun on which the verb is created
	 * @throws IOException
	 */
	public void verbOnNoun(String a) {// Repetative class
		try {
			verbOnNoun(a, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Pre: a is a whole noun whihc a verb can describe doing an action
	// Post: randomly chooses a verb which that thing can be doing
	/**
	 * @param a
	 *            is a whole Noun on which the verb is created
	 * @param whole
	 *            -> whether the whole verb is returned or not
	 * @throws IOException
	 */
	public static String verbOnNoun(String a, boolean whole) throws IOException {//TODO finds the wrong verbs
		Scanner input = new Scanner(VerbPhrase.class.getResourceAsStream(VERB_FILE));
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		for (int i = 0; i < verbs.size(); i++) {
			if (verbs.get(i).charAt(0) != '-') {
				String nounID = a.split(" ")[0];
				String[] verbIDs = verbs.get(i).split(" ")[2].split("/")[0].split("-")[0].split(",");
				for (int j = 0; j < verbIDs.length; j++)
					if (nounID.length() >= verbIDs[j].length())
						if (verbIDs[j].equals(nounID.substring(0, verbIDs[j].length())))
							verb.add(verbs.get(i));
			}
		}
		String curVerb = verb.get((int) (Math.random() * verb.size()));
		if (whole) {
			return curVerb;
		} else
			return curVerb.split(" ")[1];
	}

	// pre:: a are the id's of the beginning of ID's of verbs,
	// if whole then returns the whole identity of teh verb
	// retruns an verb out of an array of array a
	public static String ranVerb(String[] a, boolean whole) throws IOException {
		Scanner input = new Scanner(VerbPhrase.class.getResourceAsStream(VERB_FILE));
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (Noun.wordWorks(verbs.get(i), a))
				verb.add(verbs.get(i));
		} ///////////////// ends the verbs
		if (!whole)
			return verb.get((int) (Math.random() * verb.size())).split(" ")[1];
		return verb.get((int) (Math.random() * verb.size()));
	}

	/**
	 * @param a
	 *            the gramatical constraint on the verbs,
	 * @param whole
	 *            whether or not to return the whole selected Verb
	 * @param limVocab
	 *            the User's imposed limit on verbs
	 * @return a random verb given the constraints
	 */
	public static String ranVerb(String[] a, boolean whole, ArrayList<String> limVocab) { // TODO
																							// remove
																							// body
		HashSet<String> limVocabSet = new HashSet<String>();
		for (String vocab : limVocab)
			limVocabSet.add(vocab);
		Scanner input = new Scanner(VerbPhrase.class.getResourceAsStream(VERB_FILE));
		ArrayList<String> verbs = new ArrayList();
		String in;
		while (input.hasNext()) {// only adds the ones that work
			in = input.nextLine();
			if (limVocabSet.contains(in.split(" ")[0]))
				verbs.add(in);
		}
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (Noun.wordWorks(verbs.get(i), a))
				verb.add(verbs.get(i));
		} ///////////////// ends the verbs
		if (!whole)
			return verb.get((int) (Math.random() * verb.size())).split(" ")[1];
		return verb.get((int) (Math.random() * verb.size()));
	}

	/**
	 * @param a
	 *            the gramatical constraint on the verbs,
	 * @param whole
	 *            whether or not to return the whole selected Verb
	 * @param limVocab
	 *            the User's imposed limit on verbs
	 * @param hiPri
	 *            If contains working verbs, 50% of being selected from
	 * @param loPri
	 *            if contains working verbs, 25 % of being selected from
	 * @return a random verb given the constraints
	 */
	public static String ranVerb(String[] a, boolean whole, ArrayList<String> limVocab, ArrayList<String> hiPri,
			ArrayList<String> loPri) {

		Scanner input = new Scanner(VerbPhrase.class.getResourceAsStream(VERB_FILE));
		ArrayList<String> verbs = new ArrayList();
		ArrayList<String> hiVerbs = new ArrayList();
		ArrayList<String> loVerbs = new ArrayList();

		// limits Vocab
		HashSet<String> limVocabSet = null;
		if (limVocab != null) {
			limVocabSet = new HashSet<String>();
			for (String vocab : limVocab)
				limVocabSet.add(vocab);
		}
		String in;
		while (input.hasNext()) {// only adds the ones that work
			in = input.nextLine();
			if (limVocabSet == null || limVocabSet.contains(in.split(" ")[0])) {
				verbs.add(in);
			}

		}
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (Noun.wordWorks(verbs.get(i), a)) {
				String loopVerb = verbs.get(i);
				verb.add(loopVerb);
				if (hiPri.contains(loopVerb.split(" ")[0]))
					hiVerbs.add(loopVerb);
				if (loPri.contains(loopVerb.split(" ")[0]))
					loVerbs.add(loopVerb);
			}
		} ///////////////// ends the verbs
		if (hiVerbs.size() > 0 && Math.random() > .5) {
			return verbFromVerbs(hiVerbs, whole);
		} else if (loVerbs.size() > 0 && Math.random() > .5) {
			return verbFromVerbs(loVerbs, whole);
		}
		return verbFromVerbs(verb, whole);
	}

	/**
	 * @param verbList
	 *            the list of verbs that will be chosen from
	 * @param whole
	 *            whether the whole verb or just the word will be returned
	 * @return a verb chosen from the list of verbs
	 */
	public static String verbFromVerbs(ArrayList<String> verbList, boolean whole) {
		if (!whole)
			return verbList.get((int) (Math.random() * verbList.size())).split(" ")[1];
		return verbList.get((int) (Math.random() * verbList.size()));
	}

	// Pre: a is a verb
	// returns the Noun-pairing part of a verb defention
	public static String posNouns(String a) {
		String out = a.split(" ")[2];
		if (out.contains("/"))
			out = out.split("/")[0];
		return out;
	}

	public static ArrayList<String> getAllVerbsFromFile(boolean whole) {
		Scanner input = new Scanner(Noun.class.getResourceAsStream(VERB_FILE));
		ArrayList<String> outVerbs = new ArrayList();
		while (input.hasNext()) {
			String in = input.nextLine();
			if (in.length() > 0 && in.split(" ").length > 1 && '-' != (in.split(" ")[0].charAt(0)))
				if (whole) {
					outVerbs.add(in);
				} else
					outVerbs.add(in.split(" ")[1]);
		}
		return outVerbs;
	}
}