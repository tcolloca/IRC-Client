package command;

import java.util.Arrays;
import java.util.List;

import model.IRCChannel;
import model.IRCDao;
import model.IRCUser;
import parser.IRCMessage;
import parser.IRCMessageImpl;
import util.StringConverter;
import event.IRCEventListener;

/**
 * Joins the list of channels received. If no argument is used to create the
 * command, then it has the {@link PartCommand} effect. When this command is
 * read, it shows the user that has joined a channel.
 * 
 * @author Tomas
 */
public class JoinCommand extends IRCCommandImpl {

	public static final String JOIN_COMMAND = "JOIN";
	private static final int CHANNEL_INDEX = 0;

	private IRCUser user;
	private IRCChannel channel;

	/**
	 * Creates a command that leaves all the channels joined. You should better
	 * use {@link PartCommand}.
	 */
	public JoinCommand() throws InvalidCommandException {
		super(new IRCMessageImpl(JOIN_COMMAND, ZERO));
	}

	/**
	 * @param channel
	 *            Channel to be joined.
	 * @throws InvalidCommandException
	 *             If user or channel are null.
	 */
	public JoinCommand(IRCChannel channel) throws InvalidCommandException {
		this(Arrays.asList(channel));
	}

	/**
	 * @param channels
	 *            List of the new channels to be joined.
	 * @throws InvalidCommandException
	 *             If channels is null or any of the channels is null.
	 */
	public JoinCommand(List<IRCChannel> channels)
			throws InvalidCommandException {
		super(new IRCMessageImpl(JOIN_COMMAND,
				channels != null ? StringConverter
						.stringfyList(channels, "" + IN_PARAM_SEPARATOR, "",
								"", new ChannelNameFunction()) : null,
				channels != null ? StringConverter.stringfyList(channels, ""
						+ IN_PARAM_SEPARATOR, "", "", new ChannelKeyFunction())
						: null));
		if (channels == null) {
			throw new InvalidCommandException();
		}
	}

	public JoinCommand(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		super(ircMessage);
		setUser(dao, ircMessage);
		setChannel(dao, ircMessage);
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		listener.onJoin(user, channel);
	}

	private void setUser(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		String userName = ircMessage.getPrefix().getString();
		if (!dao.hasUser(userName)) {
			dao.addUser(userName);
		}
		this.user = dao.getUser(userName);
	}

	private void setChannel(IRCDao dao, IRCMessage ircMessage)
			throws InvalidCommandException {
		String channelName = ircMessage.getParameter(CHANNEL_INDEX);
		if (channelName == null) {
			throw new InvalidCommandException();
		}
		channel = dao.getChannel(channelName);
		if (channel == null) {
			throw new InvalidCommandException();
		}
	}
}
