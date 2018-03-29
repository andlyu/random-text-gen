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

	public static String[] getGroup(String a) {
		if (a.split(" ").length < 4)
			return null;
		return a.split(" ")[3].split(",");
	}



	// Pre: a is a whole noun whihc a verb can describe doing an action
	// Post: randomly chooses a verb which that thing can be doing
	public void verbOnNoun(String a) throws IOException {
		Scanner input = new Scanner(new FileReader(VERB_FILE));
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
		classVerb = verb.get((int) (Math.random() * verb.size()));
		phrase = classVerb.split(" ")[1];
	}

	// pre:: a are the id's of the beginning of ID's of verbs,
	// if whole then returns the whole identity of teh verb
	// retruns an verb out of an array of array a
	public static String ranVerb(String[] a, boolean whole) throws IOException {
		Scanner input = new Scanner(new FileReader(VERB_FILE));
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (wordWorks(verbs.get(i), a))
				verb.add(verbs.get(i));
		} ///////////////// ends the verbs
		if (!whole)
			return verb.get((int) (Math.random() * verb.size())).split(" ")[1];
		return verb.get((int) (Math.random() * verb.size()));
	}

	// Pre:: a are all of the posible ID's of the Word, word is the word bieng
	// tested
	// returs true is a has the bigining of word
	private static boolean wordWorks(String word, String[] a) {
		for (int i = 0; i < a.length; i++) {
			if (!(word.length() < a[i].length()) && a[i].equals(word.substring(0, a[i].length())))
				return true;
		}
		return false;
	}

	
	
	// returns the Noun-pairing part of a verb defention
	public static String posNouns(String a) {
		String out = a.split(" ")[2];
		if (out.contains("/"))
			out = out.split("/")[0];
		return out;
	}

}