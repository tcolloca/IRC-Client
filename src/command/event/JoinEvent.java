package command.event;

import model.IRCChannel;
import model.IRCUser;
import event.IRCEventListener;

public class JoinEvent implements IRCEvent {

	private IRCUser user;
	private IRCChannel channel;

	public JoinEvent(IRCUser user, IRCChannel channel) {
		if (user == null || channel == null) {
			throw new IllegalArgumentException();
		}
		this.user = user;
		this.channel = channel;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onJoin(user, channel);
	}
}
