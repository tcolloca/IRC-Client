package model;

public interface IRCUser {

	/**
	 * Attempts to send a private message to the user.
	 * 
	 * @param message
	 *            Message to be sent.
	 * @throws IllegalArgumentException
	 *             If message is null.
	 */
	public void send(String message);

	/**
	 * Returns the user's nickname.
	 * 
	 * @return the user's nickname.
	 */
	public String getNickname();

	/**
	 * Returns the user's username. Null if still unknown.
	 * 
	 * @return the user's username.
	 */
	public String getUsername();

	/**
	 * Returns the user's hostname. Null if still unknown.
	 * 
	 * @return the user's hostname.
	 */
	public String getHostname();

	/**
	 * Returns the user's fullname (nickname!username@hostname). Null if still
	 * unknown.
	 * 
	 * @return the user's fullname (nickname!username@hostname).
	 */
	public String getFullname();

	/**
	 * Returns the user's realname. Null if still unknown.
	 * 
	 * @return the user's realname.
	 */
	public String getRealname();

	/**
	 * Returns the hops to the user (amount of servers in between the user and
	 * the client). -1 if still unknown.
	 * 
	 * @return the hops to the user (amount of servers in between the user and
	 *         the client).
	 */
	public int getHops();

	/**
	 * Returns the server to which the user is connected. Null if still unknown.
	 * 
	 * @return the server to which the user is connected.
	 */
	public IRCServer getServer();

	/**
	 * Returns the amount of seconds that the user has been idle. -1 if still
	 * unknown.
	 * 
	 * @return the amount of seconds that the user has been idle.
	 */
	public int getIdleTime();

	/**
	 * Returns the away message if the user is gone. Null if it is unknown.
	 * 
	 * @return the away message if the user is gone.
	 * @throws IllegalStateException
	 *             If the user is not away.
	 */
	public String getAwayMessage();

	/**
	 * Returns the {@link IRCUserChannels} of the user.
	 * 
	 * @return the {@link IRCUserChannels} of the user.
	 */
	public IRCUserChannels getUserChannels();

	/**
	 * Returns the {@link IRCUserFlags} of the user.
	 * 
	 * @return the {@link IRCUserFlags} of the user.
	 */
	public IRCUserFlags getUserFlags();
}
