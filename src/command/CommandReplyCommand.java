package command;

import parser.IRCMessage;
import event.IRCRawEventListener;

public class CommandReplyCommand extends ReplyCommand {

	public CommandReplyCommand(IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		listener.onCommandReply(getReplyNumber(), getParameters());
	}
}
