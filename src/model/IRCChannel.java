package model;

import java.util.List;

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

	/**
	 * Returns the list of users in the channel.
	 * 
	 * @return the list of users in the channel.
	 */
	public List<IRCUser> getUsers();

	/**
	 * Returns the topic of this channel.
	 * 
	 * @return the topic of this channel.
	 */
	public IRCTopic getTopic();

	/**
	 * Returns the time of creation of the channel.
	 * 
	 * @return the time of creation of the channel.
	 */
	public long getCreationTime();
}
