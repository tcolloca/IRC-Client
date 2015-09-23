package client;

import model.IRCUser;
import util.IRCException;

import command.IRCCommand;

import event.IRCEventListener;
import event.IRCRawEventListener;

public interface IRCClient {

	/**
	 * Connects to the server and then it occupies of the connection, reading
	 * and sending messages.
	 * 
	 * @throws IRCException
	 *             If some other I/O or connection error occurs.
	 */
	public void run() throws IRCException;

	/**
	 * Feeds the client with a IRC message that was read.
	 * 
	 * @param message
	 *            Message that has been read.
	 * @throws IllegalArgumentException
	 *             If message is null.
	 */
	public void feed(String message);

	/**
	 * Sends the command to the server.
	 * 
	 * @param command
	 *            Command to be sent.
	 */
	public void sendCommand(IRCCommand command);

	/**
	 * Adds a listener for the raw IRC events dispatched by the IRCClient.
	 * 
	 * @param listener
	 *            Listener to be added.
	 * @return this
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void addRawListener(IRCRawEventListener listener);

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

	/**
	 * Returns the IRCUser with that userName.
	 * 
	 * @return the IRCUser with that userName.
	 * @throws IllegalArgumentException
	 *             If userName is null.
	 */
	public IRCUser getUser(String userName);
}
