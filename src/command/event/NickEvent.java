package command.event;

import model.IRCUser;
import util.IRCValues;
import event.IRCEventListener;

public class NickEvent implements IRCEvent, IRCValues {

	private IRCUser user;
	private String prevNickname;

	public NickEvent(IRCUser user, String prevFullUsername) {
		if (user == null || prevFullUsername == null) {
			throw new IllegalArgumentException();
		}
		this.user = user;
		this.prevNickname = prevFullUsername.split("" + USERNAME_INDICATOR)[0];
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onNick(user, prevNickname);
	}

}
