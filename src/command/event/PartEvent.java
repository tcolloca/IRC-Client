package command.event;

import model.IRCChannel;
import model.IRCUser;
import event.IRCEventListener;

public class PartEvent implements IRCEvent {

	private IRCUser user;
	private IRCChannel channel;
	private String message;

	public PartEvent(IRCUser user, IRCChannel channel, String message) {
		if (user == null || channel == null) {
			throw new IllegalArgumentException();
		}
		this.user = user;
		this.channel = channel;
		this.message = message;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onPart(user, channel, message);
	}
}
