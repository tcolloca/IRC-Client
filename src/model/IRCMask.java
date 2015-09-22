package model;

import util.IRCValues;

public class IRCMask implements IRCValues {

	private String nicknameRegex;
	private String usernameRegex;
	private String hostRegex;

	/**
	 * @param mask
	 * @throws IllegalArgumentException
	 *             If it's not a valid mask (A valid mask has the form
	 *             &lt;nicknameRegex&gt;!&lt;usernameRegex&gt;@&lt;hostRegex&gt;) or null.
	 */
	public IRCMask(String mask) {
		// TODO : Improve matching.
		if (mask == null || !mask.contains("" + USERNAME_INDICATOR)
				|| !mask.contains("" + HOST_INDICATOR)) {
			throw new IllegalArgumentException();
		}
		String[] regexs = mask.split("" + USERNAME_INDICATOR);
		nicknameRegex = regexs[0];
		regexs = regexs[1].split("" + HOST_INDICATOR);
		usernameRegex = regexs[0];
		hostRegex = regexs[1];
	}

	public IRCMask(String nicknameRegex, String usernameRegex, String hostRegex) {
		super();
		this.nicknameRegex = nicknameRegex;
		this.usernameRegex = usernameRegex;
		this.hostRegex = hostRegex;
	}

	/**
	 * Returns a String that represents the instance for an IRC message.
	 * 
	 * @return a String that represents the instance for an IRC message.
	 */
	public String getString() {
		return (nicknameRegex != null ? nicknameRegex : "*")
				+ USERNAME_INDICATOR
				+ (usernameRegex != null ? usernameRegex : "*")
				+ HOST_INDICATOR + (hostRegex != null ? hostRegex : "*");
	}
}
