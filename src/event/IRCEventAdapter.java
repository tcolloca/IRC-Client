package event;

/**
 * An adapter for the IRCEventListener.
 * 
 * @author Tomas
 */
public class IRCEventAdapter implements IRCEventListener {

	@Override
	public void onConnect() {
	}

	@Override
	public void onPing(String server) {
	}

	@Override
	public void onLogin() {
	}

	@Override
	public void onNick(String user, String nickname) {
	}

	@Override
	public void onNotice(String msgtarget, String message) {
	}
}
