package rs.ai.fon.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class WordEntity {

	private String word;
	private String label;

	public WordEntity(String word, String label) {
		this.word = word;
		this.label = label;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return "WordEntity [word=" + word + ", label=" + label + "]";
	}

}
