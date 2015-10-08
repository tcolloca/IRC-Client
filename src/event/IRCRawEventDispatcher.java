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
import command.event.MessageEvent;
import command.event.ModeEvent;
import command.event.NickEvent;
import command.event.NoticeEvent;
import command.event.PartEvent;
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
	public void onNick(String prevFullUsername, String newNickname) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onNick(prevFullUsername, newNickname);
		}
		IRCUser user = dao.getUser(newNickname);
		eventDispatcher.onExecute(new NickEvent(user, prevFullUsername));
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
	public void onPart(String nickname, String channelName, String message) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onPart(nickname, channelName, message);
		}
		IRCUser user = dao.getUser(nickname);
		IRCChannel channel = dao.getChannel(channelName);
		eventDispatcher.onExecute(new PartEvent(user, channel, message));
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
	public void onMessage(String sender, String msgtarget, String message) {
		for (int i = 0; i < listeners.size(); i++) {
			IRCRawEventListener listener = listeners.get(i);
			listener.onMessage(sender, msgtarget, message);
		}
		IRCChannel channel = null;
		if (dao.hasChannel(msgtarget)) {
			channel = dao.getChannel(msgtarget);
		}
		IRCUser senderUser = dao.getOrAddUser(sender);
		eventDispatcher
				.onExecute(new MessageEvent(senderUser, channel, message));
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
