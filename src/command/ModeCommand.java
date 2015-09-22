package command;

import java.util.List;

import model.IRCChannel;
import model.IRCDao;
import model.IRCModeAction;
import model.IRCUser;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import parser.IRCModesParser;
import event.IRCEventListener;

public class ModeCommand extends IRCCommandImpl {

	private static final String MODE_COMMAND = "MODE";
	private static final int TARGET_INDEX = 0;

	private IRCChannel channel;
	private IRCUser user;
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

	public ModeCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		String target = ircMessage.getParameter(TARGET_INDEX);
		user = dao.getUser(target);
		channel = dao.getChannel(target);
		if (user == null && channel == null) {
			throw new InvalidCommandException();
		}
		if (ircMessage.amountOfParameters() <= 1) {
			throw new InvalidCommandException();
		}
		if (channel != null) {
			channelModeActions = IRCModesParser.parseChannelModeActions(dao,
					ircMessage.getParameters().getParameters());
		}
		if (user != null) {
			userModeActions = IRCModesParser.parseUserModeActions(dao,
					ircMessage.getParameters().getParameters());
		}
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (user != null) {
			listener.onMode(user, userModeActions);
		}
		if (channel != null) {
			listener.onMode(channel, channelModeActions);
		}
	}
}
