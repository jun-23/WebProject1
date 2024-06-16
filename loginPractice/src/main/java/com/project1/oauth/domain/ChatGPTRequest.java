package com.project1.oauth.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ChatGPTRequest { // request 목적
	private String model;
	private List<Message> messages;

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public ChatGPTRequest(String model, String prompt) {
		this.model = model;
		this.messages = new ArrayList<>();
		this.messages.add(new Message("user", prompt));
	}
}
