package command;

import parser.IRCMessage;
import event.IRCRawEventListener;

public class ConnectionReplyCommand extends ReplyCommand {

	public ConnectionReplyCommand(IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		listener.onConnectionReply(getReplyNumber(), getParameters());
	}
}
