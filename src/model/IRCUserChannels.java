package model;

import java.util.List;
import java.util.Set;

/**
 * This interface allows to access the users channels according to its levels on
 * them.
 * 
 * @author Tomas
 */
public interface IRCUserChannels {

	/**
	 * Returns the set of channels where the user is. Null if still unknown.
	 * 
	 * @return the set of channels where the user is.
	 */
	public Set<IRCChannel> getChannels();

	/**
	 * Returns the set of channels where the user is and has no extra power.
	 * Null if still unknown.
	 * 
	 * @return the set of channels where the user is and has no extra power.
	 */
	public List<IRCChannel> getNormalChannels();

	/**
	 * Returns the list of channels where the user is and has halfOps. Null if
	 * still unknown.
	 * 
	 * @return the list of channels where the user is and has halfOps.
	 */
	public List<IRCChannel> getHalfOpChannels();

	/**
	 * Returns the list of channels where the user is and has ops. Null if still
	 * unknown.
	 * 
	 * @return the list of channels where the user is and has ops.
	 */
	public List<IRCChannel> getOpChannels();

	/**
	 * Returns the list of channels where the user is and has superOps. Null if
	 * still unknown.
	 * 
	 * @return the list of channels where the user is and has superOps.
	 */
	public List<IRCChannel> getSuperOpChannels();

	/**
	 * Returns the list of channels where the user is and has owner status. Null
	 * if still unknown.
	 * 
	 * @return the list of channels where the user is and has owner status.
	 */
	public List<IRCChannel> getOwnerChannels();

	/**
	 * Returns the list of channels where the user is and has voice. Null if
	 * still unknown.
	 * 
	 * @return the list of channels where the user is and has voice.
	 */
	public List<IRCChannel> getVoicedChannels();

	void initializeNormalChannels(List<IRCChannel> channels);

	void initializeHalfOpChannels(List<IRCChannel> channels);

	void initializeOpChannels(List<IRCChannel> channels);

	void initializeSuperOpChannels(List<IRCChannel> channels);

	void initializeOwnerChannels(List<IRCChannel> channels);

	void initializeVoicedChannels(List<IRCChannel> channels);

	void initialize();
}
