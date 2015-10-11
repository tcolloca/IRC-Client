package client;

import java.util.List;
import java.util.Set;

import util.IRCConnectionException;
import model.IRCChannel;
import model.IRCUser;
import command.IRCCommand;
import event.IRCEventListener;

public interface IRCClient {

	/**
	 * Connects to the server and then it occupies of the connection, reading
	 * and sending messages.
	 * 
	 * @throws IRCConnectionException
	 *             If any connection error occurs.
	 */
	public void run() throws IRCConnectionException;

	/**
	 * Stops the client from executing.
	 */
	public void stop();

	/**
	 * Stops the client from executing with the message received.
	 * 
	 * @param message
	 *            Message to show as quit message.
	 * @throws IllegalArgumentException
	 *             If message is null.
	 */
	public void stop(String message);

	/**
	 * Attempts to joins the channel with the received name. Ignores it if
	 * already joined.
	 * 
	 * @param channelName
	 *            Name of the channel to be joined.
	 * @throws IllegalArgumentException
	 *             If channelName is null.
	 */
	public void join(String channelName);

	/**
	 * Attempts to joins the channel with the received name and password.
	 * Ignores it if already joined.
	 * 
	 * @param channelName
	 *            Name of the channel to be joined.
	 * @param password
	 *            Password of the channelName.
	 * @throws IllegalArgumentException
	 *             If channelName is null.
	 */
	public void join(String channelName, String password);

	/**
	 * Attempts to joins the channels with the received names. Ignores it if
	 * already joined.
	 * 
	 * @param channelNames
	 *            Names of the channels to be joined.
	 * @throws IllegalArgumentException
	 *             If channelNames is null or any channel name is null.
	 */
	public void join(List<String> channelNames);

	/**
	 * Attempts to joins the channels with the received names and passwords.
	 * Ignores it if already joined. If a channel has no password it should be
	 * null in the list.
	 * 
	 * @param channelNames
	 *            Names of the channels to be joined.
	 * @param passwords
	 *            Passwords of the channels to be joined.
	 * @throws IllegalArgumentException
	 *             If channelNames or any channel name is null.
	 */
	public void join(List<String> channelNames, List<String> passwords);

	/**
	 * Leaves the channel received. Ignores it if the client is not on it.
	 * 
	 * @param channel
	 *            Channel to be left.
	 * @throws IllegalArgumentException
	 *             If channel is null.
	 */
	public void leave(IRCChannel channel);

	/**
	 * Attempts to change the nick of the client to newNick.
	 * 
	 * @param newNick
	 *            New nick of the client.
	 * @throws IllegalArgumentException
	 *             If newNick is null.
	 */
	public void setNickname(String newNick);

	/**
	 * Attempts to send the message through the channel.
	 * 
	 * @param channel
	 *            Channel where the message is being sent.
	 * @param message
	 *            Message to be sent.
	 * @throws IllegalArgumentException
	 *             If channel or message are null.
	 */
	public void sendChannelMessage(IRCChannel channel, String message);

	/**
	 * Attempts to send the private message to the user.
	 * 
	 * @param user
	 *            User that would received the message.
	 * @param message
	 *            Message to be sent.
	 * @throws IllegalArgumentException
	 *             If user or message are null.
	 */
	public void sendPrivateMessage(IRCUser user, String message);

	/**
	 * Sends the command to the server.
	 * 
	 * @param command
	 *            Command to be sent.
	 */
	public void sendCommand(IRCCommand command);

	/**
	 * Sends the raw IRC message through the connection.
	 * 
	 * @param message
	 *            Message to be sent.
	 */
	public void sendRawLine(String message);

	/**
	 * Returns the IRCUser that represents the client.
	 * 
	 * @return the IRCUser that represents the client.
	 */
	public IRCUser getClientUser();

	/**
	 * Returns the IRCUser with that userName or null if there is no one.
	 * 
	 * @return the IRCUser with that userName or null if there is no one.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	public IRCUser getUser(String userName);

	/**
	 * Returns the IRCChannel with that name if known to the client, or null if
	 * not.
	 * 
	 * @return the IRCChannel with that name if known to the client, or null if
	 *         not.
	 * @throws IllegalArgumentException
	 *             If channelName is null.
	 */
	public IRCChannel getChannel(String channelName);

	/**
	 * Returns a set with all the users known by the client.
	 * 
	 * @return a set with all the users known by the client.
	 */
	public Set<IRCUser> getAllUsers();

	/**
	 * Returns a set with all the channels known by the client.
	 * 
	 * @return a set with all the channels known by the client.
	 */
	public Set<IRCChannel> getAllChannels();

	/**
	 * Returns the nick of the client.
	 * 
	 * @return the nick of the client.
	 */
	public String getNick();

	/**
	 * Returns the configuration of the client.
	 * 
	 * @return the configuration of the client.
	 */
	public IRCConfiguration getConfiguration();

	/**
	 * Adds a listener for the IRC events dispatched by the IRCClient.
	 * 
	 * @param listener
	 *            Listener to be added.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void addListener(IRCEventListener listener);
}
