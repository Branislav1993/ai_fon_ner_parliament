package rs.ai.fon.controller;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rs.ai.fon.model.Speech;
import rs.ai.fon.model.WordEntity;
import rs.ai.fon.model.network.MentionNetwork;
import rs.ai.fon.ner.NERClassification;

@RestController
@RequestMapping("/api/")
public class NERRestController {

	@PostMapping(value = "/classify")
	public ResponseEntity<Object> classifyText(@RequestBody Speech speech,
			@RequestParam(name = "type", required = false, defaultValue = "") String type) {

		HttpHeaders responseHeaders = new HttpHeaders();
		MediaType plainUtf = new MediaType(MediaType.TEXT_PLAIN, (StandardCharsets.UTF_8));

		switch (type) {
		case "xml":
		case "tsv":
		case "slashTags":
		case "inlineXML":
		case "tabbedEntities":
			responseHeaders.setContentType(plainUtf);
			break;
		default:
			responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
		}
		return new ResponseEntity<Object>(NERClassification.getInstance().classify(speech, type), HttpStatus.OK);
	}

	@PostMapping(value = "/tokenize")
	public ResponseEntity<List<WordEntity>> tokenizeText(@RequestBody Speech speech) {
		return new ResponseEntity<List<WordEntity>>(NERClassification.getInstance().tokenizeAsWordEntities(speech.getText()),
				HttpStatus.OK);
	}

	@PostMapping(value = "/mentions")
	public ResponseEntity<MentionNetwork> getMentionsNetwork(@RequestBody List<Speech> speeches) {
		for (Speech speech : speeches) {
			System.out.println(speech.getMember().getLastName());
		}
		return new ResponseEntity<MentionNetwork>(NERClassification.getInstance().createMentionNetwork(speeches),
				HttpStatus.OK);
	}

}
