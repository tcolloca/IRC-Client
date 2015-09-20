package parser;

/**
 * Represents a message of IRC according to the BNF representation specified in
 * the RFC 1459.
 * 
 * @author Tomas
 */
public interface IRCMessage {

	/**
	 * @param parameters
	 * @throws IllegalArgumentException
	 *             If parameters is null.
	 */
	public void setParameters(String... parameters);

	/**
	 * Returns the parameter in the index position (starting from 0), or null if
	 * there is no parameter in such position.
	 * 
	 * @param index
	 * @return the parameter in the index position (starting from 0), or null if
	 *         there is no parameter in such position.
	 */
	public String getParameter(int index);

	/**
	 * Returns the amount of parameters.
	 * 
	 * @return the amount of parameters.
	 */
	public int amountOfParameters();

	/**
	 * Returns true if it has a prefix.
	 * 
	 * @return true if it has a prefix.
	 */
	public boolean hasPrefix();

	/**
	 * Returns the IRCPrefix of the message. Null if it has none.
	 * 
	 * @return the IRCPrefix of the message. Null if it has none.
	 */
	public IRCPrefix getPrefix();

	/**
	 * Returns the command of the message.
	 * 
	 * @return the command of the message.
	 */
	public String getCommand();

	/**
	 * Returns the IRCParameters of the command.
	 * 
	 * @return the IRCParameters of the command.
	 */
	public IRCParameters getParameters();

	/**
	 * Returns the IRC representation of this message.
	 * 
	 * @return the IRC representation of this message.
	 */
	public String getString();
}
