package command.event;

import java.util.List;

import model.IRCChannel;
import model.IRCModeAction;
import model.IRCUser;
import event.IRCEventListener;

public class ModeEvent implements IRCEvent {

	private IRCUser user;
	private IRCChannel channel;
	private List<IRCModeAction> modeActions;

	public ModeEvent(IRCUser user, List<IRCModeAction> modeActions) {
		if (user == null || modeActions == null) {
			throw new IllegalArgumentException();
		}
		this.user = user;
		this.modeActions = modeActions;
	}

	public ModeEvent(IRCChannel channel, List<IRCModeAction> modeActions) {
		if (channel == null || modeActions == null) {
			throw new IllegalArgumentException();
		}
		this.channel = channel;
		this.modeActions = modeActions;
	}

	@Override
	public void onExecute(IRCEventListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException();
		}
		if (user != null) {
			listener.onMode(user, modeActions);
		} else {
			listener.onMode(channel, modeActions);
		}
	}

}
