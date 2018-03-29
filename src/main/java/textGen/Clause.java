package textGen;
// move 5.2.4.1.1.1  to names in Nouns.txt
//Names can only be adults in NOuns.txt
//WORK ON GROUPING
import simplenlg.framework.*;

import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.util.*;
import java.io.*;

public class Clause {
	private NounPhrase classNoun;
	private VerbPhrase classVerb;
	private SPhraseSpec sent;
	private NounPhrase objectNoun;
	private PrepPhrase classPrep;

	private static Lexicon lexicon = Lexicon.getDefaultLexicon();
	private static NLGFactory nlgFactory = new NLGFactory(lexicon);
	private static Realiser realiser = new Realiser(lexicon);


	public NounPhrase getClassNoun() {
		return classNoun;
	}// just Basics

	public VerbPhrase getClassVerb() {
		return classVerb;
	}

	public SPhraseSpec getSent() {
		return sent;
	}

	public NounPhrase getObjectNoun() {
		return objectNoun;
	}

	public PrepPhrase getClassPrep() {
		return classPrep;
	}

	public void setClassNoun(NounPhrase a) {
		classNoun = a;
	}

	public void setClassVerb(VerbPhrase a) {
		classVerb = a;
	}

	public void setSent(SPhraseSpec a) {
		sent = a;
	}

	public void setObjectNoun(NounPhrase a) {
		objectNoun = a;
	}

	public void setClassPrep(PrepPhrase a) {
		classPrep = a;
	}

	public String toString() {
		return (realiser.realiseSentence(sent));
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		System.out.println();
		Clause c = new Clause();
		Clause d = new Clause();

		for (int i = 0; i < 10000; i++) {

			if (Math.random() < .5) {
				if (d.getObjectNoun() != null) {
					if (Math.random() < .5) {
						c = new Clause(d.getObjectNoun());// waistfull
						c.adjClause();
						System.out.println(c.toString());
					}
				}
				d.adjClause();
			} else if (d.getObjectNoun() != null)
				d = new Clause(d.getObjectNoun());
			else if (Math.random() < .5) {

				d = new Clause(d.getClassNoun());
			} else {
				System.out.println();
				d = new Clause();
			}

			System.out.println(d.toString());

		}

		for (int i = 0; i < 0; i++)// TEMPorary
		{
			SPhraseSpec ind = Sentance.makeSentance();
			Clause a = new Clause();
			System.out.println(a.getClassVerb());
			System.out.println(a.getClassNoun());
			System.out.println(realiser.realiseSentence(a.getSent()));
			Clause b = new Clause();// WORK rework clauseOnNoun()
			b.setSent(clauseOnNoun(a.getClassNoun().getClassNoun()));
			System.out.println(realiser.realiseSentence(b.getSent()));

		}

		// }

	}

