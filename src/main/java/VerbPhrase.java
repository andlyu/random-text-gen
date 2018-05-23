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
		} catch (IOException ex) {
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

	private String oneVerb(ArrayList<String> limVocab) throws IOException {
		classVerb = ranVerb(new String[] { "1", "2" }, true, limVocab);
		return classVerb.split(" ")[1];
	}

	// Pre: a is a whole noun whihc a verb can describe doing an action
	// Post: randomly chooses a verb which that thing can be doing
	/**
	 * @param a
	 *            is a whole Noun on which the verb is created
	 * @throws IOException
	 */
	public void verbOnNoun(String a) throws IOException {// Repetative class
		verbOnNoun(a, false);
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
	public static String verbOnNoun(String a, boolean whole) throws IOException {
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

	// pre:: a are the id's of the beginning of ID's of verbs,
	// if whole then returns the whole identity of teh verb
	// retruns an verb out of an array of array a
	public static String ranVerb(String[] a, boolean whole, ArrayList<String> limVocab) throws IOException {
		HashSet<String> limVocabSet = new HashSet<String>();
		for (String vocab : limVocab)
			limVocabSet.add(vocab);
		Scanner input = new Scanner(VerbPhrase.class.getResourceAsStream(VERB_FILE));
		ArrayList<String> verbs = new ArrayList();
		String in;
		while (input.hasNext()) {
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

	// Pre: a is a verb
	// returns the Noun-pairing part of a verb defention
	public static String posNouns(String a) {
		String out = a.split(" ")[2];
		if (out.contains("/"))
			out = out.split("/")[0];
		return out;
	}

}