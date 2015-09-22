package model;

/**
 * Represents a user power level over a channel.
 * 
 * @author Tomas
 */
public enum IRCUserLevel {

	VOICE('+'), HALFOP('%'), OP('@'), ADMIN('&'), OWNER('~');

	private char symbol;

	private IRCUserLevel(char symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the IRCUserLevel corresponding to the symbol. Null if there is no
	 * IRCUserLevel found.
	 * 
	 * @param symbol
	 * @return the IRCUserLevel corresponding to the symbol. Null if there is no
	 *         IRCUserLevel found.
	 */
	public static IRCUserLevel getUserLevel(char symbol) {
		for (IRCUserLevel level : values()) {
			if (level.symbol == symbol) {
				return level;
			}
		}
		return null;
	}

	public char getSymbol() {
		return symbol;
	}
}
