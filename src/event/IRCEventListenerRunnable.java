package event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import util.IRCFrameworkErrorException;

import command.IRCCommand;

/**
 * Saves the different commands that haven been executed and every time the
 * listener is available it calls the corresponding method of the listener with
 * a new command from the queue.
 * 
 * @author Tomas
 */
public class IRCEventListenerRunnable implements Runnable {

	private IRCEventListener listener;
	private BlockingQueue<IRCCommand> commandsQueue;

	/**
	 * @param listener
	 * @throws IllegalArgumentException
	 *             If listener is null.
	 */
	IRCEventListenerRunnable(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		this.listener = listener;
		this.commandsQueue = new LinkedBlockingQueue<IRCCommand>();
	}

	/**
	 * Pushes the command to the queue of pending commands that have been read.
	 * 
	 * @param command
	 * @throws {@link IllegalArgumentException} If command is null.
	 * @throws IRCFrameworkErrorException
	 *             If a thread related error occurs.
	 */
	void push(IRCCommand command) {
		if (command == null) {
			throw new IllegalArgumentException();
		}
		try {
			commandsQueue.put(command);
		} catch (Exception e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void run() {
		while (true) {
			IRCCommand command;
			try {
				command = commandsQueue.take();
				command.onExecute(listener);
			} catch (InterruptedException e) {
				throw new IRCFrameworkErrorException();
			}
		}
	}
}
