package model;

public class IRCTopic {

	private String topic;
	private IRCUser lastModifier;
	private long lastModifiedTime;
	
	
	IRCTopic(String topic, IRCUser lastModifier, long lastModifiedTime) {
		super();
		this.topic = topic;
		this.lastModifier = lastModifier;
		this.lastModifiedTime = lastModifiedTime;
	}


	public String getTopic() {
		return topic;
	}


	public IRCUser getLastModifier() {
		return lastModifier;
	}


	public long getLastModifiedTime() {
		return lastModifiedTime;
	}
}
