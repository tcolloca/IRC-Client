package command;

import event.IRCEventListener;
import model.IRCDao;
import parser.IRCMessage;

public class CommandReplyCommand extends ReplyCommand {

	public CommandReplyCommand(IRCDao dao, IRCMessage ircMessage) throws InvalidCommandException {
		super(dao, ircMessage);
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		listener.onCommandReply(getReplyNumber(), getParameters());
	}
}
