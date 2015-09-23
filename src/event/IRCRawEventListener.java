package event;

import java.util.List;

import model.IRCModeAction;

public interface IRCRawEventListener {

	/**
	 * This method is called when the server sends a PING command.
	 * 
	 * @param server
	 *            Server that sent the PING.
	 * @throws {@link IllegalArgumentException} If server is null.
	 */
	public void onPing(String server);

	/**
	 * This method is called when user with prevNickname has changed it's
	 * nickname to newNickname.
	 * 
	 * @param prevNickname
	 *            Nickname of the user that has changed its nickname.
	 * @param newNickname
	 *            The new nickname of the user.
	 * 
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	public void onNick(String prevNickname, String newNickname);

	/**
	 * This method is called when a NOTICE has been sent to a target.
	 * 
	 * @param target
	 *            target of the notice.
	 * @param message
	 *            Message of the notice.
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	public void onNotice(String target, String message);

	/**
	 * This method is called when the user with that nickname joins the channel
	 * with that channelName.
	 * 
	 * @param nickname
	 *            Nickname of the user that joined the channel.
	 * @param channel
	 *            Name of the channel being joined by the user.
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	public void onJoin(String nickname, String channelName);

	/**
	 * This method is called when the modes of an user or channel have been
	 * changed.
	 * 
	 * @param name
	 *            Nickname of the user or name of the channel that has changed
	 *            modes.
	 * @param modeActions
	 *            Mode changes of the user or channel.
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	public void onMode(String name, List<IRCModeAction> modeActions);

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
