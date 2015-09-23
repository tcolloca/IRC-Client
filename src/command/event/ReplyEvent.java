package command.event;

import java.util.List;

import event.IRCEventListener;

public abstract class ReplyEvent implements IRCEvent {

	private int replyNumber;
	private List<String> parameters;

	public ReplyEvent(int replyNumber, List<String> parameters) {
		this.replyNumber = replyNumber;
		this.parameters = parameters;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		onReplyExecute(listener, replyNumber, parameters);
	}

	abstract void onReplyExecute(IRCEventListener listener, int replyNumber,
			List<String> parameters);

}
