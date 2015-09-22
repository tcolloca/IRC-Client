package parser;

import java.util.List;

/**
 * Represents a message's parameters of IRC according to the BNF representation
 * specified in the RFC 1459.
 * 
 * @author Tomas
 */
public interface IRCParameters {

	/**
	 * Returns the trailing/last parameter, or null if there is none.
	 * 
	 * @return the trailing/last parameter, or null if there is none.
	 */
	public String getTrailing();

	/**
	 * Returns the current parameter, or null if there is none.
	 * 
	 * @return the current parameter, or null if there is none.
	 */
	public String getMiddle();

	/**
	 * Returns the next parameters, or null if there are none.
	 * 
	 * @return the next parameters, or null if there are none.
	 */
	public IRCParameters getParameters();

	/**
	 * Returns the parameter in the given index, or null if there is none.
	 * 
	 * @return the parameter in the given index, or null if there is none.
	 */
	public String getParameter(int index);

	/**
	 * Returns the parameters as a list of strings.
	 * 
	 * @return the parameters as a list of strings.
	 */
	public List<String> getParametersAsList();

	/**
	 * Returns the IRC representation of this message.
	 * 
	 * @return the IRC representation of this message.
	 */
	public String getString();
}
