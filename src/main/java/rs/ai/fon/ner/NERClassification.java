package rs.ai.fon.ner;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import rs.ai.fon.model.Speech;
import rs.ai.fon.model.WordEntity;
import rs.ai.fon.model.network.Edge;
import rs.ai.fon.model.network.MentionNetwork;
import rs.ai.fon.model.network.Node;

public class NERClassification {

	private AbstractSequenceClassifier<CoreLabel> classifer;
	private static NERClassification instance;
	public static Pattern PERSON_PATTERN = Pattern.compile("<PERSON>(.*?)</PERSON>");

	public static NERClassification getInstance() {
		if (instance == null) {
			instance = new NERClassification();
		}
		return instance;
	}

	public NERClassification() {
		try {
			classifer = CRFClassifier.getClassifier("ner-model-105k.ser.gz");
		} catch (Exception e) {
			System.out.println("COULDN'T GET CLASSIFIER");
			e.printStackTrace();
		}
	}

	public Object classify(Speech speech, String type) {

		switch (type) {
		case "xml":
		case "tsv":
		case "slashTags":
		case "inlineXML":
		case "tabbedEntities":
			speech.setText(classifer.classifyToString(cleanText(speech.getText()), type, false));
			return speech.getText();
		default:
			return tokenizeAndClassifyText(speech.getText());
		}

	}

	public List<CoreLabel> tokenize(String text) {

		List<CoreLabel> tokens = new ArrayList<>();

		text = cleanText(text);

		// creating string reader because of PTBTokenizer input parameter
		StringReader reader = new StringReader(text);

		// creating tokens from text
		PTBTokenizer<CoreLabel> ptbt = new PTBTokenizer<>(reader, new CoreLabelTokenFactory(), "tokenizeNLs=false");
		while (ptbt.hasNext()) {
			CoreLabel cl = ptbt.next();
			tokens.add(cl);
		}
		return tokens;
	}

	private List<WordEntity> tokenizeAndClassifyText(String text) {
		List<WordEntity> entities = new ArrayList<>();
		List<CoreLabel> cls = classifer.classifySentence(tokenize(text));
		for (CoreLabel cl : cls) {
			entities.add(new WordEntity(cl.word(), cl.getString(AnswerAnnotation.class)));
		}
		return entities;
	}

	private String cleanText(String text) {
		text = text.replaceAll("\\.", ". ");
		text = text.replaceAll("\\?", "? ");
		text = text.replaceAll("!", "! ");
		text = text.replaceAll(":", ": ");
		text = text.replaceAll(",", ", ");
		return text;
	}

	public MentionNetwork createMentionNetwork(List<Speech> speeches) {

		List<Node> nodes = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		int generatedId = 0;

		for (Speech s : speeches) {

			Node speechNode = new Node(++generatedId, s.getMember().getName() + " " + s.getMember().getLastName());

			if (!nodes.contains(speechNode)) {
				nodes.add(speechNode);
			} else {
				speechNode = nodes.get(nodes.indexOf(speechNode));
			}

			String classifiedSpeech = (String) classify(s, "inlineXML");

			Matcher m = PERSON_PATTERN.matcher(classifiedSpeech);

			while (m.find()) {

				String label = m.group().split("<PERSON>")[1].split("</PERSON>")[0];
				System.out.println(label);

				Node node = new Node(++generatedId, label);

				boolean exist = false;
				for (Node n : nodes) {
					if (n.getLabel().equals(node.getLabel())) {
						node = n;
						exist = true;
						break;
					}
				}
				if (!exist) {
					nodes.add(node);
				}

				if (speechNode.getId() == node.getId()) {
					continue;
				}
				Edge edge = new Edge(speechNode.getId(), node.getId());
				if (!edges.contains(edge)) {
					edges.add(edge);
				}
			}
		}
		return new MentionNetwork(nodes, edges);
	}

	public List<WordEntity> tokenizeAsWordEntities(String text) {
		List<CoreLabel> cls = tokenize(text);
		List<WordEntity> wes = new ArrayList<>();

		for (CoreLabel cl : cls) {
			wes.add(new WordEntity(cl.word(), null));
		}
		return wes;
	}

}
