package com.job.pojo;

public class Message {
	String id;
	String content;


	public Message(String id, String content) {

		this.id = id;
		this.content = content;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "User  (id=" + id + ", content = ;"+content+")";
	}
}