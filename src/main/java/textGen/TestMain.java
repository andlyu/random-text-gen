package textGen;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.util.*;
import java.io.*;


// things to do
// classNoun
public class TestMain {
   private static String classNoun;

   private static Lexicon lexicon = Lexicon.getDefaultLexicon();
   private static NLGFactory nlgFactory = new NLGFactory(lexicon);
   private static Realiser realiser = new Realiser(lexicon);
     
	public static final String ADJECTIVE_FILE = "lib/Adjective.txt";
	public static final String NOUN_FILE = "lib/Noun.txt";
	public static final String PREPOSITION_FILE = "lib/Preposition.txt";
	public static final String VERB_FILE = "lib/Verb.txt";
   
   public static void main(String[] args)throws IOException {
   
      for(int i = 0; i<150; i++)
      {
         SPhraseSpec ind = makeSentance();
         SPhraseSpec dep = makeSentance();
         if(Math.random()>.5)
            dep.setFeature(Feature.COMPLEMENTISER, "because");
         else
            dep.setFeature(Feature.COMPLEMENTISER, "while");
             
        // ind.addComplement(dep);
         String output = realiser.realiseSentence(ind);
         
         System.out.println(output);
      }
   
     // }
   
   }


   public static String ranPlace()throws IOException
   {
      Scanner input = new Scanner(new FileReader(NOUN_FILE));
      ArrayList<String> nouns= new ArrayList();
      while(input.hasNext())
         nouns.add(input.nextLine());
      ArrayList<String> places= new ArrayList();
      for(int i = 0; i< nouns.size(); i++)
      {      
         if((nouns.get(i).split(" ")[0]).split("\\.")[0].equals("3"))
            places.add(nouns.get(i));
      }
      return places.get((int)(Math.random()*places.size())).split(" ")[1];
   }
   
   public static String ranLife()throws IOException
   {
      Scanner input = new Scanner(new FileReader(NOUN_FILE));
      ArrayList<String> nouns= new ArrayList();
      while(input.hasNext())
         nouns.add(input.nextLine());
      ArrayList<String> life= new ArrayList();
      String g ;
      for(int i = 0; i< nouns.size(); i++)
      {
         g = (nouns.get(i).split(" ")[0]).split("\\.")[0];
         if(g.equals("1")||g.equals("2"))
            life.add(nouns.get(i));
      }
      return life.get((int)(Math.random()*life.size())).split(" ")[1];
   }
   
    
   public static SPhraseSpec makeSentance() throws IOException   {    
      String v1 = VerbPhrase.ranVerb(new String[]{"1","2"},true);
      SPhraseSpec p;
      String n1 = "";
      String n2 = "";
      SPhraseSpec dep = null;
      if(VerbPhrase.posNouns(v1).contains("-"))// if(verb is transitive)
      {
         ////////sets the subject either new one or the previous sentances;
         if(classNoun == null)
            n1 =  Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[0].split(","),true);// subject of sentance
         else
            n1 = classNoun; // WORK 
         ////////sets the subject either new one or the previous sentances;
         n2 = Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[1].split(","),true); // object of sentance
         classNoun = n2; // sets subject of next sentance to n2
         dep = Clause.clauseOnNoun(n2);// makes a clause on the second noun
         // creates multiple subjects
         CoordinatedPhraseElement n1p = Sentance.manyNouns(v1);
         NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
          ///// next two lines give adj
         for(int i = Probability.likely(); i>0; i--) 
            n2p.addPreModifier(NounPhrase.ranAdj(NounPhrase.nounToAdj(n2),false));
         n2p.setDeterminer(Noun.ranDet());
         p = nlgFactory.createClause(n1p, Noun.word(v1),n2p);
      }
      else // if verb is intransitive
      {     
         //     if(v1.countains("/"))
         //                 String n1 = ranNoun(v1.split(" ")[2].split("/").split(","));       
         n1 = Noun.ranNoun(VerbPhrase.posNouns(v1).split(","),true);
         dep = Clause.clauseOnNoun(n1);// creates clause on the subject
         NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
         n1p.setDeterminer(Noun.ranDet());
         p = nlgFactory.createClause(n1p, Noun.word(v1));
      }
         // changing preposition
      
      PPPhraseSpec pp = null;
      if(v1.contains("/"))// temporary
      {
         int prepI = (int)( Math.random()*PrepPhrase.posPreps(v1).split("--").length);// prepI = instance of prep in verb Def
         String prepS = PrepPhrase.ranPrep(PrepPhrase.posPreps(v1).split("--")[prepI].split("-")[0].split(",")); // prepS = preposition String
         NPPhraseSpec prepNP = nlgFactory.createNounPhrase(Noun.ranNoun(PrepPhrase.posPreps(v1).split("--")[prepI].split("-")[1].split(",")));// prepositional phrase
         prepNP.setDeterminer(Noun.ranDet());
         pp = nlgFactory.createPrepositionPhrase();
         pp.addComplement(prepNP);
         pp.setPreposition(prepS);
          //  if(Math.random()>.5)
         p.addComplement(pp);
            // creates a random tense
         if (Math.random()<.33333)
            p.setFeature(Feature.TENSE, Tense.PAST);
         if (Math.random()<.5)
            p.setFeature(Feature.TENSE, Tense.FUTURE);
          //  p.setFeature(Feature.COMPLEMENTISER, "because");
         // creates random tense
      }
      if(dep!= null)
      {
         if(Math.random()>.5)
            dep.setFeature(Feature.COMPLEMENTISER, "and");
         if(Math.random()>.5)
            dep.setFeature(Feature.COMPLEMENTISER, "because");
         else
            dep.setFeature(Feature.COMPLEMENTISER, "while");
            
            
         p.addComplement(dep);
      }
      
            
            
      
      else{
         /*/ first prep preposition
            NPPhraseSpec place = nlgFactory.createNounPhrase(ranNoun(new String[]{"3."}));// prepositional phrase
            place.setDeterminer(ranDet());
            pp = nlgFactory.createPrepositionPhrase();
            pp.addComplement(place);
            pp.setPreposition("in");/*///works wrong
      }
       //  if(Math.random()>.5)
         //p.addComplement(pp);
            
      String output = realiser.realiseSentence(p);
      return p;
   }
   
  
   
}