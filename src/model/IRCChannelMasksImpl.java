package model;

import java.util.List;

import util.IRCFrameworkErrorException;
import util.IRCValues;

import command.InvalidCommandException;
import command.ModeCommand;

public class IRCChannelMasksImpl implements IRCChannelMasks, IRCValues {

	private IRCChannel channel;
	private List<IRCMask> banMasks;
	private List<IRCMask> banExceptionMasks;
	private List<IRCMask> inviteOnlyMasks;

	/**
	 * @param channel
	 *            Channel that holds these masks.
	 * @throws IllegalArgumentException
	 *             If channel is null.
	 */
	IRCChannelMasksImpl(IRCChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		this.channel = channel;
	}

	@Override
	public List<IRCMask> getBanMasks() {
		return banMasks;
	}

	@Override
	public List<IRCMask> getBanExceptionMasks() {
		return banExceptionMasks;
	}

	@Override
	public List<IRCMask> getInviteOnlyMasks() {
		return inviteOnlyMasks;
	}

	@Override
	public void addBanMasks(List<IRCMask> banMasks) {
		addMasks(IRCChannelMode.BAN_MASK, banMasks);
	}

	@Override
	public void addBanMask(IRCMask banMask) {
		addMask(IRCChannelMode.BAN_MASK, banMask);
	}

	@Override
	public void addBanExceptionMasks(List<IRCMask> banExceptionMasks) {
		addMasks(IRCChannelMode.BAN_EXCEPTION_MASK, banExceptionMasks);
	}

	@Override
	public void addBanExceptionMask(IRCMask banExceptionMask) {
		addMask(IRCChannelMode.BAN_EXCEPTION_MASK, banExceptionMask);
	}

	@Override
	public void addInviteOnlyMasks(List<IRCMask> inviteOnlyMasks) {
		addMasks(IRCChannelMode.INVITE_ONLY_MASK, inviteOnlyMasks);
	}

	@Override
	public void addInviteOnlyMask(IRCMask inviteOnlyMask) {
		addMask(IRCChannelMode.INVITE_ONLY_MASK, inviteOnlyMask);
	}

	@Override
	public void removeBanMasks(List<IRCMask> banMasks) {
		removeMasks(IRCChannelMode.BAN_MASK, banMasks);
	}

	@Override
	public void removeBanMask(IRCMask banMask) {
		removeMask(IRCChannelMode.BAN_MASK, banMask);
	}

	@Override
	public void removeBanExceptionMasks(List<IRCMask> banExceptionMasks) {
		removeMasks(IRCChannelMode.BAN_EXCEPTION_MASK, banExceptionMasks);
	}

	@Override
	public void removeBanExceptionMask(IRCMask banExceptionMask) {
		removeMask(IRCChannelMode.BAN_EXCEPTION_MASK, banExceptionMask);
	}

	@Override
	public void removeInviteOnlyMasks(List<IRCMask> inviteOnlyMasks) {
		removeMasks(IRCChannelMode.INVITE_ONLY_MASK, inviteOnlyMasks);
	}

	@Override
	public void removeInviteOnlyMask(IRCMask inviteOnlyMask) {
		removeMask(IRCChannelMode.INVITE_ONLY_MASK, inviteOnlyMask);
	}

	private void addMasks(IRCChannelMode mode, List<IRCMask> masks) {
		changeMasks(mode, masks, ADD);
	}

	private void removeMasks(IRCChannelMode mode, List<IRCMask> masks) {
		changeMasks(mode, masks, DEL);
	}

	private void addMask(IRCChannelMode mode, IRCMask mask) {
		changeMask(mode, mask, ADD);
	}

	private void removeMask(IRCChannelMode mode, IRCMask mask) {
		changeMask(mode, mask, DEL);
	}

	private void changeMasks(IRCChannelMode mode, List<IRCMask> masks,
			char action) {
		if (masks == null) {
			throw new IllegalArgumentException();
		}
		for (IRCMask mask : masks) {
			changeMask(mode, mask, action);
		}
	}

	private void changeMask(IRCChannelMode mode, IRCMask mask, char action) {
		if (mask == null) {
			throw new IllegalArgumentException();
		}
		try {
			channel.getClient().sendCommand(
					new ModeCommand(channel, new IRCModeAction(mode, action,
							mask.getString())));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void putBanMask(IRCMask banMask) {
		banMasks.add(banMask);
	}

	@Override
	public void putBanExceptionMask(IRCMask banExceptionMask) {
		banExceptionMasks.add(banExceptionMask);
	}

	@Override
	public void putInviteOnlyMask(IRCMask inviteOnlyMask) {
		inviteOnlyMasks.add(inviteOnlyMask);
	}

	@Override
	public void deleteBanMask(IRCMask banMask) {
		banMasks.remove(banMask);
	}

	@Override
	public void deleteBanExceptionMask(IRCMask banExceptionMask) {
		banExceptionMasks.remove(banExceptionMask);
	}

	@Override
	public void deleteInviteOnlyMask(IRCMask inviteOnlyMask) {
		inviteOnlyMasks.remove(inviteOnlyMask);
	}

	@Override
	public void initializeMasks(List<IRCMask> banMasks,
			List<IRCMask> banExceptionMasks, List<IRCMask> inviteOnlyMasks) {
		this.banMasks = banMasks;
		this.banExceptionMasks = banExceptionMasks;
		this.inviteOnlyMasks = inviteOnlyMasks;
	}
}
