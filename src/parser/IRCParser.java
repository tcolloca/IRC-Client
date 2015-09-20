package parser;

public interface IRCParser {

	/**
	 * Parses the given message into the correct command of IRC that was
	 * received.
	 * 
	 * @param message
	 * @return The {@link IRCMessage} associated with the message received.
	 * @throws IllegalArgumentException
	 *             If message is null or if it doesn't end in \r\n.
	 */
	public IRCMessage parse(String message);

	/**
	 * Parses the given userName and returns the nickname on it.
	 * 
	 * @param userName
	 * @return the nickname within the userName.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	String parseNickname(String userName);

	/**
	 * Parses the given userName and returns the username on it. Null if it has
	 * no username.
	 * 
	 * @param userName
	 * @return the username within the userName. Null if it has no username.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	String parseUsername(String userName);

	/**
	 * Parses the given userName and returns the host on it. Null if it has no
	 * host.
	 * 
	 * @param userName
	 * @return the host within the userName. Null if it has no host.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	String parseHost(String userName);

}
