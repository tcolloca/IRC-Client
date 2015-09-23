package command;

import java.util.List;

import model.IRCChannel;
import model.IRCModeAction;
import model.IRCUser;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import parser.IRCModesParser;
import event.IRCRawEventListener;

public class ModeCommand extends IRCCommandImpl {

	private static final String MODE_COMMAND = "MODE";
	private static final int TARGET_INDEX = 0;
	private static final String CHANNEL_NAME_REGEX = "^(#|&|\\+|!).*$";

	private String name;
	private List<IRCModeAction> channelModeActions;
	private List<IRCModeAction> userModeActions;

	public ModeCommand(IRCUser user) throws InvalidCommandException {
		super(new IRCMessageImpl(MODE_COMMAND, user.getNickname()));
	}

	public ModeCommand(IRCUser user, IRCModeAction action)
			throws InvalidCommandException {
		super(new IRCMessageImpl(MODE_COMMAND, user.getNickname(),
				action.getString()));
	}

	public ModeCommand(IRCChannel channel) throws InvalidCommandException {
		super(new IRCMessageImpl(MODE_COMMAND, channel.getName()));
	}

	public ModeCommand(IRCChannel channel, IRCModeAction action)
			throws InvalidCommandException {
		super(new IRCMessageImpl(MODE_COMMAND, channel.getName(),
				action.getString()));
	}

	public ModeCommand(IRCMessage ircMessage) throws InvalidCommandException {
		super(ircMessage);
		this.name = ircMessage.getParameter(TARGET_INDEX);
		if (ircMessage.amountOfParameters() <= 1) {
			throw new InvalidCommandException();
		}
		if (isChannelName(name)) {
			channelModeActions = IRCModesParser
					.parseChannelModeActions(ircMessage.getParameters()
							.getParameters());
		} else {
			userModeActions = IRCModesParser.parseUserModeActions(ircMessage
					.getParameters().getParameters());
		}
	}

	private boolean isChannelName(String name) {
		return name.matches(CHANNEL_NAME_REGEX);
	}

	@Override
	public void onExecute(IRCRawEventListener listener) {
		if (channelModeActions != null) {
			listener.onMode(name, channelModeActions);
		} else {
			listener.onMode(name, userModeActions);
		}
	}
}
