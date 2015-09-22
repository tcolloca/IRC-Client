package model;

import java.util.List;

/**
 * This interface allows to attempt to access and modify the modes of a channel.
 * 
 * @author Tomas
 */
public interface IRCChannelModes {

	/**
	 * Returns the list of modes of the channel. Null if they are still unknown.
	 * 
	 * @return the list of modes of the channel.
	 */
	public List<IRCChannelMode> getChannelModes();

	/**
	 * Returns true if colored messages are not allowed in the channel.
	 * 
	 * @return true if colored messages are not allowed in the channel.
	 */
	public boolean isNoColors();

	/**
	 * Returns true if the channel is set as invite only.
	 * 
	 * @return true if the channel is set as invite only.
	 */
	public boolean isInviteOnly();

	/**
	 * Returns true if the channel is set as moderated.
	 * 
	 * @return true if the channel is set as moderated.
	 */
	public boolean isModerated();

	/**
	 * Returns true if external messages are not allowed for the channel.
	 * 
	 * @return true if external messages are not allowed for the channel.
	 */
	public boolean isNoExternalMessages();

	/**
	 * Returns true if the channel is set as private.
	 * 
	 * @return true if the channel is set as private.
	 */
	public boolean isPrivate();

	/**
	 * Returns true if the channel is set as secret.
	 * 
	 * @return true if the channel is set as secret.
	 */
	public boolean isSecret();

	/**
	 * Returns true if the channel topic is locked.
	 * 
	 * @return true if the channel topic is locked.
	 */
	public boolean isTopicLocked();

	/**
	 * Returns true if the channel can only be accessed by users who have secure
	 * connections.
	 * 
	 * @return true if the channel can only be accessed by users who have secure
	 *         connections.
	 */
	public boolean isSecuredOnly();

	/**
	 * Returns true if the channel can only be accessed by IRC admins.
	 * 
	 * @return true if the channel can only be accessed by IRC admins.
	 */
	public boolean isIRCAdminOnly();

	/**
	 * Returns true if ctcp messages are not allowed in the channel.
	 * 
	 * @return true if ctcp messages are not allowed in the channel.
	 */
	public boolean isNoCTCPS();

	/**
	 * Returns true if profanities are censored from the channel.
	 * 
	 * @return true if profanities are censored from the channel.
	 */
	public boolean isCensoredProfanities();

	/**
	 * Returns true if knocks are not allowed for the channel.
	 * 
	 * @return true if knocks are not allowed for the channel.
	 */
	public boolean isNoKnocks();

	/**
	 * Returns true if nickname changes are not allowed while being in the
	 * channel.
	 * 
	 * @return true if nickname changes are not allowed while being in the
	 *         channel.
	 */
	public boolean isNoNicknameChanges();

	/**
	 * Returns true if kick actions are not allowed in the channel.
	 * 
	 * @return true if kick actions are not allowed in the channel.
	 */
	public boolean isNoKicks();

	/**
	 * Returns true if color from messages are stripped in the channel.
	 * 
	 * @return true if color from messages are stripped in the channel.
	 */
	public boolean isStripColor();

	/**
	 * Returns true if invitations to the channel are not allowed.
	 * 
	 * @return true if invitations to the channel are not allowed.
	 */
	public boolean isNoInvitations();

	/**
	 * Attempts to set the channel modes.
	 * 
	 * @param modes
	 * @param parameters
	 *            Parameters for the modes in the same order.
	 * @throws IllegalArgumentException
	 *             If modes is null, or any mode or parameter is null.
	 */
	public void setChannelModes(List<IRCChannelMode> modes, String... parameters);

	/**
	 * Attempts to set the channel mode.
	 * 
	 * @param modes
	 * @param parameter
	 *            If it has a parameter.
	 * @throws IllegalArgumentException
	 *             If mode is null, or any parameter is null.
	 */
	public void setChannelMode(IRCChannelMode mode, String... parameters);

	/**
	 * Attempts to unset the channel modes.
	 * 
	 * @param modes
	 * @param parameters
	 *            Parameters for the modes in the same order.
	 * @throws IllegalArgumentException
	 *             If modes is null, or any mode or parameter is null.
	 */
	public void unsetChannelModes(List<IRCChannelMode> modes, String... parameters);

	/**
	 * Attempts to unset the channel mode.
	 * 
	 * @param modes
	 * @param parameter
	 *            If it has a parameter.
	 * @throws IllegalArgumentException
	 *             If mode is null, or any parameter is null.
	 */
	public void unsetChannelMode(IRCChannelMode mode, String... parameters);

	/**
	 * Attempts to set the no colors mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoColors(boolean bool);

	/**
	 * Attempts to set the inviteOnly mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setInviteOnly(boolean bool);

	/**
	 * Attempts to set the moderated mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setModerated(boolean bool);

	/**
	 * Attempts to set the no external messages mode to the boolean received for
	 * the channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoExternalMessages(boolean bool);

	/**
	 * Attempts to set the private mode to the boolean received for the channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setPrivate(boolean bool);

	/**
	 * Attempts to set the secret mode to the boolean received for the channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setSecret(boolean bool);

	/**
	 * Attempts to set the topic locked mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setTopicLocked(boolean bool);

	/**
	 * Attempts to set the secured only mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setSecuredOnly(boolean bool);

	/**
	 * Attempts to set the irc admin only mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setIRCAdminOnly(boolean bool);

	/**
	 * Attempts to set the no ctcps mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoCTCPS(boolean bool);

	/**
	 * Attempts to set the censored profanities mode to the boolean received for
	 * the channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setCensoredProfanities(boolean bool);

	/**
	 * Attempts to set the no knocks mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoKnocks(boolean bool);

	/**
	 * Attempts to set the no nickname changes mode to the boolean received for
	 * the channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoNicknameChanges(boolean bool);

	/**
	 * Attempts to set the no kicks mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoKicks(boolean bool);

	/**
	 * Attempts to set the strip color mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setStripColor(boolean bool);

	/**
	 * Attempts to set the no invitations mode to the boolean received for the
	 * channel.
	 * 
	 * @param bool
	 *            New boolean value for the mode.
	 */
	public void setNoInvitations(boolean bool);

	void putMode(IRCChannelMode mode);

	void deleteMode(IRCChannelMode mode);
}
