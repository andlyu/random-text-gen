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
      String v1 = ranVerb(new String[]{"1","2"},true);
      SPhraseSpec p;
      String n1 = "";
      String n2 = "";
      SPhraseSpec dep = null;
      if(posNouns(v1).contains("-"))// if(verb is transitive)
      {
         ////////sets the subject either new one or the previous sentances;
         if(classNoun == null)
            n1 =  ranNoun(posNouns(v1).split("-")[0].split(","),true);// subject of sentance
         else
            n1 = classNoun; // WORK 
         ////////sets the subject either new one or the previous sentances;
         n2 = ranNoun(posNouns(v1).split("-")[1].split(","),true); // object of sentance
         classNoun = n2; // sets subject of next sentance to n2
         dep = clauseOnNoun(n2);// makes a clause on the second noun
      
         // creates multiple subjects
         CoordinatedPhraseElement n1p = manyNouns(v1);
         NPPhraseSpec n2p = nlgFactory.createNounPhrase(n2.split(" ")[1]);
          ///// next two lines give adj
         for(int i = likely(); i>0; i--) 
            n2p.addPreModifier(ranAdj(nounToAdj(n2),false));
         n2p.setDeterminer(ranDet());
         p = nlgFactory.createClause(n1p, word(v1),n2p);
      }
      else // if verb is intransitive
      {     
         n1 = ranNoun(posNouns(v1).split(","),true);
         dep = clauseOnNoun(n1);// creates clause on the subject
         CoordinatedPhraseElement n1p = manyNouns(v1);
       //  NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1.split(" ")[1]);
       //  n1p.setDeterminer(ranDet());
         p = nlgFactory.createClause(n1p, word(v1));
      }
         // changing preposition
      
      PPPhraseSpec pp = null;
      if(v1.contains("/"))// temporary
      {
         int prepI = (int)( Math.random()*posPreps(v1).split("--").length);// prepI = instance of prep in verb Def
         String prepS = ranPrep(posPreps(v1).split("--")[prepI].split("-")[0].split(",")); // prepS = preposition String
         NPPhraseSpec prepNP = nlgFactory.createNounPhrase(ranNoun(posPreps(v1).split("--")[prepI].split("-")[1].split(",")));// prepositional phrase
         prepNP.setDeterminer(ranDet());
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
   
   public static SPhraseSpec clauseOnVerb(String v1)throws IOException
   
   {
      SPhraseSpec p;
      if(posNouns(v1).contains("-"))// if Transitive
      {
         
         String n1 =  ranNoun(posNouns(v1).split("-")[0].split(","),true);
         String n2 = ranNoun(posNouns(v1).split("-")[1].split(","),true);
         classNoun = n2;
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
         //     if(v1.countains("/"))
         //                 String n1 = ranNoun(v1.split(" ")[2].split("/").split(","));       
         String n1 = ranNoun(posNouns(v1).split(","));
         classNoun = n1; // sets noun to subject
         NPPhraseSpec n1p = nlgFactory.createNounPhrase(n1);
         n1p.setDeterminer(ranDet());
         p = nlgFactory.createClause(n1p, word(v1));
      }
                  
      String output = realiser.realiseSentence(p);
      return p;
   }
   
   // returns a clause where the subject is the noun sent
   // pre: n1 is the full length of noun
   public static SPhraseSpec clauseOnNoun(String n1)throws IOException
   
   {
      SPhraseSpec p;
      
      {
         String v1 = nounToVerb(n1)[(int)(Math.random()*(double)(nounToVerb(n1).length))];
         if((posNouns(v1).contains("-")))// if transitive
         {
            String n2 = ranNoun(posNouns(v1).split("-")[1].split(","),true);
            classNoun = n2;// makes the next sentance noun the object of this sentance
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
   
   // creates a "noun phrase" with many subjects and adjectives around which all agree with v1
   private static CoordinatedPhraseElement manyNouns(String v1)throws IOException
   {
      CoordinatedPhraseElement n1p = new CoordinatedPhraseElement();
      for(int i = likely(); i>=0; i--) 
      {
         String n1 =  ranNoun(posNouns(v1).split("-")[0].split(","),true);// subject of sentance
         NPPhraseSpec n3p = nlgFactory.createNounPhrase(n1.split(" ")[1]);//NEW
         
         if(n1.split(" ")[0].length()>=6 && 
                    !(n1.split(" ")[0].substring(0,6).equals("1.2.2.") 
                           || n1.split(" ")[0].substring(0,6).equals("1.2.1.")))//checks for name
            n3p.setDeterminer(ranDet());
         else if(n1.split(" ")[0].length()<6)
            n3p.setDeterminer(ranDet());// ads determiner for small indexes
                  
      
                     ///// next two lines give adj
         for(int j = likely(); j>0; j--)  
            n3p.addPreModifier(ranAdj(nounToAdj(n1),false));
         n1p.addCoordinate(n3p);
      }
      return n1p;
      
   }
   
}