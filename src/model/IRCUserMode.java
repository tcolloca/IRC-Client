package model;

/**
 * Represents an IRCUserMode.
 *
 * <p>
 * Service admin('a'), deaf ('d'), Rw GlobOps LocOps('g'), Registered('r'),
 * vhost('t'), secure connection('z'), no ctcps('T') are not standard and may
 * not work in another server other than Unreal.
 * </p>
 *
 * @author Tomas
 *
 */
public enum IRCUserMode implements IRCMode {

	SERVICE_ADMIN('a'), DEAF('d'), RW_GLOBOPS_LOCOPS('g'), HELPER('h'), INVISIBLE(
			'i'), GLOBAL_IRCOP('o'), HIDE_CHANNELS('p'), KICK_ARBITRERS_ONLY(
			'q'), REGISTERED('r'), SERVICE_NOTICE_MASK('s', 1), VHOST('t'), VICTIM(
			'v'), WALLOPS('w'), HIDDEN_HOSTNAME('x'), SECURE_CONNECTION('z'), SERVER_ADMIN(
			'A'), BOT('B'), CO_ADMIN('C'), CENSORED_USER_PROFANITIES('G'), HIDE_IRCOP_STATUS(
			'H'), NET_ADMIN('N'), LOCAL_IRCOP('O'), REGIS_ONLY('R'), SERVICE_PROTECT(
			'S'), NO_CTCPS('T'), WEBTV_USER('V'), SEE_WHOIS('W');

	private char letter;
	private int paramsAmount;

	private IRCUserMode(char letter) {
		this(letter, 0);
	}

	private IRCUserMode(char letter, int paramsAmount) {
		this.letter = letter;
		this.paramsAmount = paramsAmount;
	}

	/**
	 * Returns the IRCUserMode corresponding to the letter. Null if there is no
	 * IRCUserMode found.
	 * 
	 * @param letter
	 * @return the IRCUserMode corresponding to the letter. Null if there is no
	 *         IRCUserMode found.
	 */
	public static IRCMode getMode(char letter) {
		for (IRCUserMode mode : values()) {
			if (mode.letter == letter) {
				return mode;
			}
		}
		return null;
	}

	@Override
	public char getLetter() {
		return letter;
	}

	@Override
	public int getParamsAmount() {
		return paramsAmount;
	}
}
