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
   
   public NounPhrase getClassNoun(){       
      return classNoun;}//just Basics
   public VerbPhrase getClassVerb(){       
      return classVerb;}
   public SPhraseSpec getSent(){           
      return sent;}
   public NounPhrase getObjectNoun(){      
      return objectNoun;}
   public PrepPhrase getClassPrep(){
      return classPrep;}
   public void setClassNoun(NounPhrase a){classNoun = a;}
   public void setClassVerb(VerbPhrase a){classVerb = a;}
   public void setSent(SPhraseSpec a){sent = a;}
   public void setObjectNoun(NounPhrase a){objectNoun = a;}
   public void setClassPrep(PrepPhrase a){classPrep = a;}
   public String toString(){
      return (realiser.realiseSentence(sent));}
   
      
   public static void main(String[] args)throws IOException {
	   Scanner input = new Scanner(new FileReader("Adjective.txt"));

   
      System.out.println();
      Clause c = new Clause();
      Clause d = new Clause();
   
      for(int i = 0; i<10000; i++)
      {
      
         if(Math.random()<.5){
            if (d.getObjectNoun() != null){
               if(Math.random()<.5){
                  c = new Clause(d.getObjectNoun());//waistfull
                  c.adjClause();
                  System.out.println(c.toString());
               }
            }
            d.adjClause();
         }
         else if (d.getObjectNoun() != null)
            d = new Clause(d.getObjectNoun());
         else if(Math.random()<.5) {
       
            d = new Clause(d.getClassNoun());
      }
      else {
      System.out.println();
      d = new Clause();
      }
      
      
         System.out.println(d.toString());
      
      }
   
      for(int i = 0; i<0; i++)//TEMPorary
      {
         SPhraseSpec ind = makeSentance();
         Clause a = new Clause();
         System.out.println(a.getClassVerb());
         System.out.println(a.getClassNoun());
         System.out.println(realiser.realiseSentence(a.getSent()));
         Clause b = new Clause();//WORK rework clauseOnNoun()
         b.setSent(clauseOnNoun(a.getClassNoun().getClassNoun()));
         System.out.println(realiser.realiseSentence(b.getSent()));
        
      }
   
     // }
   
   }
   public Clause()//catch IOException
   {
      try 
      {
         VerbPhrase v2 = new VerbPhrase();
         classVerb = v2;               // Sets classVerb
         SPhraseSpec p;
      //      SPhraseSpec dep = null;
         if(posNouns(v2.getClassVerb()).contains("-"))// if(verb is transitive)
         {
         
         // creates multiple subjects
            NounPhrase a = new NounPhrase();//UGLY
            a.manyNouns(v2.getClassVerb());
            classNoun = a;
           
         //DirectObject
         
            NounPhrase b = new NounPhrase();//WORK
            b.manyNounObjs((String)v2.getClassVerb());//CHECK
            objectNoun = b;
                  
         
         
            p = nlgFactory.createClause(a.getPhrase(),v2.getPhrase(),b.getPhrase());
            sent = p;
         }
         else // if verb is intransitive
         {     
         // creates multiple subjects
            NounPhrase a = new NounPhrase();
            CoordinatedPhraseElement n1p = a.manyNouns(v2.getClassVerb());
            classNoun = a;
            p = nlgFactory.createClause(n1p, v2.getPhrase());
            sent = p;
         /*            if(groups!=null && groups.length != 0 && groups[0].equals("10.1")){//BAD{
               sent = nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase(),NounPhrase.ranAdj(NounPhrase.nounToAdj(classNoun.getClassNoun()),false));
            }*/
         }
         // changing preposition
         classPrep = new PrepPhrase(classVerb,classNoun);
         if(Math.random()<.5)
            sent.addComplement(classPrep.getPrepPhrase());
         
         
         
         
         /*
         if(v2.getClassVerb().contains("/"))// temporary
         {
            int prepI = (int)( Math.random()*posPreps(v2.getClassVerb()).split("--").length);// prepI = instance of prep in verb Def
            String prepS = ranPrep(posPreps(v2.getClassVerb()).split("--")[prepI].split("-")[0].split(",")); // prepS = preposition String
            NPPhraseSpec prepNP = nlgFactory.createNounPhrase(ranNoun(posPreps(v2.getClassVerb()).split("--")[prepI].split("-")[1].split(",")));// prepositional phrase
            prepNP.setDeterminer(ranDet());
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
         
         } */     
            
         String output = realiser.realiseSentence(p);
      }
      catch (IOException ex) 
      {
      
      }
   
   }
   
   public Clause(NounPhrase  a)
   {
      try{
         boolean adj = false;//TEMP
         classNoun = a;
         classVerb = new VerbPhrase();
         classVerb.verbOnNoun(a.getClassNoun());
         objectNoun = new NounPhrase();
         if(classVerb.isTransitive()){
            
            objectNoun.manyNounObjs((String)classVerb.getClassVerb());
            sent =  nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase(),objectNoun.getPhrase()); 
            if(adj){//WORK 
               System.out.println("May 4");
               sent = nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase(),NounPhrase.ranAdj(NounPhrase.nounToAdj(classNoun.getClassNoun()),false));
            }
         }
         else{
            sent =  nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase());
            objectNoun = null;
         }
         
         classPrep = new PrepPhrase(classVerb,classNoun);
         if(Math.random()<.5)
            sent.addComplement(classPrep.getPrepPhrase());
      }
      catch(IOException ex) 
      {
         System.out.println("error line 160");
      }
   
   }
   
   public Clause(VerbPhrase a)// WORK
   {
      try{
         classVerb = a;
         classNoun = new NounPhrase();
         classNoun.manyNounSbjs(a.getClassVerb());
         if(a.isTransitive()){         
            objectNoun = new NounPhrase();
            objectNoun.manyNounObjs(a.getClassVerb());
            sent =  nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase(),objectNoun.getPhrase()); 
         } 
         else{
            sent =  nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase());
         }
         classPrep = new PrepPhrase(classVerb,classNoun);
         if(Math.random()<.5)
            sent.addComplement(classPrep.getPrepPhrase());
         
         
      }
      catch(IOException ex)
      {
      
      }
   }
   
   public void adjClause(){
      try{
         classVerb = new VerbPhrase(VerbPhrase.ranVerb(new String[]{"2.4"},true));
         sent = nlgFactory.createClause(classNoun.getPhrase(),classVerb.getPhrase(),
            NounPhrase.ranAdj(NounPhrase.nounToAdj(classNoun.getClassNoun()),false));
      
      }
      catch(IOException ex){}
   }
   
   public static String ranDet()
   {
      if(Math.random()<.5)
         return "a";
      return "the";
   }
    
   public static String word(String a)
   {
      return a.split(" ")[1];
   }
   
   //Adds preposition phrase to the clause
   public void addPrepositionPhrase()throws IOException{//WORK
      PPPhraseSpec pp = null;
      int prepI = (int)( Math.random()*posPreps(classVerb.getClassVerb()).split("--").length);// prepI = instance of prep in verb Def
      String prepS = ranPrep(posPreps(classVerb.getClassVerb()).split("--")[prepI].split("-")[0].split(",")); // prepS = preposition String
      NPPhraseSpec prepNP = nlgFactory.createNounPhrase(ranNoun(posPreps(classVerb.getClassVerb()).split("--")[prepI].split("-")[1].split(",")));// prepositional phrase
      prepNP.setDeterminer(ranDet());
      System.out.println(realiser.realiseSentence(nlgFactory.createClause(prepNP, null)));
      pp = nlgFactory.createPrepositionPhrase();
      pp.addComplement(prepNP);
      pp.setPreposition(prepS);
          //  if(Math.random()>.5)
      sent.addComplement(pp);
   
   
   }
    
   public static String ranNoun()throws IOException
   {
      Scanner input = new Scanner(new FileReader("Noun.txt"));
      ArrayList<String> nouns= new ArrayList();
      while(input.hasNext())
         nouns.add(input.nextLine());
      return nouns.get((int)(Math.random()*nouns.size())).split(" ")[1];
   }
   
   public static String ranPlace()throws IOException
   {
      Scanner input = new Scanner(new FileReader("Noun.txt"));
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
      Scanner input = new Scanner(new FileReader("Noun.txt"));
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
   
   // returns a noun whose identification begins with one of the a array numbers; returns only 1 word
   public static String ranNoun(String [] a)throws IOException
   {
      Scanner input = new Scanner(new FileReader("Noun.txt"));
      ArrayList<String> nouns= new ArrayList();
      while(input.hasNext())
         nouns.add(input.nextLine());
      ArrayList<String> noun= new ArrayList();
      String g ;
      for(int i = 0; i< nouns.size(); i++)
      {////////Goes through nouns
         if(wordWorks(nouns.get(i),a))
            noun.add(nouns.get(i));
      }/////////////////ends the nouns
      return noun.get((int)(Math.random()*noun.size())).split(" ")[1];
   }
   //whole means whetehr to return whole noune defenition
   public static String ranNoun(String [] a, boolean whole)throws IOException
   {
      Scanner input = new Scanner(new FileReader("Noun.txt"));
      ArrayList<String> nouns= new ArrayList();
      while(input.hasNext())
         nouns.add(input.nextLine());
      ArrayList<String> noun= new ArrayList();
      String g ;
      for(int i = 0; i< nouns.size(); i++)
      {////////Goes through nouns
         if(wordWorks(nouns.get(i),a))
            noun.add(nouns.get(i));
      }/////////////////ends the nouns
      if(!whole)
         return noun.get((int)(Math.random()*noun.size())).split(" ")[1];
      return noun.get((int)(Math.random()*noun.size()));
   }
   
   public static String ranVerb(String [] a, boolean whole)throws IOException
   {
      Scanner input = new Scanner(new FileReader("Verb.txt"));
      ArrayList<String> verbs= new ArrayList();
      while(input.hasNext())
         verbs.add(input.nextLine());
      ArrayList<String> verb= new ArrayList();
      String g ;
      for(int i = 0; i< verbs.size(); i++)
      {////////Goes through verbs
         if(wordWorks(verbs.get(i),a))
            verb.add(verbs.get(i));
      }/////////////////ends the verbs
      if (!whole)
         return verb.get((int)(Math.random()*verb.size())).split(" ")[1];
      return verb.get((int)(Math.random()*verb.size()));
   }
   
   //returns a random Preposition
   
   public static String ranPrep(String [] a)throws IOException
   {
      Scanner input = new Scanner(new FileReader("Preposition.txt"));
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
      
   public static String ranPrep(String [] a, boolean whole)throws IOException
   {
      Scanner input = new Scanner(new FileReader("Preposition.txt"));
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
   
   public static String ranAdj(String [] a, boolean whole)throws IOException
   {
      Scanner input = new Scanner(new FileReader("Adjective.txt"));
      ArrayList<String> verbs= new ArrayList();
      while(input.hasNext())
         verbs.add(input.nextLine());
      ArrayList<String> verb= new ArrayList();
      String g ;
      for(int i = 0; i< verbs.size(); i++)
      {////////Goes through verbs
         if(wordWorks(verbs.get(i),a))
            verb.add(verbs.get(i));
      }/////////////////ends the verbs
      if (!whole)
         return verb.get((int)(Math.random()*verb.size())).split(" ")[1];
      return verb.get((int)(Math.random()*verb.size()));
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
   
      //returns the Noun-pairing part of a verb defention
   private static String posNouns(String a)
   {
      String out = a.split(" ")[2];
      if(out.contains("/"))
         out = out.split("/")[0];
      return out;
   }
   
   //retruns prep with certain ID
   private static String certPrep(String a) throws IOException
   {
      Scanner input = new Scanner(new FileReader("Preposition.txt"));
      String prep;
      while(input.hasNext())
      {
         prep = input.nextLine();
         if(prep.split(" ")[0].equals(a))
            return prep.split(" ")[1];
      }
      return null;
   }
   
   //returns the preposition-pairing part of a verb defention
   private static String posPreps(String a)
   {
      String out = a.split(" ")[2];
      if(out.contains("/"))
         out = out.split("/")[1];
      return out;
   
   }
   // returns a String [] of adjective indecies which corelate with the noun
   // I THINK THIS IS WRONG
   public static String[] nounToAdj(String n)throws IOException
   {
   
      String iD = n.split(" ")[0];
      String [] iDs = iD.split("\\.");//makes the array of the total number of IDs, not the right iDs
      int count = 0;
      iDs[count++] = iD;
      while(iD.contains("."))
      {
         iD = iD.substring(0,iD.lastIndexOf("."));
         iDs[count++] = iD;
      }
   
      ArrayList all = new ArrayList();
      Scanner input= new Scanner (new FileReader("Noun.txt"));
      {
         String a = null; // to store input
         String [] some = null; // some of the adjective IDs
         count = iDs.length - 1;
         while(input.hasNextLine()&&count> -1)
         {
            a = input.nextLine();
            if (a.startsWith(iDs[count]+" ")||a.startsWith("-"+iDs[count]+ " "))
            {
               if(a.split(" ").length>2)
                  some = a.split(" ")[2].split(",");
               for(int i = 0; i<some.length; i++)
                  all.add(some[i]);
               count--;
               
            }    
         }
      }
      String [] out = new String [all.size()];//to be returned in String [] format
      for(int i = 0; i<out.length;i++)
         out[i] = (String)all.get(i); // could make the String earlier
      return out;
   }
   
   // returns a String [] of Verbs indecies which corelate with the noun
   public static String[] nounToVerb(String n)throws IOException
   {
   
      String iD = n.split(" ")[0];
      String [] iDs = iD.split("\\.");//makes the array of the total number of IDs, not the right iDs
      int count = 0;
      iDs[count++] = iD;
      while(iD.contains("."))
      {
         iD = iD.substring(0,iD.lastIndexOf("."));
         iDs[count++] = iD; // noun IDs
      }
   
      ArrayList all = new ArrayList();
      Scanner input= new Scanner (new FileReader("Verb.txt"));
      {
         String a = null; // to store input
         String [] some = null; // some of the adjective IDs
         count = iDs.length - 1;
         while(input.hasNextLine())
         {
            a = input.nextLine();
            if(a.charAt(0)!='-')
            {
               String [] pos = a.split(" ")[2].split("/")[0].split("-")[0].split(",");// arrayList containing posible Nouns
               for(int i = 0; i<iDs.length; i++)
                  for(int j = 0; j<pos.length; j++)
                     if(pos[j].equals(iDs[i]))
                     {
                        all.add(a);
                        break;
                     }
            }
         }
      }
      String [] out = new String [all.size()];//to be returned in String [] format
      for(int i = 0; i<out.length;i++)
         out[i] = (String)all.get(i); // could make the String earlier
      return out;
   }

   
   public static SPhraseSpec makeSentance() throws IOException   {    
      VerbPhrase v2 = new VerbPhrase();
      SPhraseSpec p;
      String n2 = "";
      SPhraseSpec dep = null;
      if(posNouns(v2.getClassVerb()).contains("-"))// if(verb is transitive)
      {
         ////////sets the subject either new one or the previous sentances;
         ////////sets the subject either new one or the previous sentances;
      
         // creates multiple subjects
         NounPhrase a = new NounPhrase();
         a.manyNouns(v2.getClassVerb());
         NounPhrase b = new NounPhrase();   
         n2 = ranNoun(posNouns(v2.getClassVerb()).split("-")[1].split(","),true); // object of sentance
         NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
      
      
         p = nlgFactory.createClause(a.getPhrase(),v2.getPhrase(),n2p);
      }
      else // if verb is intransitive
      {     
         // creates multiple subjects
         NounPhrase a = new NounPhrase();
         CoordinatedPhraseElement n1p = a.manyNouns(v2.getClassVerb());
         p = nlgFactory.createClause(n1p, v2.getPhrase());
      }
         // changing preposition
      
      PPPhraseSpec pp = null;
      if(v2.getClassVerb().contains("/"))// temporary
      {
         int prepI = (int)( Math.random()*posPreps(v2.getClassVerb()).split("--").length);// prepI = instance of prep in verb Def
         String prepS = ranPrep(posPreps(v2.getClassVerb()).split("--")[prepI].split("-")[0].split(",")); // prepS = preposition String
         NPPhraseSpec prepNP = nlgFactory.createNounPhrase(ranNoun(posPreps(v2.getClassVerb()).split("--")[prepI].split("-")[1].split(",")));// prepositional phrase
         prepNP.setDeterminer(ranDet());
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
      
      }      
            
      String output = realiser.realiseSentence(p);
      return p;
   }
   
   public  SPhraseSpec clauseOnVerb(String v1)throws IOException
   
   {
      SPhraseSpec p;
      if(posNouns(v1).contains("-"))// if Transitive
      {
         
         String n1 =  ranNoun(posNouns(v1).split("-")[0].split(","),true);
         String n2 = ranNoun(posNouns(v1).split("-")[1].split(","),true);
         NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
              ///// next two lines give adj
         for(int i = likely(); i>0; i--) 
            n1p.addPreModifier(ranAdj(nounToAdj(n1),false));
         n1p.setDeterminer(ranDet());
         NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
              ///// next two lines give adj
         for(int i = likely(); i>0; i--) 
            n2p.addPreModifier( ranAdj(nounToAdj(n2),false));
         n2p.setDeterminer(ranDet());
         p = nlgFactory.createClause(n1p, word(v1),n2p);
      }
      else// if intransitive
      {     
         String n1 = ranNoun(posNouns(v1).split(","));
         NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1);
         n1p.setDeterminer(ranDet());
         p = nlgFactory.createClause(n1p, v1);
      }
                  
      String output = realiser.realiseSentence(p);
      return p;
   }
   
   // returns a clause where the subject is the noun sent
   // pre: n1 is the full length of noun
   public  static SPhraseSpec clauseOnNoun(String n1)throws IOException
   
   {
      SPhraseSpec p;
      
      {
         String v1 = nounToVerb(n1)[(int)(Math.random()*(double)(nounToVerb(n1).length))];
         if((posNouns(v1).contains("-")))// if transitive
         {
            String n2 = ranNoun(posNouns(v1).split("-")[1].split(","),true);
            NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
            n1p.setDeterminer("the");
            NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
                 ///// next two lines give adj
            for(int i = likely(); i>0; i--) 
               n2p.addPreModifier( ranAdj(nounToAdj(n2),false));
            n2p.setDeterminer(ranDet());
            p = nlgFactory.createClause(n1p, word(v1),n2p);
         }
         else// if intransitive
         {
            NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
            n1p.setDeterminer(ranDet());
            p = nlgFactory.createClause(n1p, word(v1));
         }
      }
   
                 
      String output = realiser.realiseSentence(p);
      return p;
   }
   
   // standard factor is .3
   // for the pourpose of having many 1s, few 2s and even less 3s
   private static int likely()
   {
      int out = 0;
      double rand = Math.random();
      double factor = .4;
      double curCompare = factor;
      while(rand<curCompare)
      {
         curCompare*=factor;
         curCompare*=factor;
         out++;
      }
      return out;
   }
   
   private static int likely(double factor)
   {
      int out = 0;
      double rand = Math.random();
      double curCompare = factor;
      while(rand<curCompare)
      {
         curCompare*=factor;
         out++;
      }
      return out;
   }
   


   
 
}
// EXAMPLES OF WRONG CONSTRUCTION:
/*

 James and happy Andrew create a huge game from the candy.
 under the park.
 
     Emily, the lion and the bird push small Rebecca under the pool.
 Lauren and skiny, skiny Barbara push a mammal and a fish from a forest.
 NounPhrase YOOXI
 NounPhrase YOOXI
 manyNounSbjs() is Wrong
 Exception in thread "main" java.lang.NullPointerException
 	at NounPhrase.toNPPhraseSpec(NounPhrase.java:78)
 	at PrepPhrase.<init>(PrepPhrase.java:73)
 	at Clause.<init>(Clause.java:171)
 	at Clause.main(Clause.java:57)








*/