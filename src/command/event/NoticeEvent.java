package command.event;

import model.IRCChannel;
import event.IRCEventListener;

public class NoticeEvent implements IRCEvent {

	private IRCChannel channel;
	private String message;

	public NoticeEvent(IRCChannel channel, String message) {
		if (message == null) {

		}
		this.channel = channel;
		this.message = message;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onNotice(channel, message);
	}
}
