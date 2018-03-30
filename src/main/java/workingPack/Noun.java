package workingPack;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.io.*;
import java.util.*;

public class Noun {
	// part NLG
	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);

	public static final String NOUN_FILE = "/lib/Noun.txt";

	private String classNoun;
	private ArrayList adj;
	private NPPhraseSpec noun;

	public ArrayList getAdj() {
		return adj;
	}

	public String getClassNoun() {
		return classNoun;
	}

	public NPPhraseSpec getNoun() {
		return noun;
	}

	public void setAdj(ArrayList a) {
		adj = a;
	}

	public void setClassNoun(String a) {
		classNoun = a;
	}

	public void setNoun(NPPhraseSpec a) {
		noun = a;
	}

	public Noun() {
		try {
			classNoun = ranNoun(true);
			noun = nlgFactory.createNounPhrase(classNoun.split(" ")[1]);// creates a phrase
			if (classNoun.split(" ")[0].length() >= 6 && !(classNoun.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| classNoun.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				noun.setDeterminer(ranDet());
			else if (classNoun.split(" ")[0].length() < 6)
				noun.setDeterminer(ranDet());// ads determiner for small indexes
			NounPhrase.addAdj(noun, classNoun.split(" ")[1]);
		} catch (IOException ex) {
		}
	}

	// a = classNoun
	public Noun(String a) {
		try {
			classNoun = a;
			noun = nlgFactory.createNounPhrase(classNoun.split(" ")[1]);// creates a phrase
			if (classNoun.split(" ")[0].length() >= 6 && !(classNoun.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| classNoun.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				noun.setDeterminer(ranDet());
			else if (classNoun.split(" ")[0].length() < 6)
				noun.setDeterminer(ranDet());// ads determiner for small indexes
			NounPhrase.addAdj(noun, classNoun.split(" ")[1]);
		} catch (IOException ex) {
		}
	}

	public Noun(String[] a) {
		try {
			classNoun = ranNoun(a, true);
			noun = nlgFactory.createNounPhrase(classNoun.split(" ")[1]);// creates a phrase
			if (classNoun.split(" ")[0].length() >= 6 && !(classNoun.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| classNoun.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				noun.setDeterminer(ranDet());
			else if (classNoun.split(" ")[0].length() < 6)
				noun.setDeterminer(ranDet());// ads determiner for small indexes
			NounPhrase.addAdj(noun, classNoun.split(" ")[1]);
		} catch (IOException ex) {
		}
	}

	public static String ranNoun(boolean whole) throws IOException {
		Scanner input = new Scanner(Noun.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		while (input.hasNext())
			nouns.add(input.nextLine());
		if (whole)
			return nouns.get((int) (Math.random() * nouns.size()));
		else
			return nouns.get((int) (Math.random() * nouns.size())).split(" ")[1];

	}

	// whole means whetehr to return whole noun defenition
	public static String ranNoun(String[] a, boolean whole) throws IOException {
		Scanner input = new Scanner(Noun.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		while (input.hasNext())
			nouns.add(input.nextLine());
		ArrayList<String> noun = new ArrayList();
		String g;
		for (int i = 0; i < nouns.size(); i++) {//////// Goes through nouns
			if (wordWorks(nouns.get(i), a))
				noun.add(nouns.get(i));
		} ///////////////// ends the nouns
		if (!whole)
			return noun.get((int) (Math.random() * noun.size())).split(" ")[1];
		return noun.get((int) (Math.random() * noun.size()));
	}

	// pre: a is array of possible numbers, b is the group the noun must have
	// whole means whetehr to return whole noun defenition
	public static String ranNoun(String[] a, String[] group, boolean whole) throws IOException// WORK
	{
		Scanner input = new Scanner(Noun.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		group = onlyNot(group); // goes to only "!"
		while (input.hasNext())
			nouns.add(input.nextLine());
		ArrayList<String> noun = new ArrayList(); // Group : upGroups(b.getClassNoun())
		for (int i = 0; i < nouns.size(); i++) {//////// Goes through nouns
			if (wordWorks(nouns.get(i), a)
					&& (group == null || Probability.containsNone(group, upGroups(nouns.get(i)))))
				noun.add(nouns.get(i));

		} ///////////////// ends the nouns
		if (noun.size() == 0)// DEBUG
			System.out.println("NounPhrase YOOXI");
		for (int i = 70; i < nouns.size(); i++) {//////// Goes through nouns
			if (wordWorks(nouns.get(i), a)
					&& (group == null || Probability.containsNone(group, upGroups(nouns.get(i)))))// WORD
				// DOESN"T
				// WORK
				noun.add(nouns.get(i));

		} ///////////////// ends the nouns
		if (!whole)
			return noun.get((int) (Math.random() * noun.size())).split(" ")[1];
		try {
			return noun.get((int) (Math.random() * noun.size()));
		} catch (Exception ignored) {
			System.out.println("NounPhrase YOOXI");
		}
		return null;// WORK
	}

	public static String word(String a) {
		return a.split(" ")[1];
	}

	public static String ranDet() {
		if (Math.random() < .5)
			return "a";
		return "the";
	}

	// pre: noun is a whole noun
	// post: returns up-groups associated with the noun (groups which the noun must
	// be)
	public static String[] upGroups(String n) throws IOException {
		String iD = n.split(" ")[0];
		String[] iDs = iD.split("\\.");// makes the array of the total number of IDs, not the right iDs
		int count = 0;
		iDs[count++] = iD;
		while (iD.contains(".")) {
			iD = iD.substring(0, iD.lastIndexOf("."));
			iDs[count++] = iD;
		}

		ArrayList<String> all = new ArrayList();// IMPROVE: all and some are extra
		Scanner input = new Scanner(Noun.class.getResourceAsStream(NOUN_FILE));
		{
			String a = null; // to store input
			String[] some = null; // some of the adjective IDs
			count = iDs.length - 1;
			while (input.hasNextLine() && count > -1) {
				a = input.nextLine();
				if (a.startsWith(iDs[count] + " ") || a.startsWith("-" + iDs[count] + " ")) {
					try {
						if (a.split(" ").length > 3 && a.split(" ")[3].charAt(0) != 'P')// P stands for pass
							some = a.split(" ")[3].split(",");
						if (some != null)
							for (int i = 0; i < some.length; i++)// RUN ERR
								all.add(some[i]);
						some = null;
						count--;
					} catch (Exception igrnored) {
						System.out.println("WAZA NounPhrase.java");
					}

				}
			}
			if (all == null)
				return null;
		}
		return all.toArray(new String[all.size()]);

	}

	// array of groups
	// only returns the indexes of the arrays with "!" at the beginning, reomoving
	// the "!"
	public static String[] onlyNot(String[] a) {
		int size = 0;
		for (String i : a)
			if (i.charAt(0) == '!')
				size++;
		if (size == 0)
			return null;
		String[] out = new String[size];
		size = 0;
		for (String i : a)
			if (i.charAt(0) == '!')
				out[size++] = i.substring(1, i.length());
		return out;
	}


	// returs true is a has the bigining of noun
	public static boolean wordWorks(String word, String[] a) {
		if (a == null)
			return true;
		for (int i = 0; i < a.length; i++)// RUN ERR
		{
			if (!(word.length() < a[i].length()) && a[i].equals(word.substring(0, a[i].length())))
				return true;
		}
		return false;
	}
}