	public Clause()// catch IOException
	{
		try {
			VerbPhrase v2 = new VerbPhrase();
			classVerb = v2; // Sets classVerb
			SPhraseSpec p;
			// SPhraseSpec dep = null;
			if (VerbPhrase.posNouns(v2.getClassVerb()).contains("-"))// if(verb is transitive)
			{

				// creates multiple subjects
				NounPhrase a = new NounPhrase();// UGLY
				a.manyNouns(v2.getClassVerb());
				classNoun = a;

				// DirectObject

				NounPhrase b = new NounPhrase();// WORK
				b.manyNounObjs((String) v2.getClassVerb());// CHECK
				objectNoun = b;

				p = nlgFactory.createClause(a.getPhrase(), v2.getPhrase(), b.getPhrase());
				sent = p;
			} else // if verb is intransitive
			{
				// creates multiple subjects
				NounPhrase a = new NounPhrase();
				CoordinatedPhraseElement n1p = a.manyNouns(v2.getClassVerb());
				classNoun = a;
				p = nlgFactory.createClause(n1p, v2.getPhrase());
				sent = p;
				/*
				 * if(groups!=null && groups.length != 0 && groups[0].equals("10.1")){//BAD{
				 * sent = nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase(),
				 * NounPhrase.ranAdj(NounPhrase.nounToAdj(classNoun.getClassNoun()),false)); }
				 */
			}
			// changing preposition
			classPrep = new PrepPhrase(classVerb, classNoun);
			if (Math.random() < .5)
				sent.addComplement(classPrep.getPrepPhrase());

			/*
			 * if(v2.getClassVerb().contains("/"))// temporary { int prepI = (int)(
			 * Math.random()*posPreps(v2.getClassVerb()).split("--").length);// prepI =
			 * instance of prep in verb Def String prepS =
			 * ranPrep(posPreps(v2.getClassVerb()).split("--")[prepI].split("-")[0].split(
			 * ",")); // prepS = preposition String NPPhraseSpec prepNP =
			 * nlgFactory.createNounPhrase(ranNoun(posPreps(v2.getClassVerb()).split("--")[
			 * prepI].split("-")[1].split(",")));// prepositional phrase
			 * prepNP.setDeterminer(ranDet()); pp = nlgFactory.createPrepositionPhrase();
			 * pp.addComplement(prepNP); pp.setPreposition(prepS); // if(Math.random()>.5)
			 * p.addComplement(pp); // creates a random tense if (Math.random()<.33333)
			 * p.setFeature(Feature.TENSE, Tense.PAST); if (Math.random()<.5)
			 * p.setFeature(Feature.TENSE, Tense.FUTURE);
			 * 
			 * }
			 */

			String output = realiser.realiseSentence(p);
		} catch (IOException ex) {

		}

	}

	public Clause(NounPhrase a) {
		try {
			boolean adj = false;// TEMP
			classNoun = a;
			classVerb = new VerbPhrase();
			classVerb.verbOnNoun(a.getClassNoun());
			objectNoun = new NounPhrase();
			if (classVerb.isTransitive()) {

				objectNoun.manyNounObjs((String) classVerb.getClassVerb());
				sent = nlgFactory.createClause(classNoun.getPhrase(), classVerb.getPhrase(), objectNoun.getPhrase());
				if (adj) {// WORK
					System.out.println("May 4");
					sent = nlgFactory.createClause(classNoun.getPhrase(), classVerb.getPhrase(),
							NounPhrase.ranAdj(NounPhrase.nounToAdj(classNoun.getClassNoun()), false));
				}
			} else {
				sent = nlgFactory.createClause(classNoun.getPhrase(), classVerb.getPhrase());
				objectNoun = null;
			}

			classPrep = new PrepPhrase(classVerb, classNoun);
			if (Math.random() < .5)
				sent.addComplement(classPrep.getPrepPhrase());
		} catch (IOException ex) {
			System.out.println("error line 160");
		}

	}

	public Clause(VerbPhrase a)// WORK
	{
		try {
			classVerb = a;
			classNoun = new NounPhrase();
			classNoun.manyNounSbjs(a.getClassVerb());
			if (a.isTransitive()) {
				objectNoun = new NounPhrase();
				objectNoun.manyNounObjs(a.getClassVerb());
				sent = nlgFactory.createClause(classNoun.getPhrase(), classVerb.getPhrase(), objectNoun.getPhrase());
			} else {
				sent = nlgFactory.createClause(classNoun.getPhrase(), classVerb.getPhrase());
			}
			classPrep = new PrepPhrase(classVerb, classNoun);
			if (Math.random() < .5)
				sent.addComplement(classPrep.getPrepPhrase());

		} catch (IOException ex) {

		}
	}

	public void adjClause() {
		try {
			classVerb = new VerbPhrase(VerbPhrase.ranVerb(new String[] { "2.4" }, true));
			sent = nlgFactory.createClause(classNoun.getPhrase(), classVerb.getPhrase(),
					NounPhrase.ranAdj(NounPhrase.nounToAdj(classNoun.getClassNoun()), false));

		} catch (IOException ex) {
		}
	}

