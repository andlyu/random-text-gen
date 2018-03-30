package workingPack;
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

			for (int i = likely(); i >= 0; i--) {
				NPPhraseSpec n3p = nlgFactory.createNounPhrase(a.split(" ")[1]);// creates a phrase
				if (a.split(" ")[0].length() >= 6 && !(a.split(" ")[0].substring(0, 6).equals("1.2.2.")
						|| a.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
					n3p.setDeterminer(ranDet());
				else if (a.split(" ")[0].length() < 6)
					n3p.setDeterminer(ranDet());// ads determiner for small indexes
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
			n3p.setDeterminer(ranDet());
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
			out.setDeterminer(ranDet());
		else if (classNoun.split(" ")[0].length() < 6)
			out.setDeterminer(ranDet());// ads determiner for small indexes
		return out;
	}

	public static String ranDet() {
		if (Math.random() < .5)
			return "a";
		return "the";
	}

	public static String word(String a) {
		return a.split(" ")[1];
	}

	public static String ranNoun() throws IOException {
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		while (input.hasNext())
			nouns.add(input.nextLine());
		return nouns.get((int) (Math.random() * nouns.size())).split(" ")[1];
	}

	public static String ranNoun(boolean whole) throws IOException {
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));//HERE

		ArrayList<String> nouns = new ArrayList();
		while (input.hasNext())
			nouns.add(input.nextLine());
		if (whole)
			return nouns.get((int) (Math.random() * nouns.size()));
		else
			return nouns.get((int) (Math.random() * nouns.size())).split(" ")[1];

	}

	// returns a noun whose identification begins with one of the a array numbers;
	// returns only 1 word
	public static String ranNoun(String[] a) throws IOException {
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		while (input.hasNext())
			nouns.add(input.nextLine());
		ArrayList<String> noun = new ArrayList();
		String g;
		for (int i = 0; i < nouns.size(); i++) {//////// Goes through nouns
			if (wordWorks(nouns.get(i), a))
				noun.add(nouns.get(i));
		} ///////////////// ends the nouns
		return noun.get((int) (Math.random() * noun.size())).split(" ")[1];
	}

	// whole means whetehr to return whole noun defenition
	public static String ranNoun(String[] a, boolean whole) throws IOException {
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
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

	/*
	 * OLD CODE, public static String ranNoun(String [] a,String [] group, boolean
	 * whole)throws IOException//WORK { Scanner input = new Scanner(new
	 * FileReader(NOUN_FILE)); ArrayList<String> nouns= new ArrayList();
	 * while(input.hasNext()) nouns.add(input.nextLine()); ArrayList<String> noun=
	 * new ArrayList(); String g ; for(int i = 0; i< nouns.size(); i++)
	 * {////////Goes through nouns //
	 * if(wordWorks(nouns.get(i),a)&&overlap(subGroups(nouns.get(i)),group))
	 * if(wordWorks(nouns.get(i),a)&&containsAll(group,allGroups(nouns.get(i))))
	 * noun.add(nouns.get(i));
	 * 
	 * }/////////////////ends the nouns if(!whole) return
	 * noun.get((int)(Math.random()*noun.size())).split(" ")[1]; try{ return
	 * noun.get((int)(Math.random()*noun.size())); }catch(Exception ignored){
	 * System.out.println("NounPhrase");} return null;//WORK }
	 */
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

	// returns a random Adjective
	public static String ranAdj(String[] a, boolean whole) throws IOException {
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(ADJECTIVE_FILE));
		ArrayList<String> verbs = new ArrayList();
		while (input.hasNext())
			verbs.add(input.nextLine());
		ArrayList<String> verb = new ArrayList();
		String g;
		for (int i = 0; i < verbs.size(); i++) {//////// Goes through verbs
			if (wordWorks(verbs.get(i), a))
				verb.add(verbs.get(i));
		} ///////////////// ends the verbs
		if (verb.size() == 0)
			return null;
		if (!whole) {
			return verb.get((int) (Math.random() * verb.size())).split(" ")[1];
		}
		return verb.get((int) (Math.random() * verb.size()));
	}

	// returs true is a has the bigining of noun
	private static boolean wordWorks(String word, String[] a) {
		if (a == null)
			return true;
		for (int i = 0; i < a.length; i++)// RUN ERR
		{
			if (!(word.length() < a[i].length()) && a[i].equals(word.substring(0, a[i].length())))
				return true;
		}
		return false;
	}

	// pre: a is array of possible numbers, b is the group the noun must have
	// whole means whetehr to return whole noun defenition
	public static String ranNoun(String[] a, String[] group, boolean whole) throws IOException// WORK
	{
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		group = onlyNot(group); // goes to only "!"
		while (input.hasNext())
			nouns.add(input.nextLine());
		ArrayList<String> noun = new ArrayList(); // Group : upGroups(b.getClassNoun())
		for (int i = 0; i < nouns.size(); i++) {//////// Goes through nouns
			if (wordWorks(nouns.get(i), a) && (group == null || containsNone(group, upGroups(nouns.get(i)))))
				noun.add(nouns.get(i));

		} ///////////////// ends the nouns
		if (noun.size() == 0)// DEBUG
			System.out.println("NounPhrase YOOXI");
		for (int i = 70; i < nouns.size(); i++) {//////// Goes through nouns
			if (wordWorks(nouns.get(i), a) && (group == null || containsNone(group, upGroups(nouns.get(i)))))// WORD
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
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(VERB_FILE));
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
	private static boolean overlap(String[] a, String[] b) {
		for (String x : a)
			for (String y : b)
				if (x.equals(y))
					return true;
		return false;
	}

	private static boolean containsAll(String[] a, String[] b) {
		boolean pass = false;
		if (b == null)
			return false;// Not Sure
		for (String x : a) {
			for (String y : b)
				if (x.equals(y) || ("-" + x).equals(y)) {
					pass = true;
					break;
				}
			if (!pass)
				return false;
			pass = false;

		}
		return true;
	}

	// returns true if there are none a's in the array b
	private static boolean containsNone(String[] a, String[] b) {
		if (b == null)
			return false;// Not Sure
		for (String x : a) {
			for (String y : b)
				if (x.equals(y) || ("-" + x).equals(y)) {
					return false;
				}
		}
		return true;
	}
	///////////////////////// GROUPS END//////////////////////////////////////////

	// returns the Noun-pairing part of a verb defention
	private static String posNouns(String a) {
		String out = a.split(" ")[2];
		if (out.contains("/"))
			out = out.split("/")[0];
		return out;
	}

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
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
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
	}

	// standard factor is .3
	// for the pourpose of having many 1s, few 2s and even less 3s
	private static int likely() {
		int out = 0;
		double rand = Math.random();
		double factor = .4;
		double curCompare = factor;
		while (rand < curCompare) {
			curCompare *= factor;
			curCompare *= factor;
			out++;
		}
		return out;
	}

	private static int likely(double factor) {
		int out = 0;
		double rand = Math.random();
		double curCompare = factor;
		while (rand < curCompare) {
			curCompare *= factor;
			out++;
		}
		return out;
	}

	// creates a "noun phrase" with many subjects and adjectives around which all
	// agree with v1 (WHOLE)
	public CoordinatedPhraseElement manyNouns(String v1) throws IOException {
		CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
		for (int i = likely(); i >= 0; i--) {
			String n1 = ranNoun(posNouns(v1).split("-")[0].split(","), true);// subject of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name
				n3p.setDeterminer(ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = likely(); j > 0; j--)
				n3p.addPreModifier(ranAdj(nounToAdj(n1), false));
			n1p.addCoordinate(n3p);
			phrase = n1p;
		}
		return n1p;

	}

	// Pre: n1p is NPPhraseSpec to which to add the adj, n1 is the NounWord
	public static void addAdj(NPPhraseSpec n1p, String n1) throws IOException {
		for (int j = likely(); j > 0; j--)
			n1p.addPreModifier(ranAdj(nounToAdj(n1), false));

	}

	public CoordinatedPhraseElement manyNounSbjs() throws IOException {
		CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
		for (int i = likely(); i >= 0; i--) {
			String n1 = ranNoun(true);// subject of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				n3p.setDeterminer(ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = likely(); j > 0; j--)
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
		for (int i = likely(); i >= 0; i--) {
			String n1 = ranNoun(posNouns(v).split("-")[0].split(","), true);// subject of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name
				n3p.setDeterminer(ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = likely(); j > 0; j--)
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
		for (int i = likely(); i >= 0; i--) {
			String n1 = ranNoun(posNouns(v).split("-")[1].split(","), true); // object of sentance
			classNoun = n1;
			NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);// NEW

			if (n1.split(" ")[0].length() >= 6 && !(n1.split(" ")[0].substring(0, 6).equals("1.2.2.")
					|| n1.split(" ")[0].substring(0, 6).equals("1.2.1.")))// checks for name/ to add determiner
				n3p.setDeterminer(ranDet());
			else if (n1.split(" ")[0].length() < 6)
				n3p.setDeterminer(ranDet());// ads determiner for small indexes

			///// next two lines give adj
			for (int j = likely(); j > 0; j--)
				n3p.addPreModifier(ranAdj(nounToAdj(n1), false));
			n1p.addCoordinate(n3p);
		}
		phrase = n1p;
		return n1p;

	}

	// pre::a is a whole nounString
	// Post:: returns the group number, returns null if none
	public static String[] getGroup(String a) {
		if (a.split(" ").length < 4)
			return null;
		return a.split(" ")[3].split(",");
	}

	// Pre: group is group ID, noun is whole noun String
	// Checks whether a noun is in a certain group
	public static boolean checkGroup(String group, String noun) {
		String[] nounGroups = getGroup(noun);
		for (String a : nounGroups)
			if (a.equals(group))
				return true;
		return false;
	}

	// pre: word is whole noun
	// post: retuns an array of all other nouns with it's id at the beginning
	public static String[] child(String noun) throws IOException {
		String id = noun.split(" ")[0];
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
		ArrayList<String> nouns = new ArrayList();
		nouns.add(noun);
		while (input.hasNext()) {
			if (input.nextLine().equals(noun)) {
				String temp = input.nextLine();
				while (temp.split(" ")[0].equals(id) || temp.split(" ")[0].equals("-" + id))
					nouns.add(input.nextLine());
			}
			break;
		}
		return nouns.toArray(new String[nouns.size()]);

	}

	// pre: noun is a whole noun
	// post: returns sub-groups associated with the noun
	public static String[] subGroups(String noun) throws IOException {
		List<String> all = Arrays.asList(child(noun));
		List<String> out = new ArrayList();
		for (int i = 0; i < all.size(); i++) {
			if (getGroup(all.get(i)) != null)
				out.addAll(Arrays.asList(getGroup(all.get(i))));

		}
		return out.toArray(new String[out.size()]);
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
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
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

	// pre: noun is a whole noun
	// post: returns up-groups associated with the noun (groups which the noun must
	// be)
	public static String[] allGroups(String n) throws IOException {
		String iD = n.split(" ")[0];
		String[] iDs = iD.split("\\.");// makes the array of the total number of IDs, not the right iDs
		int count = 0;
		iDs[count++] = iD;
		while (iD.contains(".")) {
			iD = iD.substring(0, iD.lastIndexOf("."));
			iDs[count++] = iD;
		}

		ArrayList<String> all = new ArrayList();// IMPROVE: all and some are extra
		Scanner input = new Scanner(NounPhrase.class.getResourceAsStream(NOUN_FILE));
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
					} catch (Exception igrnored) {
						System.out.println("WAZA NounPhrase.java");
					}
					if (some != null)
						for (int i = 0; i < some.length; i++)// RUN ERR
							all.add(some[i]);
					count--;
				}
			}
			List<String> up = Arrays.asList(child(n));// IMPROVE thre is repediteveness with child(n)
			for (int i = 0; i < all.size(); i++) {
				if (getGroup(all.get(i)) != null)
					all.addAll(Arrays.asList(getGroup(up.get(i))));

			}
			if (all.size() == 0)
				return null;
		}
		return all.toArray(new String[all.size()]);

	}

}