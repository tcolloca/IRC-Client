package parser;

/**
 * Represents a message's prefix of IRC according to the BNF representation
 * specified in the RFC 1459.
 * 
 * @author Tomas
 */
public interface IRCPrefix {

	/**
	 * Returns the nickname of the prefix.
	 * 
	 * @return the nickname of the prefix.
	 */
	public String getNickame();

	/**
	 * Returns the user of the prefix, or null if not set.
	 * 
	 * @return the user of the prefix, or null if not set.
	 */
	public String getUser();

	/**
	 * Returns the host of the prefix, or null if not set.
	 * 
	 * @return the host of the prefix, or null if not set.
	 */
	public String getHost();

	/**
	 * Returns the IRC representation of this message.
	 * 
	 * @return the IRC representation of this message.
	 */
	public String getString();
}
