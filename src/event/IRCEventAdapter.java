package event;

import model.IRCChannel;
import model.IRCUser;

/**
 * An adapter for the IRCEventListener.
 * 
 * @author Tomas
 */
public class IRCEventAdapter implements IRCEventListener {

	@Override
	public void onPing(String server) {
	}

	@Override
	public void onLogin() {
	}

	@Override
	public void onNick(IRCUser user, String newNickname, String prevNickname) {
	}

	@Override
	public void onNotice(IRCChannel channel, String message) {
	}

	@Override
	public void onJoin(IRCUser user, IRCChannel channel) {
	}
}
