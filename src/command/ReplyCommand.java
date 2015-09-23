package command;

import java.util.List;

import parser.IRCMessage;

public abstract class ReplyCommand extends IRCCommandImpl {

	private int replyNumber;
	private List<String> parameters;

	public ReplyCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		try {
			replyNumber = Integer.valueOf(ircMessage.getCommand());
		} catch (NumberFormatException e) {
			throw new InvalidCommandException();
		}
		parameters = ircMessage.getParametersAsList();
		if (parameters == null) {
			throw new IllegalArgumentException();
		}
		parameters = parameters.subList(1, parameters.size());
	}

	int getReplyNumber() {
		return replyNumber;
	}

	List<String> getParameters() {
		return parameters;
	}
}
