# random-text-gen

### PRODUCT:
### Read [DEMO.md](https://github.com/andlyu/random-text-gen/blob/master/DEMO.md)

#### purpose:
I wanted a program that could generate random text. The main criteria is that it should be plausible. <br/>
This means that a "*A pencil can fly*" because "*a pencil can fly across a room if somebody threw it*" <br/>
Initially my goal was to start with sentences, and to build up to stories, but I was only able to generate coherent sentances. 

### Continuation
[Merge](https://github.com/andlyu/Merge)<br/>
My attempt to implement NLP into this project

### Database organization

#### Noun.txt <br/>

	1 person 1,2,3,4,5 10.1,10.2,10.2.1
	1.1 child P 10.2
	1.1.1 boy P 10.2.1.2
	1.1.2 girl P 10.2.1.2
	1.1.3 student P 10.2.1.2
	1.1.4 baby P 10.2.1.3

#### Verb.txt <br/>

	1.2.1.1 sense 1,2-1,2,3,4/1.5,1.6-3
	1.2.1.1.1 see 1.1.1,1.1.2,1.1.3,2-1,2,3,4/1.5,1.6-3
	1.2.1.1.2 smell 1,2-1,2,4/1.5,1.6-3
	1.2.1.1.3 hear 1,2-1,2/1.5,1.6-3
	1.2.1.1.4 touch 1,2-1,2,4/1.5,1.6-3

### Explanation

#### Noun.txt format <br/>

<ul>	
<li>The number and period sequence is a words iD</li>
<li>The word</li>
<li>Adjective</li>
<ol>
<li>the Adjective that can be used with a noun. If it doesn't change from it's parent, then the "P" acts as a place holder</li>
</ol>
<li>Groups</li>
<ol>
<li>all groups are start with 10, and they are ways to categorize instead of the order of the IDs</li>
</ol>

</ul>

#### Verb.txt format <br/>

<ul>	
<li>The number and period sequence is a words iD
<li>The word
<li>Properties
<ol>
<li>(All nouns that can be the subjects(separated by commas)) "-" (all nouns that can be objects(separated by commas)

<li>if a verb is intransitive, a dash will not be present
<li>"/" all prepositions that can be used "-" all the nouns where the verb can take place (for the prepositional phrase)
<br/>
<br/>
</ol>
</ul>

### Example

In the example above, if we start "see", for the subject we can choose any nouns that start with the iD 1.1.1, 1.1.2, 1,1,3 and 2.<br/>

	Lets choose "student". <br/>
for the object, we can choose any nouns that start with the iD 1, 2, 3, and 4.<br/>

	lets choose "child"<br>
We will have a preposition. It's iD will start with either 1.5 or 1.6.<br>

	Lets choose "near"
For the place, the Noun's iD must start with a 3.<br>
 
	Lets choose "3.2.1.3 field"
The sentence will end up being:<br/>

	The student sees a child near a field.

SympleNLG does all of the adjustment with placing periods, capitalization, and changing "see" to "sees"<br/>
Thanks SympleNLG


### Conclusion
The clauses turn out being all right, however when connecting multiple clauses, they become too illogical. It could be solved by adding more parameters to the vocab files, but I thought that using NLP to create the text files would be much more efficient. 

## Disclaimer
Sorry that much of the code uses bad practices. Much of it was writen before I knew what to do and what not to do. 

### Citations<br/>
I used SympleNLG for creating the sentences out of the words that I fed to it. 
