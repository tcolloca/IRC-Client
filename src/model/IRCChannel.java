package model;

import client.IRCClient;

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
	 * Returns the topic of this channel. Null if it is still unknown.
	 * 
	 * @return the topic of this channel.
	 */
	public IRCTopic getTopic();

	/**
	 * Returns the time of creation of the channel. 0 if it is still unknown.
	 * 
	 * @return the time of creation of the channel.
	 */
	public long getCreationTime();

	/**
	 * Returns the limit of users for the channel. -1 if it has no limit, and 0
	 * if it is still unknown.
	 * 
	 * @return the limit of users for the channel.
	 */
	public int getLimit();

	/**
	 * Returns the flood limit of the channel. Null if it is still unknown.
	 * 
	 * @return the flood limit of the channel.
	 */
	public IRCFloodLimit getFloodLimit();

	/**
	 * Returns the {@link IRCChannelUsers} of the channel.
	 * 
	 * @return the {@link IRCChannelUsers} of the channel.
	 */
	public IRCChannelUsers getChannelUsers();

	/**
	 * Returns the {@link IRCChannelModes} of the channel.
	 * 
	 * @return the {@link IRCChannelModes} of the channel.
	 */
	public IRCChannelModes getChannelModes();

	/**
	 * Returns the {@link IRCChannelMasks} of the channel.
	 * 
	 * @return the {@link IRCChannelMasks} of the channel.
	 */
	public IRCChannelMasks getChannelMasks();

	/**
	 * Returns true if the channel has a key.
	 * 
	 * @return true if the channel has a key.
	 */
	public boolean hasKey();

	/**
	 * Returns true if the channel has a limit.
	 * 
	 * @return true if the channel has a limit.
	 */
	public boolean hasLimit();

	/**
	 * Attempts to set the key for the channel.
	 * 
	 * @param key
	 *            New key for the channel.
	 * @throws IllegalArgumentException
	 *             If key is null.
	 */
	public void setKey(String key);

	/**
	 * Attempts to set the topic for the channel.
	 * 
	 * @param message
	 *            Message of the new topic for the channel.
	 * @throws IllegalArgumentException
	 *             If message is null.
	 */
	public void setTopic(String message);

	/**
	 * Attempts to set an users limit for the channel. If limit is -1, then the
	 * limit will be removed.
	 * 
	 * @param limit
	 *            New limit for the channel.
	 * @throws IllegalArgumentException
	 *             If limit is 0.
	 */
	public void setLimit(int limit);

	/**
	 * Attempts to set the flood limit for the channel.
	 * 
	 * @param floodLimit
	 *            New floodLimit for the channel.
	 * @throws IllegalArgumentException
	 *             If floodLimit is null.
	 */
	public void setFloodLimit(IRCFloodLimit floodLimit);

	/**
	 * Attempts to remove the key from the channel.
	 */
	public void removeKey();

	/**
	 * Attempts to remove the topic from the channel.
	 */
	public void removeTopic();

	/**
	 * Attempts to set the limit from the channel.
	 */
	public void removeLimit();

	/**
	 * Attempts to remove the flood limit for the channel.
	 */
	public void removeFloodLimit();

	/**
	 * Returns the client that holds a reference to this channel.
	 * 
	 * @return the client that holds a reference to this channel.
	 */
	IRCClient getClient();
}
