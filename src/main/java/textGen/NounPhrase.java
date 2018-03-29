package textGen;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.util.*;
//mistake = off of only one noun
import java.io.*;

public class NounPhrase {
	private String classNoun;// long, with all of the information
	private CoordinatedPhraseElement phrase;

	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);

	public static final String ADJECTIVE_FILE = "lib/Adjective.txt";
	public static final String NOUN_FILE = "lib/Noun.txt";
	public static final String VERB_FILE = "lib/Verb.txt";

	public NounPhrase() {
		try {
			phrase = manyNounSbjs();
		} catch (IOException ex) {
			System.out.println("manyNounSbjs() is Wrong");
		}
	}

	// Pre: a is the long noun on which the string is built
	public NounPhrase(String a) {
		try {
			classNoun = a;
			CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();

			for (int i = Probability.likely(); i >= 0; i--) {
				NPPhraseSpec n3p = nlgFactory.createNounPhrase(a.split(" ")[1]);// creates a phrase
				if (a.split(" ")[0].length() >= 6 && !(a.split(" ")[0].substring(0, 6).equals("1.2.2.")
						|| a.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
					n3p.setDeterminer(Noun.ranDet());
				else if (a.split(" ")[0].length() < 6)
					n3p.setDeterminer(Noun.ranDet());// ads determiner for small indexes
				addAdj(n3p, a.split(" ")[1]);
				n1p.addCoordinate(n3p);
			}
			phrase = n1p;
		} catch (Exception ignored) {
			System.out.println("manyNounSbjs() is Wrong");
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			NounPhrase a = new NounPhrase();
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(a.getClassNoun().split(" ")[1]);
			n3p.setDeterminer(Noun.ranDet());
			n3p.setDeterminer(" ");
			SPhraseSpec p = nlgFactory.createClause(n3p, null);
			System.out.println(i + "   " + realiser.realiseSentence(p));
			System.out.println(a.getClassNoun());
		}
	}

	public String getClassNoun() {
		return classNoun;
	}

	public void setClassNoun(String a) {
		classNoun = a;
	}

	public CoordinatedPhraseElement getPhrase() {
		return phrase;
	}

	public String toString() {
		return realiser.realiseSentence(nlgFactory.createClause(phrase, null));
	}

	public NPPhraseSpec toNPPhraseSpec() {
		NPPhraseSpec out = nlgFactory.createNounPhrase(classNoun.split(" ")[1]);

		if (classNoun.split(" ")[0].length() >= 6 && !(classNoun.split(" ")[0].substring(0, 6).equals("1.2.2.")
				|| classNoun.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
			out.setDeterminer(Noun.ranDet());
		else if (classNoun.split(" ")[0].length() < 6)
			out.setDeterminer(Noun.ranDet());// ads determiner for small indexes
		return out;
	}

	// returns a random Adjective
	public static String ranAdj(String[] a, boolean whole) throws IOException {
		Scanner input = new Scanner(new FileReader(ADJECTIVE_FILE));
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (Noun.wordWorks(verbs.get(i), a))
				verb.add(verbs.get(i));
		} ///////////////// ends the verbs
		if (verb.size() == 0)
			return null;
		if (!whole) {
			return verb.get((int) (Math.random() * verb.size())).split(" ")[1];
		}
		return verb.get((int) (Math.random() * verb.size()));
	}

	public static String[] nounToVerb(String n) throws IOException {

		String iD = n.split(" ")[0];
		String[] iDs = iD.split("\\.");// makes the array of the total number of IDs, not the right iDs
		int count = 0;
		iDs[count++] = iD;
		while (iD.contains(".")) {
			iD = iD.substring(0, iD.lastIndexOf("."));
			iDs[count++] = iD; // noun IDs
		}

		ArrayList all = new ArrayList();
		Scanner input = new Scanner(new FileReader(VERB_FILE));
		{
			String a = null; // to store input
			String[] some = null; // some of the adjective IDs
			count = iDs.length - 1;
			while (input.hasNextLine()) {
				a = input.nextLine();
				if (a.charAt(0) != '-') {
					String[] pos = a.split(" ")[2].split("/")[0].split("-")[0].split(",");// arrayList containing
																							// posible Nouns
					for (int i = 0; i < iDs.length; i++)
						for (int j = 0; j < pos.length; j++)
							if (pos[j].equals(iDs[i])) {
								all.add(a);
								break;
							}
				}
			}
		}
		String[] out = new String[all.size()];// to be returned in String [] format
		for (int i = 0; i < out.length; i++)
			out[i] = (String) all.get(i); // could make the String earlier
		return out;
	}

	//////////////////// GROUPS///////////////////////////////////
	// returns true if any index overlaps with the other index

	///////////////////////// GROUPS END//////////////////////////////////////////

	// returns a String [] of adjective indecies which corelate with the noun
	// I THINK THIS IS WRONG
	public static String[] nounToAdj(String n) throws IOException {

		String iD = n.split(" ")[0];
		String[] iDs = iD.split("\\.");// makes the array of the total number of IDs, not the right iDs
		int count = 0;
		iDs[count++] = iD;
		while (iD.contains(".")) {
			iD = iD.substring(0, iD.lastIndexOf("."));
			iDs[count++] = iD;
		}

		ArrayList all = new ArrayList();
		Scanner input = new Scanner(new FileReader(NOUN_FILE));
		{
			String a = null; // to store input
			String[] some = null; // some of the adjective IDs
			count = iDs.length - 1;
			while (input.hasNextLine() && count > -1) {
				a = input.nextLine();
				if (a.startsWith(iDs[count] + " ") || a.startsWith("-" + iDs[count] + " ")) {
					try {
						if (a.split(" ").length > 2 && a.split(" ")[2].charAt(0) != 'P')// P stands for pass
							some = a.split(" ")[2].split(",");
					} catch (Exception ignored) {
						System.out
								.println(a + "HEHEHEHHEHEHEHEHEHHEHEHEHHEHHEHEHEHHEHEHEHHEHHEHEHEHHEHEHEHHEHHEHHEHEH");
					}
					if (some == null)
						return null;
					for (int i = 0; i < some.length; i++)// RUN ERR
						all.add(some[i]);
					count--;

				}
			}
		}
		String[] out = new String[all.size()];// to be returned in String [] format
		for (int i = 0; i < out.length; i++)
			out[i] = (String) all.get(i); // could make the String earlier
		return out;
	} // HERE

	// creates a "noun phrase" with many subjects and adjectives around which all
	// agree with v1 (WHOLE)
	public CoordinatedPhraseElement manyNouns(String v1) throws IOException {
		CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
		for (int i = Probability.likely(); i >= 0; i--) {
			String n1 = Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[0].split(","), true);// subject of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name
				n3p.setDeterminer(Noun.ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(Noun.ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = Probability.likely(); j > 0; j--)
				n3p.addPreModifier(ranAdj(nounToAdj(n1), false));
			n1p.addCoordinate(n3p);
			phrase = n1p;
		}
		return n1p;

	}

	// Pre: n1p is NPPhraseSpec to which to add the adj, n1 is the NounWord
	public static void addAdj(NPPhraseSpec n1p, String n1) throws IOException {
		for (int j = Probability.likely(); j > 0; j--)
			n1p.addPreModifier(ranAdj(nounToAdj(n1), false));

	}

	public CoordinatedPhraseElement manyNounSbjs() throws IOException {
		CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
		for (int i = Probability.likely(); i >= 0; i--) {
			String n1 = Noun.ranNoun(true);// subject of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				n3p.setDeterminer(Noun.ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(Noun.ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = Probability.likely(); j > 0; j--)
				n3p.addPreModifier(ranAdj(nounToAdj(n1), false));
			n1p.addCoordinate(n3p);
		}
		phrase = n1p;
		return n1p;

	}

	// pre: v = verb aroun which to make the noun
	// post: changes NounPhrase to be built on the verb, all nouns agree with the
	// verb
	public CoordinatedPhraseElement manyNounSbjs(String v) throws IOException {
		CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
		for (int i = Probability.likely(); i >= 0; i--) {
			String n1 = Noun.ranNoun(VerbPhrase.posNouns(v).split("-")[0].split(","), true);// subject of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name
				n3p.setDeterminer(Noun.ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(Noun.ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = Probability.likely(); j > 0; j--)
				n3p.addPreModifier(ranAdj(nounToAdj(n1), false));
			n1p.addCoordinate(n3p);
		}
		phrase = n1p;
		return n1p;
	}

	// pre: v = verb aroun which to make the noun
	// post: changes NounPhrase to be built on the verb, all nouns agree with the
	// verb
	public CoordinatedPhraseElement manyNounObjs(String v) throws IOException {

		CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
		for (int i = Probability.likely(); i >= 0; i--) {
			String n1 = Noun.ranNoun(VerbPhrase.posNouns(v).split("-")[1].split(","), true); // object of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				n3p.setDeterminer(Noun.ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(Noun.ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = Probability.likely(); j > 0; j--)
				n3p.addPreModifier(ranAdj(nounToAdj(n1), false));
			n1p.addCoordinate(n3p);
		}
		phrase = n1p;
		return n1p;

	}

}