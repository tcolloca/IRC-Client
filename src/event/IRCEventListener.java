package event;

import model.IRCChannel;
import model.IRCUser;
import util.IRCException;

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

	// TODO :
	/**
	 * This method is called after the user's mode has been set.
	 * 
	 * @throws IRCException
	 */
	public void onLogin();

	/**
	 * This method is called when user has changed it's nickname to newNickname.
	 * If user is null, then it was the client who changed it's nick.
	 * 
	 * @param user
	 *            User that has changed its nickname (It will have the nickname
	 *            changed).
	 * @param newNickname
	 *            The new nickname of the user.
	 * @param prevNickname
	 *            The previous nickname of the user.
	 * 
	 * @throws {@link IllegalArgumentException} If any of the arguments is null.
	 */
	public void onNick(IRCUser user, String newNickname, String prevNickname);

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
}
