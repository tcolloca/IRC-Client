package model;

import java.util.List;

/**
 * This interface allows to attempt to access and modify the masks of a channel.
 * 
 * @author Tomas
 */
public interface IRCChannelMasks {

	/**
	 * Returns the list of ban masks of the channel. Null if they are still
	 * unknown.
	 * 
	 * @return the list of ban masks of the channel.
	 */
	public List<IRCMask> getBanMasks();

	/**
	 * Returns the list of ban exception masks of the channel. Null if they are
	 * still unknown.
	 * 
	 * @return the list of ban exception masks of the channel.
	 */
	public List<IRCMask> getBanExceptionMasks();

	/**
	 * Returns the list of invite only masks of the channel. Null if they are
	 * still unknown.
	 * 
	 * @return the list of invite only masks of the channel.
	 */
	public List<IRCMask> getInviteOnlyMasks();

	/**
	 * Attempts to add the banMasks for the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banMasks is null, or any of the masks is null.
	 */
	public void addBanMasks(List<IRCMask> banMasks);

	/**
	 * Attempts to add the banMask for the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banMask is null.
	 */
	public void addBanMask(IRCMask banMask);

	/**
	 * Attempts to add the banExceptionMasks for the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banExceptionMasks is null, or any of the masks is null.
	 */
	public void addBanExceptionMasks(List<IRCMask> banExceptionMasks);

	/**
	 * Attempts to add the banExceptionMask for the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banExceptionMask is null.
	 */
	public void addBanExceptionMask(IRCMask banExceptionMask);

	/**
	 * Attempts to add the inviteOnlyMasks for the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if inviteOnlyMasks is null, or any of the masks is null.
	 */
	public void addInviteOnlyMasks(List<IRCMask> inviteOnlyMasks);

	/**
	 * Attempts to add the inviteOnlyMask for the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if inviteOnlyMask is null.
	 */
	public void addInviteOnlyMask(IRCMask inviteOnlyMask);
	
	/**
	 * Attempts to remove the banMasks from the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banMasks is null, or any of the masks is null.
	 */
	public void removeBanMasks(List<IRCMask> banMasks);

	/**
	 * Attempts to remove the banMask from the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banMask is null.
	 */
	public void removeBanMask(IRCMask banMask);

	/**
	 * Attempts to remove the banExceptionMasks from the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banExceptionMasks is null, or any of the masks is null.
	 */
	public void removeBanExceptionMasks(List<IRCMask> banExceptionMasks);

	/**
	 * Attempts to remove the banExceptionMask from the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if banExceptionMask is null.
	 */
	public void removeBanExceptionMask(IRCMask banExceptionMask);

	/**
	 * Attempts to remove the inviteOnlyMasks from the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if inviteOnlyMasks is null, or any of the masks is null.
	 */
	public void removeInviteOnlyMasks(List<IRCMask> inviteOnlyMasks);

	/**
	 * Attempts to remove the inviteOnlyMask from the channel.
	 * 
	 * @throws IllegalArgumentException
	 *             if inviteOnlyMask is null.
	 */
	public void removeInviteOnlyMask(IRCMask inviteOnlyMask);
}
