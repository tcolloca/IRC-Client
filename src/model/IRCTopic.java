package model;

public class IRCTopic {

	private String topic;
	private IRCUser lastSetter;
	private long lastModifiedTime;
	
	
	IRCTopic(String topic, IRCUser lastSetter, long lastModifiedTime) {
		super();
		this.topic = topic;
		this.lastSetter = lastSetter;
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getTopic() {
		return topic;
	}


	public IRCUser getLastSetter() {
		return lastSetter;
	}


	public long getLastModifiedTime() {
		return lastModifiedTime;
	}
}
