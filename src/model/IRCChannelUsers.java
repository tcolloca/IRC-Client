package model;

import java.util.List;
import java.util.Set;

/**
 * This interface allows to attempt to access and modify the users permissions
 * over a channel.
 * 
 * @author Tomas
 */
public interface IRCChannelUsers {

	/**
	 * Returns the set of all the users in the channel. Null if they are still
	 * unknown.
	 * 
	 * @returns the set of all the users in the channel.
	 */
	public Set<IRCUser> getUsers();

	/**
	 * Returns the list of users without any additional power over the channel.
	 * Null if they are still unknown.
	 * 
	 * @return the list of users without any additional power over the channel.
	 */
	public List<IRCUser> getNormalUsers();

	/**
	 * Returns the list of users with half ops over the channel. Null if they
	 * are still unknown.
	 * 
	 * @return the list of users with half ops over the channel.
	 */
	public List<IRCUser> getHalfOpUsers();

	/**
	 * Returns the list of users with op over the channel. Null if they are
	 * still unknown.
	 * 
	 * @return the list of users with op over the channel.
	 */
	public List<IRCUser> getOpUsers();

	/**
	 * Returns the list of users with sop (a.k.a. admins or protected) over the
	 * channel. Null if they are still unknown.
	 * 
	 * @return the list of users with sop (a.k.a. admins or protected) over the
	 *         channel.
	 */
	public List<IRCUser> getSuperOpUsers();

	/**
	 * Returns the list of owner users of the channel. Null if they are still
	 * unknown.
	 * 
	 * @return the list of owner users of the channel.
	 */
	public List<IRCUser> getOwnerUsers();

	/**
	 * Returns the list of voiced users of the channel. Null if they are still
	 * unknown.
	 * 
	 * @return the list of voiced users of the channel.
	 */
	public List<IRCUser> getVoicedUsers();

	/**
	 * Attempts to give halfOps for the channel to the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void giveHalfOps(IRCUser user);

	/**
	 * Attempts to give op for the channel to the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void giveOp(IRCUser user);

	/**
	 * Attempts to give superOp for the channel to the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void giveSuperOp(IRCUser user);

	/**
	 * Attempts to give owner status for the channel to the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void giveOwner(IRCUser user);

	/**
	 * Attempts to give voice for the channel to the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void giveVoice(IRCUser user);

	/**
	 * Attempts to remove halfOps for the channel from the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void removeHalfOps(IRCUser user);

	/**
	 * Attempts to remove op for the channel from the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void removeOp(IRCUser user);

	/**
	 * Attempts to remove superOp for the channel from the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void removeSuperOp(IRCUser user);

	/**
	 * Attempts to remove owner status for the channel from the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void removeOwner(IRCUser user);

	/**
	 * Attempts to remove voice for the channel from the user.
	 * 
	 * @throws IllegalArgumentException
	 *             If user is null.
	 */
	public void removeVoice(IRCUser user);

	/**
	 * Adds the user to the channel.
	 * 
	 * @param user
	 *            User being added to the channel.
	 */
	void addNormalUser(IRCUser user);
}
