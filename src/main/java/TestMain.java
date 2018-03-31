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
         SPhraseSpec ind = Sentance.makeSentance();
         SPhraseSpec dep = Sentance.makeSentance();
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
      Scanner input = new Scanner(TestMain.class.getResourceAsStream(NOUN_FILE));
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
      Scanner input = new Scanner(TestMain.class.getResourceAsStream(NOUN_FILE));
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
   
  
}