	// Adds preposition phrase to the clause
	public void addPrepositionPhrase() throws IOException {// WORK
		PPPhraseSpec pp = null;
		int prepI = (int) (Math.random() * PrepPhrase.posPreps(classVerb.getClassVerb()).split("--").length);// prepI =
																												// instance
																												// of
		// prep in verb Def
		String prepS = PrepPhrase.ranPrep(
				PrepPhrase.posPreps(classVerb.getClassVerb()).split("--")[prepI].split("-")[0].split(",")); // prepS
		// =
		// preposition
		// String
		NPPhraseSpec prepNP = nlgFactory.createNounPhrase(Noun
				.ranNoun(PrepPhrase.posPreps(classVerb.getClassVerb()).split("--")[prepI].split("-")[1].split(",")));// prepositional
		// phrase
		prepNP.setDeterminer(Noun.ranDet());
		System.out.println(realiser.realiseSentence(nlgFactory.createClause(prepNP, null)));
		pp = nlgFactory.createPrepositionPhrase();
		pp.addComplement(prepNP);
		pp.setPreposition(prepS);
		// if(Math.random()>.5)
		sent.addComplement(pp);

	}



	// returns a String [] of Verbs indecies which corelate with the noun
	public SPhraseSpec clauseOnVerb(String v1) throws IOException

	{
		SPhraseSpec p;
		if (VerbPhrase.posNouns(v1).contains("-"))// if Transitive
		{

			String n1 = Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[0].split(","), true);
			String n2 = Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[1].split(","), true);
			NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
			///// next two lines give adj
			for (int i = Probability.likely(); i > 0; i--)
				n1p.addPreModifier(NounPhrase.ranAdj(NounPhrase.nounToAdj(n1), false));
			n1p.setDeterminer(Noun.ranDet());
			NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
			///// next two lines give adj
			for (int i = Probability.likely(); i > 0; i--)
				n2p.addPreModifier(NounPhrase.ranAdj(NounPhrase.nounToAdj(n2), false));
			n2p.setDeterminer(Noun.ranDet());
			p = nlgFactory.createClause(n1p, Noun.word(v1), n2p);
		} else// if intransitive
		{
			String n1 = Noun.ranNoun(VerbPhrase.posNouns(v1).split(","));
			NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1);
			n1p.setDeterminer(Noun.ranDet());
			p = nlgFactory.createClause(n1p, v1);
		}

		String output = realiser.realiseSentence(p);
		return p;
	}

	// returns a clause where the subject is the noun sent
	// pre: n1 is the full length of noun
	public static SPhraseSpec clauseOnNoun(String n1) throws IOException

	{
		SPhraseSpec p;

		{
			String v1 = NounPhrase.nounToVerb(n1)[(int) (Math.random() * (double) (NounPhrase.nounToVerb(n1).length))];
			if ((VerbPhrase.posNouns(v1).contains("-")))// if transitive
			{
				String n2 = Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[1].split(","), true);
				NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
				n1p.setDeterminer("the");
				NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
				///// next two lines give adj
				for (int i = Probability.likely(); i > 0; i--)
					n2p.addPreModifier(NounPhrase.ranAdj(NounPhrase.nounToAdj(n2), false));
				n2p.setDeterminer(Noun.ranDet());
				p = nlgFactory.createClause(n1p, Noun.word(v1), n2p);
			} else// if intransitive
			{
				NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
				n1p.setDeterminer(Noun.ranDet());
				p = nlgFactory.createClause(n1p, Noun.word(v1));
			}
		}

		String output = realiser.realiseSentence(p);
		return p;
	}

}
// EXAMPLES OF WRONG CONSTRUCTION:
/*
 * 
 * James and happy Andrew create a huge game from the candy. under the park.
 * 
 * Emily, the lion and the bird push small Rebecca under the pool. Lauren and
 * skiny, skiny Barbara push a mammal and a fish from a forest. NounPhrase YOOXI
 * NounPhrase YOOXI manyNounSbjs() is Wrong Exception in thread "main"
 * java.lang.NullPointerException at
 * NounPhrase.toNPPhraseSpec(NounPhrase.java:78) at
 * PrepPhrase.<init>(PrepPhrase.java:73) at Clause.<init>(Clause.java:171) at
 * Clause.main(Clause.java:57)
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */