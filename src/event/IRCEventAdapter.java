package event;

import java.util.List;

import model.IRCChannel;
import model.IRCModeAction;
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
	public void onNick(IRCUser user, String prevNickname) {
	}

	@Override
	public void onNotice(IRCChannel channel, String message) {
	}

	@Override
	public void onJoin(IRCUser user, IRCChannel channel) {
	}

	@Override
	public void onPart(IRCUser user, IRCChannel channel, String message) {
	}

	@Override
	public void onMode(IRCUser user, List<IRCModeAction> userModeActions) {
	}

	@Override
	public void onMode(IRCChannel channel,
			List<IRCModeAction> channelModeActions) {
	}

	@Override
	public void onMessage(IRCUser sender, String message) {
	}

	@Override
	public void onChannelMessage(IRCUser sender, IRCChannel channel,
			String message) {
	}

	@Override
	public void onPrivateMessage(IRCUser sender, String message) {
	}

	@Override
	public void onConnectionReply(int replyNumber, List<String> parameters) {
	}

	@Override
	public void onCommandReply(int replyNumber, List<String> parameters) {
	}
}
