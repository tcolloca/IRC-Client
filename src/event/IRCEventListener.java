package event;

import client.IRCException;

/**
 * Listener of an IRC Event.
 * 
 * @author Tomas
 */
public interface IRCEventListener {

	/**
	 * This method is called after an IRC connection has been established.
	 */
	public void onConnect();

	/**
	 * This method is called when the server sends a PING command.
	 * 
	 * @param server
	 *            Server that sent the PING.
	 * @throws {@link IllegalArgumentException} If server is null.
	 */
	public void onPing(String server);

	/**
	 * This method is called after the user's mode has been set.
	 * 
	 * @throws IRCException
	 */
	public void onLogin();

	/**
	 * This method is called when user has changed it's nickname to nickname. If
	 * user is null, then it was the client who changed it's nick.
	 * 
	 * @throws {@link IllegalArgumentException} If nickname is null.
	 */
	public void onNick(String user, String nickname);

	/**
	 * This method is called when a NOTICE has been read.
	 * 
	 * @param msgtarget
	 * @param message
	 */
	void onNotice(String msgtarget, String message);
}
