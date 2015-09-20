package model;

public interface IRCUser {

	/**
	 * Returns the user's nickname.
	 * 
	 * @return the user's nickname.
	 */
	public String getNickname();

	/**
	 * Returns the user's username.
	 * 
	 * @return the user's username.
	 */
	public String getUsername();

	/**
	 * Returns the user's host.
	 * 
	 * @return the user's host.
	 */
	public String getHost();
}
