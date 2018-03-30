package workingPack;
import java.io.*;
import java.util.*;
import simplenlg.framework.*;
import simplenlg.lexicon.*;
import simplenlg.realiser.english.*;
import simplenlg.phrasespec.*;
import simplenlg.features.*;

public class PrepPhrase
{
   private NounPhrase place;
   private PPPhraseSpec prepPhrase;
   private String classPreposition;
   private String phrase;

	public static final String PREPOSITION_FILE = "lib/Preposition.txt";
   
   public NounPhrase getPlace(){
      return place;}
   public PPPhraseSpec getPrepPhrase(){
      return prepPhrase;}
   public String getClassPreposition(){
      return classPreposition;}
   public String getPhrase(){
      return this.toString();}
   public void setPlace(NounPhrase a){place = a;}
   public void setPrepPhrase(PPPhraseSpec a){prepPhrase = a;}
   public void setClassPreposition(String a){classPreposition = a;}
   public void setPhrase(String a){phrase = a;}
   public String toString(){
      return realiser.realiseSentence(prepPhrase);//return place.toString();
   }

   private static Lexicon lexicon = Lexicon.getDefaultLexicon();
   private static NLGFactory nlgFactory = new NLGFactory(lexicon);
   private static Realiser realiser = new Realiser(lexicon);

   public PrepPhrase(){
      try{
         classPreposition = ranPrep(new String[]{"1","2","3","4","5"},true); // prepS = preposition String
         place = new NounPhrase();// prepositional phrase//WORK
         prepPhrase = nlgFactory.createPrepositionPhrase();
         prepPhrase.addComplement(place.toNPPhraseSpec());
         prepPhrase.setPreposition(classPreposition.split(" ")[1]);
      }
      catch(IOException ex){}
   }
   
   public PrepPhrase(VerbPhrase a){
      try{
         int prepI = (int)( Math.random()*posPreps(a.getClassVerb()).split("--").length);// prepI = instance of prep in verb Def
         classPreposition = ranPrep(posPreps(a.getClassVerb()).split("--")[prepI].split("-")[0].split(","),true); // prepS = preposition String
         place = new NounPhrase(NounPhrase.ranNoun(posPreps(a.getClassVerb()).split("--")[prepI].split("-")[1].split(","),true));// prepositional phrase
         prepPhrase = nlgFactory.createPrepositionPhrase();
         prepPhrase.setDeterminer(NounPhrase.ranDet());
         prepPhrase.addComplement(place.toNPPhraseSpec());
         prepPhrase.setPreposition(classPreposition.split(" ")[1]);  
      }
      catch(IOException ex){}
   }
   
   // a is the place,
   //Must be transitive for now
   public PrepPhrase(VerbPhrase a, NounPhrase b){
      try{
         int prepI = (int)( Math.random()*posPreps(a.getClassVerb()).split("--").length);// prepI = instance of prep in verb Def
         classPreposition = ranPrep(posPreps(a.getClassVerb()).split("--")[prepI].split("-")[0].split(","),true); // prepS = preposition String
                  if ((a.getClassVerb()).contains("/"))
            place = new NounPhrase(NounPhrase.ranNoun(posPreps(a.getClassVerb()).split("--")[prepI].split("-")[1].split(","),
                                 NounPhrase.upGroups(b.getClassNoun()),true));// prepositional phrase//WORK
         if(place == null){
            System.out.println("Doesn't work: Verb "+a.getClassVerb()+"Noun: "+b.getClassNoun());
            return;
         }
         prepPhrase = nlgFactory.createPrepositionPhrase();
         prepPhrase.setDeterminer(NounPhrase.ranDet());
         prepPhrase.addComplement(place.toNPPhraseSpec());
         prepPhrase.setPreposition(classPreposition.split(" ")[1]);
      }
      catch(IOException ex){
    	  ex.printStackTrace();
         System.out.print("HOOPA");
      }
   }
   
   
   
   
   //////////////////////////////////////////\/
   ///////////////////////////////////////////////
   ///////////////////////////////////////
   //////////////////////////////////////////////////LARGER HELPER METHODS

//Pre: a = array of posible indexes of prepositions
//Post: returns the word
   public static String ranPrep(String [] a)throws IOException
   {
      Scanner input = new Scanner(PrepPhrase.class.getResourceAsStream(PREPOSITION_FILE));
      ArrayList<String> preps= new ArrayList();
      for(int i = 0; i<a.length; i++)
         a[i]=a[i].split("-")[0]; // ugly code, doesn't follow format
      while(input.hasNext())
         preps.add(input.nextLine());
      ArrayList<String> prep= new ArrayList();
      String g ;
      for(int i = 0; i< preps.size(); i++)
      {////////Goes through verbs
         if(wordWorks(preps.get(i),a))
            prep.add(preps.get(i));
      }/////////////////ends the verbs
      return prep.get((int)(Math.random()*prep.size())).split(" ")[1];
   }
   
   //Pre: a = array of posible indexes of prepositions; Whole determines whether it is full identification(t = full)
//Post: returns the word at one of the indexes
   public static String ranPrep(String [] a, boolean whole)throws IOException
   {
      Scanner input = new Scanner(PrepPhrase.class.getResourceAsStream(PREPOSITION_FILE));
      ArrayList<String> preps= new ArrayList();
      for(int i = 0; i<a.length; i++)
         a[i]=a[i].split("-")[0]; // ugly code, doesn't follow format
      while(input.hasNext())
         preps.add(input.nextLine());
      ArrayList<String> prep= new ArrayList();
      String g ;
      for(int i = 0; i< preps.size(); i++)
      {////////Goes through verbs
         if(wordWorks(preps.get(i),a))
            prep.add(preps.get(i));
      }/////////////////ends the verbs
      if (!whole)
         return prep.get((int)(Math.random()*prep.size())).split(" ")[1];
      return prep.get((int)(Math.random()*prep.size()));
   }
   
   
   //////////////////////////////////////////////////////////////////////////////////////
   /////////////////////////////////////////////////////////////////////////////////////////
   //////////////////////////////////////////////////////////////////////////////////////////
   ////////////////////////////////////////////////////////////////////////////////////////
   //SMALLER HELPER METHODS
         // returs true is a has the bigining of noun
   private static boolean wordWorks(String word, String [] a)
   {
      if(a ==null)
         return true;
      for(int i = 0; i<a.length; i++)//RUN ERR
      {
         if(!(word.length()<a[i].length()) && a[i].equals(word.substring(0,a[i].length())))
            return true; 
      }
      return false;
   }
   
    //returns the preposition-pairing part of a verb defention
   public static String posPreps(String a)
   {
      String out = a.split(" ")[2];
      if(out.contains("/"))
         out = out.split("/")[1];
      return out;
   
   }
	// retruns prep with certain ID
	private static String certPrep(String a) throws IOException {
		Scanner input = new Scanner(PrepPhrase.class.getResourceAsStream(PREPOSITION_FILE));
		String prep;
		while (input.hasNext()) {
			prep = input.nextLine();
			if (prep.split(" ")[0].equals(a))
				return prep.split(" ")[1];
		}
		return null;
	}
	

   


   

      
}
