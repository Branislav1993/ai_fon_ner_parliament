package rs.ai.fon.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Speech {
	
	private Integer id;

	private String text;
	
	@JsonProperty("creator")
	private Member member;
	
	@JsonProperty("plenarySession.meta.href")
	private String plenarySessionUrl;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yyyy.")
	private Date sessionDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getPlenarySessionUrl() {
		return plenarySessionUrl;
	}

	public void setPlenarySessionUrl(String plenarySessionUrl) {
		this.plenarySessionUrl = plenarySessionUrl;
	}

	public Date getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}

	@Override
	public String toString() {
		return "Speech [id=" + id + ", text=" + text + ", member=" + member + ", plenarySessionUrl=" + plenarySessionUrl
				+ ", sessionDate=" + sessionDate + "]";
	}

}
