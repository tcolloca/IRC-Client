package model;

import java.util.ArrayList;
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

	@Override
	public void addHalfOpUser(IRCUser user) {
		halfops.add(user);
	}

	@Override
	public void addOpUser(IRCUser user) {
		ops.add(user);
	}

	@Override
	public void addSuperOpUser(IRCUser user) {
		superops.add(user);
	}

	@Override
	public void addOwnerUser(IRCUser user) {
		owners.add(user);
	}

	@Override
	public void addVoicedUser(IRCUser user) {
		voiced.add(user);
	}

	@Override
	public void removeNormalUser(IRCUser user) {
		normalUsers.remove(user);
	}

	@Override
	public void removeHalfOpUser(IRCUser user) {
		halfops.remove(user);
	}

	@Override
	public void removeOpUser(IRCUser user) {
		ops.remove(user);
	}

	@Override
	public void removeSuperOpUser(IRCUser user) {
		superops.remove(user);
	}

	@Override
	public void removeOwnerUser(IRCUser user) {
		owners.remove(user);
	}

	@Override
	public void removeVoicedUser(IRCUser user) {
		voiced.remove(user);
	}

	@Override
	public void initializeNormalUsers(List<IRCUser> users) {
		this.normalUsers = users;
	}

	@Override
	public void initializeHalfOpUsers(List<IRCUser> users) {
		this.halfops = users;
	}

	@Override
	public void initializeOpUsers(List<IRCUser> users) {
		this.ops = users;
	}

	@Override
	public void initializeSuperOpUsers(List<IRCUser> users) {
		this.superops = users;
	}

	@Override
	public void initializeOwnerUsers(List<IRCUser> users) {
		this.owners = users;
	}

	@Override
	public void initializeVoicedUsers(List<IRCUser> users) {
		this.voiced = users;
	}

	@Override
	public void initialize() {
		initializeNormalUsers(new ArrayList<IRCUser>());
		initializeHalfOpUsers(new ArrayList<IRCUser>());
		initializeOpUsers(new ArrayList<IRCUser>());
		initializeSuperOpUsers(new ArrayList<IRCUser>());
		initializeOwnerUsers(new ArrayList<IRCUser>());
		initializeVoicedUsers(new ArrayList<IRCUser>());
	}
}
