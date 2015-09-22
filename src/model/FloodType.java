package model;

public enum FloodType {

	CTCP('c'), JOIN('j'), KNOCK('k'), MESSAGE('m'), NICKCHANGE('n'), LINE('t');

	private char letter;

	private FloodType(char letter) {
		this.letter = letter;
	}

	/**
	 * Returns the FloodType corresponding to the letter. Null if there is no
	 * FloodType found.
	 * 
	 * @param letter
	 * @return the FloodType corresponding to the letter. Null if there is no
	 *         FloodType found.
	 */
	public static FloodType getFloodType(char letter) {
		for (FloodType floodType : values()) {
			if (floodType.letter == letter) {
				return floodType;
			}
		}
		return null;
	}

	public char getLetter() {
		return letter;
	}
}
