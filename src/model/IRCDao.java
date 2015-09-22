package model;

public interface IRCDao {

	/**
	 * Returns the IRCUser that has that userName
	 * (&lt;nickname&gt;[!&lt;username&gt;][@&lt;host&gt;]), or null if there is
	 * no one.
	 * 
	 * @param userName
	 * @return the IRCUser that has that userName, or null if there is no one.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	public IRCUser getUser(String userName);

	/**
	 * Returns true if the client already knows the user with such userName.
	 * 
	 * @param userName
	 *            Name of the user.
	 * @return true if the client already knows the user with such userName.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	public boolean hasUser(String userName);

	/**
	 * Adds and returns a new user based on userName.
	 * 
	 * @param userName
	 *            Name of the new user.
	 * @return The IRCUser that has been created.
	 * @throws IllegalArgumentException
	 *             If userName is null or if it already exists an user with such
	 *             userName.
	 */
	public IRCUser addUser(String userName);

	/**
	 * Adds the user to the dao.
	 * 
	 * @param user
	 *            User to be added.
	 * @return The IRCUser that has been created.
	 * @throws IllegalArgumentException
	 *             If user is null or if it already exists that user in the dao.
	 */
	public IRCUser addUser(IRCUser user);

	/**
	 * Returns the IRCUser that has that userName
	 * (&lt;nickname&gt;[!&lt;username&gt;][@&lt;host&gt;]) and removes it from
	 * the dao, or null if there is no one.
	 * 
	 * @param userName
	 * @return the IRCUser that has that userName, or null if there is no one.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	public IRCUser removeUser(String userName);

	/**
	 * Returns the channel with that name, and null if there is no one.
	 * 
	 * @param name
	 *            Name of the channel.
	 * @return the channel with that name, and null if there is no one.
	 * @throws IllegalArgumentException
	 *             If name is null.
	 */
	public IRCChannel getChannel(String name);

	/**
	 * Returns true if it knows a channel with that name.
	 * 
	 * @param name
	 *            Name of the channel.
	 * @return true if it knows a channel with that name.
	 * @throws IllegalArgumentException
	 *             If name is null.
	 */
	public boolean hasChannel(String name);

	/**
	 * Adds and returns a new channel based on name.
	 * 
	 * @param name
	 *            Name of the new channel.
	 * @return the IRCChannel that has been added.
	 * @throws IllegalArgumentException
	 *             If name is null or if it already exists a channel with such
	 *             name.
	 */
	public IRCChannel addChannel(String name);

	/**
	 * Adds and returns a new channel based on name, and with the received
	 * password.
	 * 
	 * @param name
	 *            Name of the new channel.
	 * @param password
	 *            Password of the new channel.
	 * @return the IRCChannel that has been added.
	 * @throws IllegalArgumentException
	 *             If name is null or if it already exists a channel with such
	 *             name.
	 */
	public IRCChannel addChannel(String name, String password);

	/**
	 * Adds the channel to the dao.
	 * 
	 * @param channel
	 *            Channel to be added.
	 * @return The IRCChannel that has been created.
	 * @throws IllegalArgumentException
	 *             If channel is null or if it already exists that channel in
	 *             the dao.
	 */
	public IRCChannel addChannel(IRCChannel channel);

	/**
	 * Returns the channel with that name and removes if from the dao, and null
	 * if there is no one.
	 * 
	 * @param name
	 *            Name of the channel.
	 * @return the channel with that name, and null if there is no one.
	 * @throws IllegalArgumentException
	 *             If name is null.
	 */
	public IRCChannel removeChannel(String name);
}
