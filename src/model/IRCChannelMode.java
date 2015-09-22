package model;

/**
 * Represents an IRCChannelMode.
 *
 * <p>
 * Admin('a'), Joins_flood_limit('j'), Owner('q'), Register('r'),
 * Auditorium('u'), Link('L'), Talk_regis_only('M'), Oper_only('O'),
 * Join_regis_only('R'), notice_chanops_only('T') are not standard and may not
 * work in another server other than Unreal. <br />
 * Auditorium shows only chanops for the NAMES and WHO commands, unless the
 * caller is a chanop or above.
 * </p>
 *
 * @author Tomas
 *
 */
public enum IRCChannelMode implements IRCMode {

	ADMIN('a', 1), BAN_MASK('b', 1), NO_COLOR('c'), BAN_EXCEPTION_MASK('e', 1), FLOOD_LIMIT(
			'f', 1), HALFOP('h', 1), INVITE_ONLY('i', 1), JOINS_FLOOD_LIMIT(
			'j', 1), KEY('k', 1), USERS_LIMIT('l', 1), MODERATED('m'), NO_EXTERNAL_MSGS(
			'n'), OP('o', 1), PRIVATE('p'), OWNER('q', 1), REGISTERED('r'), SECRET(
			's'), TOPIC_LOCK('t'), AUDITORIUM('u'), VOICE('v'), SECURED_ONLY(
			'z'), IRC_ADMINS_ONLY('A'), CTCPS_NOT_ALLOWED('C'), CENSORED_PROFANITIES(
			'G'), INVITE_ONLY_MASK('I', 1), NO_KNOCK('K'), LINK('L', 1), TALK_REGIS_ONLY(
			'M'), NO_NICK_CHANGE('N'), IRC_OPS_ONLY('O'), NO_KICK('Q'), JOIN_REGIS_ONLY(
			'R'), STRIP_COLOR('S'), NOTICE_CHANOPS_ONLY('T'), NO_INVITATIONS_ALLOWED(
			'V');

	private char letter;
	private int paramsAmount;

	private IRCChannelMode(char letter) {
		this(letter, 0);
	}

	private IRCChannelMode(char letter, int paramsAmount) {
		this.letter = letter;
		this.paramsAmount = paramsAmount;
	}

	/**
	 * Returns the IRCChannelMode corresponding to the letter. Null if there is
	 * no IRCChannelMode found.
	 * 
	 * @param letter
	 * @return the IRCChannelMode corresponding to the letter. Null if there is
	 *         no IRCChannelMode found.
	 */
	public static IRCMode getMode(char letter) {
		for (IRCChannelMode mode : values()) {
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
