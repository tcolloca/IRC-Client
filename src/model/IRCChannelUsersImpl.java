package model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.IRCFrameworkErrorException;
import util.IRCValues;

import command.InvalidCommandException;
import command.ModeCommand;

public class IRCChannelUsersImpl implements IRCChannelUsers, IRCValues {

	private IRCChannel channel;
	private List<IRCUser> normalUsers;
	private List<IRCUser> halfops;
	private List<IRCUser> ops;
	private List<IRCUser> superops; // Also known as admins or protected.
	private List<IRCUser> owners;
	private List<IRCUser> voiced;

	/**
	 * @param channel
	 *            Channel that holds these users.
	 * @throws IllegalArgumentException
	 *             If channel is null.
	 */
	IRCChannelUsersImpl(IRCChannel channel) {
		if (channel == null) {
			throw new IllegalArgumentException();
		}
		this.channel = channel;
	}

	@Override
	public Set<IRCUser> getUsers() {
		Set<IRCUser> users = new HashSet<IRCUser>();
		users.addAll(normalUsers);
		users.addAll(halfops);
		users.addAll(ops);
		users.addAll(superops);
		users.addAll(owners);
		users.addAll(voiced);
		return users;
	}

	@Override
	public List<IRCUser> getNormalUsers() {
		return normalUsers;
	}

	@Override
	public List<IRCUser> getHalfOpUsers() {
		return halfops;
	}

	@Override
	public List<IRCUser> getOpUsers() {
		return ops;
	}

	@Override
	public List<IRCUser> getSuperOpUsers() {
		return superops;
	}

	@Override
	public List<IRCUser> getOwnerUsers() {
		return owners;
	}

	@Override
	public List<IRCUser> getVoicedUsers() {
		return voiced;
	}

	@Override
	public void giveHalfOps(IRCUser user) {
		setMode(IRCChannelMode.HALFOP, user);
	}

	@Override
	public void giveOp(IRCUser user) {
		setMode(IRCChannelMode.OP, user);
	}

	@Override
	public void giveSuperOp(IRCUser user) {
		setMode(IRCChannelMode.ADMIN, user);
	}

	@Override
	public void giveOwner(IRCUser user) {
		setMode(IRCChannelMode.OWNER, user);
	}

	@Override
	public void giveVoice(IRCUser user) {
		setMode(IRCChannelMode.VOICE, user);
	}

	@Override
	public void removeHalfOps(IRCUser user) {
		unsetMode(IRCChannelMode.HALFOP, user);
	}

	@Override
	public void removeOp(IRCUser user) {
		unsetMode(IRCChannelMode.OP, user);
	}

	@Override
	public void removeSuperOp(IRCUser user) {
		unsetMode(IRCChannelMode.ADMIN, user);
	}

	@Override
	public void removeOwner(IRCUser user) {
		unsetMode(IRCChannelMode.OWNER, user);
	}

	@Override
	public void removeVoice(IRCUser user) {
		unsetMode(IRCChannelMode.VOICE, user);
	}

	private void setMode(IRCChannelMode mode, IRCUser user) {
		changeMode(mode, user, ADD);
	}

	private void unsetMode(IRCChannelMode mode, IRCUser user) {
		changeMode(mode, user, DEL);
	}

	private void changeMode(IRCChannelMode mode, IRCUser user, char action) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		try {
			channel.getClient().sendCommand(
					new ModeCommand(channel, new IRCModeAction(mode, action,
							user.getNickname())));
		} catch (InvalidCommandException e) {
			throw new IRCFrameworkErrorException();
		}
	}

	@Override
	public void addNormalUser(IRCUser user) {
		normalUsers.add(user);
	}
}
