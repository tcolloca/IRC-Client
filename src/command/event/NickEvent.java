package command.event;

import model.IRCUser;
import event.IRCEventListener;

public class NickEvent implements IRCEvent {

	private IRCUser user;
	private String prevNickname;

	public NickEvent(IRCUser user, String prevNickname) {
		if (user == null || prevNickname == null) {
			throw new IllegalArgumentException();
		}
		this.user = user;
		this.prevNickname = prevNickname;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		listener.onNick(user, prevNickname);
	}

}
