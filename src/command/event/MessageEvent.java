package command.event;

import model.IRCChannel;
import model.IRCUser;
import event.IRCEventListener;

public class MessageEvent implements IRCEvent {

	private IRCUser sender;
	private IRCChannel channel;
	private String message;

	public MessageEvent(IRCUser sender, IRCChannel channel, String message) {
		if (sender == null || message == null) {
			throw new IllegalArgumentException();
		}
		this.sender = sender;
		this.channel = channel;
		this.message = message;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onMessage(sender, message);
		if (channel != null) {			
			listener.onChannelMessage(sender, channel, message);
		} else {			
			listener.onPrivateMessage(sender, message);
		}
	}
}
