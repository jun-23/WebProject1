package com.project1.oauth.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGPTResponse {
	private List<Choice> choices;

	public List<Choice> getChoices() {
		return choices;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public ChatGPTResponse() {
		// TODO Auto-generated constructor stub
	}

	public ChatGPTResponse(List<Choice> choices) {
		this.choices = choices;
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Choice {
		private int index;
		private Message message;

		public Choice() {
			// TODO Auto-generated constructor stub
		}
		
		public Choice(int index, Message message) {
			this.index  = index;
			this.message = message;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public Message getMessage() {
			return message;
		}

		public void setMessage(Message message) {
			this.message = message;
		}

	}
}