package event;

import command.IRCCommand;
import command.event.IRCEvent;

public interface IRCEventDispatcher {

	/**
	 * Adds the listener to the listeners' list.
	 * 
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	public void addListener(IRCEventListener listener);

	/**
	 * This method is called when a command related with the event was read, and
	 * it should end calling the onExecute method from {@link IRCCommand}
	 * passing all of its listeners as parameters.
	 * 
	 * @param event
	 * @throws IllegalArgumentException
	 *             If command is null.
	 */
	public void onExecute(IRCEvent event);
}
