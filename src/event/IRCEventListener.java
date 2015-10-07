package event;

import java.util.List;

import model.IRCChannel;
import model.IRCModeAction;
import model.IRCUser;

/**
 * Listener of an IRC Event.
 * 
 * @author Tomas
 */
public interface IRCEventListener {

	/**
	 * This method is called when the server sends a PING command.
	 * 
	 * @param server
	 *            Server that sent the PING.
	 * @throws {@link IllegalArgumentException} If server is null.
	 */
	public void onPing(String server);

	/**
	 * This method is called after the server replied with the Welcome message.
	 */
	public void onLogin();

	/**
	 * This method is called when user has changed it's nickname to newNickname.
	 * The user has the new nickname.
	 * 
	 * @param user
	 *            User that has changed its nickname.
	 * @param prevNickname
	 *            The previous nickname of the user.
	 * 
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	public void onNick(IRCUser user, String prevNickname);

	/**
	 * This method is called when a NOTICE has been sent to a channel where the
	 * client is, or directly to the client (channel will be null).
	 * 
	 * @param channel
	 *            Channel where the notice has been sent, or null if it was
	 *            directly sent to the client.
	 * @param message
	 *            Message of the notice.
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	void onNotice(IRCChannel channel, String message);

	/**
	 * This method is called when the user joins the channel.
	 * 
	 * @param user
	 *            User that joined the channel.
	 * @param channel
	 *            Channel being joined by the user.
	 * @throws {@link IllegalArgumentException} If user or channel are null.
	 */
	public void onJoin(IRCUser user, IRCChannel channel);

	/**
	 * This method is called when the user leaves the channel.
	 * 
	 * @param user
	 *            User that leaves the channel.
	 * @param channel
	 *            Channel being left by the user.
	 * @param message
	 *            Optional message written by the message.
	 * @throws {@link IllegalArgumentException} If user or channel are null.
	 */
	public void onPart(IRCUser user, IRCChannel channel, String message);

	/**
	 * This method is called when the modes of an user are changed.
	 * 
	 * @param user
	 *            User that has changed modes.
	 * @param userModeActions
	 *            Mode changes of the user.
	 * @throws {@link IllegalArgumentException} If channel or userModeActions
	 *         are null.
	 */
	public void onMode(IRCUser user, List<IRCModeAction> userModeActions);

	/**
	 * This method is called when the modes of a channel are changed.
	 * 
	 * @param channel
	 *            Channel that has changed modes.
	 * @param channelModeActions
	 *            Mode changes of the channel.
	 * @throws {@link IllegalArgumentException} If channel or channelModeActions
	 *         are null.
	 */
	public void onMode(IRCChannel channel,
			List<IRCModeAction> channelModeActions);

	/**
	 * This method is called when a message has been sent (private or through
	 * channel).
	 * 
	 * @param sender
	 *            Sender of the message.
	 * @param message
	 *            Message being sent.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void onMessage(IRCUser sender, String message);

	/**
	 * This method is called when a message has been sent through a channel.
	 * 
	 * @param sender
	 *            Sender of the message.
	 * @param channel
	 *            Channel where the message was sent.
	 * @param message
	 *            Message being sent.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void onChannelMessage(IRCUser sender, IRCChannel channel,
			String message);

	/**
	 * This method is called when a message has been sent privately to the
	 * client.
	 * 
	 * @param sender
	 *            Sender of the message.
	 * @param message
	 *            Message being sent.
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public void onPrivateMessage(IRCUser sender, String message);

	/**
	 * This method is called when the server replies with a connection related
	 * reply (000-099).
	 * 
	 * @param replyNumber
	 *            Number of the reply.
	 * @param parameters
	 *            Parameters of the reply.
	 * @throws IllegalArgumentException
	 *             If parameters is null.
	 */
	public void onConnectionReply(int replyNumber, List<String> parameters);

	/**
	 * This method is called when the server repliesto a command (200-399).
	 * 
	 * @param replyNumber
	 *            Number of the reply.
	 * @param parameters
	 *            Parameters of the reply.
	 * @throws IllegalArgumentException
	 *             If parameters is null.
	 */
	public void onCommandReply(int replyNumber, List<String> parameters);
}
