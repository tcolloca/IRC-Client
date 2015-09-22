package model;

import java.util.List;

import util.IRCFrameworkErrorException;
import util.IRCValues;

import command.InvalidCommandException;
import command.ModeCommand;

public class IRCChannelModesImpl implements IRCChannelModes, IRCValues {

	private IRCChannel channel;
	private List<IRCChannelMode> modes;

	/**
	 * @param channel
	 *            Channel that holds these modes.
	 * @throws IllegalArgumentException
	 *             If channel is null.
	 */
	IRCChannelModesImpl(IRCChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		this.channel = channel;
	}

	@Override
	public List<IRCChannelMode> getChannelModes() {
		return modes;
	}

	@Override
	public boolean isNoColors() {
		return modes.contains(IRCChannelMode.NO_COLOR);
	}

	@Override
	public boolean isInviteOnly() {
		return modes.contains(IRCChannelMode.INVITE_ONLY);
	}

	@Override
	public boolean isModerated() {
		return modes.contains(IRCChannelMode.MODERATED);
	}

	@Override
	public boolean isNoExternalMessages() {
		return modes.contains(IRCChannelMode.NO_EXTERNAL_MSGS);
	}

	@Override
	public boolean isPrivate() {
		return modes.contains(IRCChannelMode.PRIVATE);
	}

	@Override
	public boolean isSecret() {
		return modes.contains(IRCChannelMode.SECRET);
	}

	@Override
	public boolean isTopicLocked() {
		return modes.contains(IRCChannelMode.TOPIC_LOCK);
	}

	@Override
	public boolean isSecuredOnly() {
		return modes.contains(IRCChannelMode.SECURED_ONLY);
	}

	@Override
	public boolean isIRCAdminOnly() {
		return modes.contains(IRCChannelMode.IRC_ADMINS_ONLY);
	}

	@Override
	public boolean isNoCTCPS() {
		return modes.contains(IRCChannelMode.CTCPS_NOT_ALLOWED);
	}

	@Override
	public boolean isCensoredProfanities() {
		return modes.contains(IRCChannelMode.CENSORED_PROFANITIES);
	}

	@Override
	public boolean isNoKnocks() {
		return modes.contains(IRCChannelMode.NO_KNOCK);
	}

	@Override
	public boolean isNoNicknameChanges() {
		return modes.contains(IRCChannelMode.NO_NICK_CHANGE);
	}

	@Override
	public boolean isNoKicks() {
		return modes.contains(IRCChannelMode.NO_KICK);
	}

	@Override
	public boolean isStripColor() {
		return modes.contains(IRCChannelMode.STRIP_COLOR);
	}

	@Override
	public boolean isNoInvitations() {
		return modes.contains(IRCChannelMode.NO_INVITATIONS_ALLOWED);
	}

	@Override
	public void setChannelModes(List<IRCChannelMode> modes,
			String... parameters) {
		changeModes(modes, ADD, parameters);
	}

	@Override
	public void setChannelMode(IRCChannelMode mode, String... parameters) {
		changeMode(mode, ADD, parameters);
	}

	@Override
	public void unsetChannelModes(List<IRCChannelMode> modes,
			String... parameters) {
		changeModes(modes, DEL, parameters);
	}

	@Override
	public void unsetChannelMode(IRCChannelMode mode, String... parameters) {
		changeMode(mode, DEL, parameters);
	}

	@Override
	public void setNoColors(boolean bool) {
		changeMode(IRCChannelMode.NO_COLOR, bool ? ADD : DEL);
	}

	@Override
	public void setInviteOnly(boolean bool) {
		changeMode(IRCChannelMode.INVITE_ONLY, bool ? ADD : DEL);
	}

	@Override
	public void setModerated(boolean bool) {
		changeMode(IRCChannelMode.MODERATED, bool ? ADD : DEL);
	}

	@Override
	public void setNoExternalMessages(boolean bool) {
		changeMode(IRCChannelMode.NO_EXTERNAL_MSGS, bool ? ADD : DEL);
	}

	@Override
	public void setPrivate(boolean bool) {
		changeMode(IRCChannelMode.PRIVATE, bool ? ADD : DEL);
	}

	@Override
	public void setSecret(boolean bool) {
		changeMode(IRCChannelMode.SECRET, bool ? ADD : DEL);
	}

	@Override
	public void setTopicLocked(boolean bool) {
		changeMode(IRCChannelMode.TOPIC_LOCK, bool ? ADD : DEL);
	}

	@Override
	public void setSecuredOnly(boolean bool) {
		changeMode(IRCChannelMode.SECURED_ONLY, bool ? ADD : DEL);
	}

	@Override
	public void setIRCAdminOnly(boolean bool) {
		changeMode(IRCChannelMode.IRC_ADMINS_ONLY, bool ? ADD : DEL);
	}

	@Override
	public void setNoCTCPS(boolean bool) {
		changeMode(IRCChannelMode.CTCPS_NOT_ALLOWED, bool ? ADD : DEL);
	}

	@Override
	public void setCensoredProfanities(boolean bool) {
		changeMode(IRCChannelMode.CENSORED_PROFANITIES, bool ? ADD : DEL);
	}

	@Override
	public void setNoKnocks(boolean bool) {
		changeMode(IRCChannelMode.NO_KNOCK, bool ? ADD : DEL);
	}

	@Override
	public void setNoNicknameChanges(boolean bool) {
		changeMode(IRCChannelMode.NO_NICK_CHANGE, bool ? ADD : DEL);
	}

	@Override
	public void setNoKicks(boolean bool) {
		changeMode(IRCChannelMode.NO_KICK, bool ? ADD : DEL);
	}

	@Override
	public void setStripColor(boolean bool) {
		changeMode(IRCChannelMode.STRIP_COLOR, bool ? ADD : DEL);
	}

	@Override
	public void setNoInvitations(boolean bool) {
		changeMode(IRCChannelMode.NO_INVITATIONS_ALLOWED, bool ? ADD : DEL);
	}

	private void changeModes(List<IRCChannelMode> modes, char action,
			String... parameters) {
		if (modes == null) {
			throw new IllegalArgumentException();
		}
		for (IRCChannelMode mode : modes) {
			changeMode(mode, action, parameters);
		}
	}

	private void changeMode(IRCChannelMode mode, char action,
			String... parameters) {
		if (parameters == null || mode == null) {
			throw new IllegalArgumentException();
		}
		for (String parameter : parameters) {
			if (parameter == null) {
				throw new IllegalArgumentException();
			}
		}
		try {
			channel.getClient().sendCommand(
					new ModeCommand(channel, new IRCModeAction(mode, action,
							parameters)));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void putMode(IRCChannelMode mode) {
		modes.add(mode);
	}
	
	@Override
	public void deleteMode(IRCChannelMode mode) {
		modes.remove(mode);
	}
}
