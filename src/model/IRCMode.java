package model;

public interface IRCMode {

	/**
	 * Returns the letter that represents the mode.
	 * 
	 * @return the letter that represents the mode.
	 */
	public char getLetter();

	/**
	 * Returns the amount of parameters that the mode requires.
	 * 
	 * @return the amount of parameters that the mode requires.
	 */
	public int getParamsAmount();
}
