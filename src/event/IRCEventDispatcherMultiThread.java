package event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import command.IRCCommand;

public class IRCEventDispatcherMultiThread implements IRCEventDispatcher {

	private ExecutorService threadPool;
	private List<IRCEventListenerRunnable> listenerRunnables;

	public IRCEventDispatcherMultiThread() {
		super();
		threadPool = Executors.newCachedThreadPool();
		listenerRunnables = Collections
				.synchronizedList(new ArrayList<IRCEventListenerRunnable>());
	}

	@Override
	public void addListener(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		IRCEventListenerRunnable listenerRunnable = new IRCEventListenerRunnable(
				listener);
		listenerRunnables.add(listenerRunnable);
		FutureTask<?> task = new FutureTask<Void>(listenerRunnable, null);
		threadPool.submit(task);
	}

	@Override
	public void onExecute(IRCCommand command) {
		if (command == null) {
			throw new IllegalArgumentException();
		}
		for (IRCEventListenerRunnable listenerRunnable : listenerRunnables) {
			listenerRunnable.push(command);
		}
	}
}
