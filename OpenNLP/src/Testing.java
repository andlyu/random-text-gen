import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import opennlp.tools.langdetect.Language;
import opennlp.tools.langdetect.LanguageDetector;
import opennlp.tools.langdetect.LanguageDetectorME;
import opennlp.tools.langdetect.LanguageDetectorModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Sequence;
import opennlp.tools.util.Span;

public class Testing {
	public static void main(String[] args) throws IOException {
		POSModel model = null;
		try {
			InputStream modelIn = new FileInputStream("en-pos-maxent.bin");
			model = new POSModel(modelIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		POSTaggerME tagger = new POSTaggerME(model);
		String sent[] = new String[]{"Most", "large", "cities", "in", "the", "US", "had",
                "morning", "and", "afternoon", "newspapers", "."};		  
String[] tags = tagger.tag(sent);
double probs[] = tagger.probs();
Sequence topSequences[] = tagger.topKSequences(sent);
for(int i = 0; i<tags.length; i++) {
	System.out.println(tags[i] + "   ---    "+ sent[i] + "     -----     "+ probs[i]);
}


	}

}
