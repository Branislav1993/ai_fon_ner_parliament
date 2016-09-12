package rs.ai.fon.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Member {

	@JsonProperty("firstName")
	private String name;
	
	private String lastName;
	
	@JsonProperty("meta.href")
	private String apiUrl;

	public Member(String name, String lastName, String apiUrl) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.apiUrl = apiUrl;
	}

	public Member() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	@Override
	public String toString() {
		return "Member [name=" + name + ", lastName=" + lastName + ", apiUrl=" + apiUrl + "]";
	}

}
