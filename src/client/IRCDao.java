package client;

import java.util.HashMap;
import java.util.Map;

public class IRCDao {

	private Map<String, IRCUser> users;
	private Map<String, IRCChannel> channels;
	
	IRCDao () {
		users = new HashMap<String, IRCUser>();
		channels = new HashMap<String, IRCChannel>();
	}
	
	private IRCUser getUser(String nickname) {
		if (!users.containsKey(nickname)) {
			//users.put(nickname, newUser(nickname));
			// TODO 
		}
		return users.get(nickname);
	}
}
