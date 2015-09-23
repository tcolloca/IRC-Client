package event;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import util.IRCFrameworkErrorException;

import command.event.IRCEvent;

/**
 * Saves the different events that haven been dispatched and every time the
 * listener is available it calls the corresponding method of the listener with
 * a new event from the queue.
 * 
 * @author Tomas
 */
public class IRCEventListenerRunnable implements Runnable {

	private IRCEventListener listener;
	private BlockingQueue<IRCEvent> eventsQueue;

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
		this.eventsQueue = new LinkedBlockingQueue<IRCEvent>();
	}

	/**
	 * Pushes the IRCEvent event to the queue of pending IRCEvent event related
	 * to commands that have been read.
	 * 
	 * @param event
	 * @throws {@link IllegalArgumentException} If IRCEvent event is null.
	 * @throws IRCFrameworkErrorException
	 *             If a thread related error occurs.
	 */
	void push(IRCEvent event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		try {
			eventsQueue.put(event);
		} catch (Exception e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void run() {
		while (true) {
			IRCEvent event;
			try {
				event = eventsQueue.take();
				event.onExecute(listener);
			} catch (InterruptedException e) {
				throw new IRCFrameworkErrorException();
			}
		}
	}
}
