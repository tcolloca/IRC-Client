package model;

public interface IRCChannel {

	/**
	 * Returns the channel name.
	 * 
	 * @return the channel name.
	 */
	public String getName();

	/**
	 * Returns the channel key. Null if it is not set.
	 * 
	 * @return the channel key. Null if it is not set.
	 */
	public String getKey();
}
