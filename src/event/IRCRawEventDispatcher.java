package event;

import java.util.ArrayList;
import java.util.List;

import model.IRCChannel;
import model.IRCDao;
import model.IRCModeAction;
import model.IRCUser;

import command.event.CommandReplyEvent;
import command.event.ConnectionReplyEvent;
import command.event.JoinEvent;
import command.event.ModeEvent;
import command.event.NickEvent;
import command.event.NoticeEvent;
import command.event.PingEvent;

/**
 * This class is in charge of calling the command.onExecute() method for all the
 * listeners, and then dispatch the corresponding IRCEvent with the
 * eventDispatcher.
 * 
 * @author Tomas
 */
public class IRCRawEventDispatcher implements IRCRawEventListener {

	private List<IRCRawEventListener> listeners;
	private IRCEventDispatcher eventDispatcher;
	private IRCDao dao;

	/**
	 * @param eventDispatcher
	 * @param dao
	 * @throws IllegalArgumentException
	 *             If any of the arguments is null.
	 */
	public IRCRawEventDispatcher(IRCEventDispatcher eventDispatcher, IRCDao dao) {
		if (eventDispatcher == null || dao == null) {
			throw new IllegalArgumentException();
		}
		this.eventDispatcher = eventDispatcher;
		this.dao = dao;
		listeners = new ArrayList<IRCRawEventListener>();
	}

	/**
	 * Adds the listener to the listeners list.
	 * 
	 * @param listener
	 *            Listener to be added.
	 */
	public void addListener(IRCRawEventListener listener) {
		listeners.add(listener);
	}

	@Override
	public void onPing(String server) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onPing(server);
		}
		eventDispatcher.onExecute(new PingEvent(server));
	}

	@Override
	public void onNick(String prevNickname, String newNickname) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onNick(prevNickname, newNickname);
		}
		IRCUser user = dao.getUser(newNickname);
		eventDispatcher.onExecute(new NickEvent(user, prevNickname));
	}

	@Override
	public void onNotice(String target, String message) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onNotice(target, message);
		}
		IRCChannel channel = dao.getChannel(target);
		eventDispatcher.onExecute(new NoticeEvent(channel, message));
	}

	@Override
	public void onJoin(String nickname, String channelName) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onJoin(nickname, channelName);
		}
		IRCUser user = dao.getUser(nickname);
		IRCChannel channel = dao.getChannel(channelName);
		eventDispatcher.onExecute(new JoinEvent(user, channel));
	}

	@Override
	public void onMode(String name, List<IRCModeAction> modeActions) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onMode(name, modeActions);
		}
		if (dao.hasUser(name)) {
			IRCUser user = dao.getUser(name);
			eventDispatcher.onExecute(new ModeEvent(user, modeActions));
		} else {
			IRCChannel channel = dao.getChannel(name);
			eventDispatcher.onExecute(new ModeEvent(channel, modeActions));
		}
	}

	@Override
	public void onConnectionReply(int replyNumber, List<String> parameters) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onConnectionReply(replyNumber, parameters);
		}
		eventDispatcher.onExecute(new ConnectionReplyEvent(replyNumber,
				parameters));
	}

	@Override
	public void onCommandReply(int replyNumber, List<String> parameters) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onCommandReply(replyNumber, parameters);
		}
		eventDispatcher
				.onExecute(new CommandReplyEvent(replyNumber, parameters));
	}
}
