package model;

/**
 * This interface allows to access the users flags.
 * 
 * @author Tomas
 */
public interface IRCUserFlags {

	/**
	 * Returns true if the user is away. If it is unknown it will return false
	 * anyway.
	 * 
	 * @return true if the user is away.
	 */
	public boolean isAway();

	/**
	 * Returns true if the user is an IRC Operator. If it is unknown it will
	 * return false anyway.
	 * 
	 * @return true if the user is an IRC Operator.
	 */
	public boolean isIRCOp();

	/**
	 * Returns true if the user is marked as a Bot. If it is unknown it will
	 * return false anyway. (It can be a bot and return false anyway)
	 * 
	 * @return true if the user is is marked as a Bot.
	 */
	public boolean isBot();

	/**
	 * Returns true if the user is registered. If it is unknown it will return
	 * false anyway.
	 * 
	 * @return true if the user is registered.
	 */
	public boolean isRegistered();

	/**
	 * Returns true if the user is set as deaf. If it is unknown it will return
	 * false anyway.
	 * 
	 * @return true if the user is set as deaf.
	 */
	public boolean isDeaf();
}
