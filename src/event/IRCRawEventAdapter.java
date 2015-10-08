package event;

import java.util.List;

import model.IRCModeAction;

public class IRCRawEventAdapter implements IRCRawEventListener {

	@Override
	public void onPing(String server) {
	}

	@Override
	public void onNick(String prevFullUsername, String newNickname) {
	}

	@Override
	public void onNotice(String channelName, String message) {
	}

	@Override
	public void onJoin(String nickname, String channelName) {
	}
	
	@Override
	public void onPart(String nickname, String channelName, String message) {
	}

	@Override
	public void onMode(String name, List<IRCModeAction> modeActions) {
	}

	@Override
	public void onMessage(String sender, String msgtarget, String message) {
	}

	@Override
	public void onConnectionReply(int replyNumber, List<String> parameters) {
	}

	@Override
	public void onCommandReply(int replyNumber, List<String> parameters) {
	}
}
