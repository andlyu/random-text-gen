package textGen;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;
import java.util.*;
import java.io.*;



public class Sentance {
   private static String classNoun;

   private static Lexicon lexicon = Lexicon.getDefaultLexicon();
   private static NLGFactory nlgFactory = new NLGFactory(lexicon);
   private static Realiser realiser = new Realiser(lexicon);
      
	public static final String NOUN_FILE = "lib/Noun.txt";
	public static final String PREPOSITION_FILE = "lib/Preposition.txt";
   
   public static void main(String[] args)throws IOException {
   
      for(int i = 0; i<50; i++)
      {
         SPhraseSpec ind = makeSentance();
      
             
        // ind.addComplement(dep);
         String output = realiser.realiseSentence(ind);
         
         System.out.println(output);
         
      }

     // }
   
   }

  
   public static String ranPlace()throws IOException
   {
      Scanner input = new Scanner(Sentance.class.getResourceAsStream(NOUN_FILE));
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
      Scanner input = new Scanner(Sentance.class.getResourceAsStream(NOUN_FILE));
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
   

      
 
      // returs true is a has the bigining of noun
   private static boolean wordWorks(String word, String [] a)
   {
      for(int i = 0; i<a.length; i++)
      {
         if(!(word.length()<a[i].length()) && a[i].equals(word.substring(0,a[i].length())))
            return true; 
      }
      return false;
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
         CoordinatedPhraseElement n1p = manyNouns(v1);
         NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
          ///// next two lines give adj
         for(int i = Probability.likely(); i>0; i--) 
            n2p.addPreModifier(NounPhrase.ranAdj(NounPhrase.nounToAdj(n2),false));
         n2p.setDeterminer(Noun.ranDet());
         p = nlgFactory.createClause(n1p, Noun.word(v1),n2p);
      }
      else // if verb is intransitive
      {     
         n1 = Noun.ranNoun(VerbPhrase.posNouns(v1).split(","),true);
         dep = Clause.clauseOnNoun(n1);// creates clause on the subject
         CoordinatedPhraseElement n1p = manyNouns(v1);
       //  NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
       //  n1p.setDeterminer(ranDet());
         p = nlgFactory.createClause(n1p, Noun.word(v1));
      }
         // changing preposition
      
      PPPhraseSpec pp = null;
      if(v1.contains("/"))// temporary
      {
         int prepI = (int)( Math.random()*PrepPhrase.posPreps(v1).split("--").length);// prepI = instance of prep in verb Def
         String prepS = PrepPhrase.ranPrep(PrepPhrase.posPreps(v1).split("--")[prepI].split("-")[0].split(",")); // prepS = preposition String
         NPPhraseSpec prepNP = nlgFactory.createNounPhrase(NounPhrase.ranNoun(PrepPhrase.posPreps(v1).split("--")[prepI].split("-")[1].split(",")));// prepositional phrase
         prepNP.setDeterminer(Noun.ranDet());
         pp = nlgFactory.createPrepositionPhrase();
         pp.addComplement(prepNP);
         pp.setPreposition(prepS);
          //  if(Math.random()>.5)
         p.addComplement(pp);
            // creates a random tense
         if (Math.random()<.33333)//ONLY IF TRANSITIVE DOES THE TENSE CHANGE
            p.setFeature(Feature.TENSE, Tense.PAST);
         if (Math.random()<.5)
            p.setFeature(Feature.TENSE, Tense.FUTURE);
      
      }      
            
      String output = realiser.realiseSentence(p);
      return p;
   }
   
  
   // creates a "noun phrase" with many subjects and adjectives around which all agree with v1
   private static CoordinatedPhraseElement manyNouns(String v1)throws IOException
   {
      CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
      for(int i = Probability.likely(); i>=0; i--) 
      {
         String n1 =  Noun.ranNoun(VerbPhrase.posNouns(v1).split("-")[0].split(","),true);// subject of sentance
         NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);//NEW
         
         if(n1.split(" ")[0].length()>=6 && 
                    !(n1.split(" ")[0].substring(0,6).equals("1.2.2.") 
                           || n1.split(" ")[0].substring(0,6).equals("1.2.1.")))//checks for name
            n3p.setDeterminer(Noun.ranDet());
         else if(n1.split(" ")[0].length()<6)
            n3p.setDeterminer(Noun.ranDet());// ads determiner for small indexes
                  
      
                     ///// next two lines give adj
         for(int j = Probability.likely(); j>0; j--)  
            n3p.addPreModifier(NounPhrase.ranAdj(NounPhrase.nounToAdj(n1),false));
         n1p.addCoordinate(n3p);
      }
      return n1p;
      
   }
   
}