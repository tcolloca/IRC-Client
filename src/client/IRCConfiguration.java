package client;

import java.nio.charset.Charset;
import java.util.List;

import event.IRCEventListener;

/**
 * Represents the configuration of an IRCClient. Changes done with the set
 * methods after building the client won't have effects over it.
 * 
 * @author Tomas
 */
public interface IRCConfiguration {

	/**
	 * Sets server to which the client will try to connect to.
	 * 
	 * @param server
	 *            Server to which the client will try to connect to.
	 * @return this
	 */
	public IRCConfiguration setServer(String server);

	/**
	 * Sets the IRCConfiguration for a MultiThread IRCClient.
	 * 
	 * @param isMultiThread
	 *            True if is client should allow event listeners run in
	 *            different threads.
	 * @return this
	 */
	public IRCConfiguration setMultiThread(boolean isMultiThread);

	/**
	 * Sets the nickname of the IRCClient.
	 * 
	 * @param nickname
	 *            Nickname for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If nickname is null or empty.
	 */
	public IRCConfiguration setNickname(String nickname);

	/**
	 * Sets the password for the nickname of the IRCClient.
	 * 
	 * @param password
	 *            password for the nickname for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If password is null or empty.
	 */
	public IRCConfiguration setPassword(String password);

	/**
	 * Sets the username of the IRCClient.
	 * 
	 * @param username
	 *            Username for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If username is null or empty.
	 */
	public IRCConfiguration setUsername(String username);

	/**
	 * Sets the realname of the IRCClient.
	 * 
	 * @param realname
	 *            Realname for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If realname is null or empty.
	 */
	public IRCConfiguration setRealname(String realname);

	/**
	 * Sets the initial channels of the IRCClient.
	 * 
	 * @param channels
	 *            List with the name of the channels to join at start.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If channels or any channel is null.
	 */
	public IRCConfiguration setInitialChannels(List<String> channels);

	/**
	 * Sets the initial channels of the IRCClient.
	 * 
	 * @param channels
	 *            List with the name of the channels to join at start.
	 * @param passwords
	 *            List with the passwords of the channels to join at start.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If channels or any channels is null, or if channels size is
	 *             different to passwords size.
	 */
	public IRCConfiguration setInitialChannels(List<String> channels,
			List<String> passwords);

	/**
	 * Sets the charset being used by the IRCClient.
	 * 
	 * @param charset
	 *            Charset for the IRCClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If charset is null.
	 */
	public IRCConfiguration setCharset(Charset charset);

	/**
	 * Sets the connection handler that will be used by the IRClient.
	 * 
	 * @param handler
	 *            Connection handler that will be used by the IRClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If handler is null.
	 */
	public IRCConfiguration setConnectionHandler(IRCConnectionHandler handler);

	/**
	 * Sets the reader that will be used by the IRClient.
	 * 
	 * @param reader
	 *            Reader that will be used by the IRClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If reader is null.
	 */
	public IRCConfiguration setReader(IRCReader reader);

	/**
	 * Sets the writer that will be used by the IRClient.
	 * 
	 * @param writer
	 *            Writer that will be used by the IRClient.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If writer is null.
	 */
	public IRCConfiguration setWriter(IRCWriter writer);

	/**
	 * Adds a listener for the IRCEvents dispatched by the IRCClient.
	 * 
	 * @param listener
	 *            Listener to be added.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public IRCConfiguration addListener(IRCEventListener listener);

	/**
	 * Removes the listener from list of listeners that will listen to the
	 * IRCEvents dispatched by the IRCClient.
	 * 
	 * @param listener
	 *            Listener to be removed.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public IRCConfiguration removeListener(IRCEventListener listener);

	/**
	 * Returns the server to which the client will try/has tried to connect.
	 * 
	 * @return the server to which the client will try/has tried to connect.
	 */
	public String getServer();

	/**
	 * Returns if the eventListeners are handled in different threads.
	 * 
	 * @return if the eventListeners are handled in different threads.
	 */
	public boolean isMultiThread();

	/**
	 * Returns the nickname which the client will try/has tried to use to
	 * connect.
	 * 
	 * @return the nickname which the client will try/has tried to use to
	 *         connect.
	 */
	public String getNickname();

	/**
	 * Returns the password which the client will use/has used to connect.
	 * 
	 * @return the password which the client will use/has used to connect.
	 */
	public String getPassword();

	/**
	 * Returns the username which the client will use/has used to connect.
	 * 
	 * @return the username which the client will use/has used to connect.
	 */
	public String getUsername();

	/**
	 * Returns the realname which the client will use/has used to connect.
	 * 
	 * @return the realname which the client will use/has used to connect.
	 */
	public String getRealname();

	/**
	 * Returns the name of the channels which the client will try/has tried to
	 * join.
	 * 
	 * @return the name of the channels which the client will try/has tried to
	 *         join.
	 */
	public List<String> getChannelNames();

	/**
	 * Returns the passwords which the client will use/has used to try to join
	 * the channels.
	 * 
	 * @return the passwords which the client will use/has used to try to join
	 *         the channels.
	 */
	public List<String> getChannelPasswords();

	/**
	 * Returns the charset that will be used/is being used by the client.
	 * 
	 * @return the charset that will be used/is being used by the client.
	 */
	public Charset getCharset();

	/**
	 * Returns the connection handler that will be used/is being used by the
	 * client.
	 * 
	 * @return the connection handler that will be used/is being used by the
	 *         client.
	 */
	public IRCConnectionHandler getConnectionHandler();

	/**
	 * Returns the reader that will be used/is being used by the client.
	 * 
	 * @return the reader that will be used/is being used by the client.
	 */
	public IRCReader getReader();

	/**
	 * Returns the writer that will be used/is being used by the client.
	 * 
	 * @return the writer that will be used/is being used by the client.
	 */
	public IRCWriter getWriter();

	/**
	 * Returns the listeners subscribed to the IRCEvents thrown by the client.
	 * 
	 * @return the listeners subscribed to the IRCEvents thrown by the client.
	 */
	public List<IRCEventListener> getListeners();
}
