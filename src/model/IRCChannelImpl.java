package model;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import util.IRCCommandReplyValues;
import util.IRCValues;
import client.IRCClient;
import client.IRCClientImpl;
import event.IRCRawEventAdapter;

public class IRCChannelImpl extends IRCRawEventAdapter implements IRCChannel,
		IRCValues, IRCCommandReplyValues {

	private IRCClient client;

	private ReadWriteLock rwlock = new ReentrantReadWriteLock();

	private String name;
	private String key;
	private IRCTopic topic;
	private long creationTime;

	private IRCChannelUsers users;
	private IRCChannelMasks masks;
	private IRCChannelModes modes;

	private int limit;
	private IRCFloodLimit floodLimit;

	/**
	 * @param client
	 *            IRCClient that has a reference to this channel.
	 * @param name
	 *            Name of the channel.
	 * @throws IllegalArgumentException
	 *             If client or name are null.
	 */
	public IRCChannelImpl(IRCClient client, String name) {
		this(client, name, null);
	}

	/**
	 * @param client
	 *            IRCClient that has a reference to this channel.
	 * @param name
	 *            Name of the channel.
	 * @param key
	 *            Key of the channel.
	 * @throws IllegalArgumentException
	 *             If client or name are null.
	 */
	public IRCChannelImpl(IRCClient client, String name, String key) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		this.client = client;
		this.name = name;
		this.key = key;
		this.users = new IRCChannelUsersImpl(this);
		this.modes = new IRCChannelModesImpl(this);
		this.masks = new IRCChannelMasksImpl(this);
		((IRCClientImpl) client).addRawListener(this);
	}

	@Override
	public void send(String message) {
		if (message == null) {
			throw new IllegalArgumentException();
		}
		client.sendChannelMessage(this, message);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getKey() {
		rwlock.readLock().lock();
		try {
			return key;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public IRCTopic getTopic() {
		rwlock.readLock().lock();
		try {
			return topic;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public long getCreationTime() {
		rwlock.readLock().lock();
		try {
			return creationTime;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public int getLimit() {
		rwlock.readLock().lock();
		try {
			return limit;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public IRCFloodLimit getFloodLimit() {
		rwlock.readLock().lock();
		try {
			return floodLimit;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public IRCChannelUsers getChannelUsers() {
		rwlock.readLock().lock();
		try {
			return users;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public IRCChannelModes getChannelModes() {
		rwlock.readLock().lock();
		try {
			return modes;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public IRCChannelMasks getChannelMasks() {
		rwlock.readLock().lock();
		try {
			return masks;
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public boolean hasKey() {
		rwlock.readLock().lock();
		try {
			return modes.getChannelModes().contains(IRCChannelMode.KEY);
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public boolean hasLimit() {
		rwlock.readLock().lock();
		try {
			return modes.getChannelModes().contains(IRCChannelMode.USERS_LIMIT);
		} finally {
			rwlock.readLock().unlock();
		}
	}

	@Override
	public void setKey(String key) {
		rwlock.writeLock().lock();
		try {
			modes.setChannelMode(IRCChannelMode.KEY, key);
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void setTopic(String message) {
		rwlock.writeLock().lock();
		try {
			// TODO : Set topic
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void setLimit(int limit) {
		rwlock.writeLock().lock();
		try {
			modes.setChannelMode(IRCChannelMode.USERS_LIMIT,
					String.valueOf(limit));
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void setFloodLimit(IRCFloodLimit floodLimit) {
		rwlock.writeLock().lock();
		try {
			modes.setChannelMode(IRCChannelMode.FLOOD_LIMIT,
					floodLimit.getString());
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void removeKey() {
		rwlock.writeLock().lock();
		try {
			modes.unsetChannelMode(IRCChannelMode.KEY, key);
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void removeTopic() {
		rwlock.writeLock().lock();
		try {
			// TODO : Remove topic
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void removeLimit() {
		rwlock.writeLock().lock();
		try {
			modes.unsetChannelMode(IRCChannelMode.USERS_LIMIT);
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public void removeFloodLimit() {
		rwlock.writeLock().lock();
		try {
			modes.unsetChannelMode(IRCChannelMode.FLOOD_LIMIT);
		} finally {
			rwlock.writeLock().unlock();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IRCChannelImpl other = (IRCChannelImpl) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public IRCClient getClient() {
		return client;
	}

	@Override
	public void onJoin(String nickname, String channelName) {
		if (nickname == null || channelName == null) {
			throw new IllegalArgumentException();
		}
		if (channelName.equals(getName()) && users.getNormalUsers() != null) {
			rwlock.writeLock().lock();
			try {
				users.addNormalUser(((IRCClientImpl) client)
						.getOrAddUser(nickname));
			} finally {
				rwlock.writeLock().unlock();
			}
		}
		// TODO : Remove
		for (IRCUser user : users.getUsers()) {
			System.out.println(user);
		}
	}

	@Override
	public void onCommandReply(int replyNumber, List<String> parameters) {
		if (replyNumber == RPL_NAMREPLY) {
			String channelName = parameters.get(1);
			if (channelName.equals(this.name)) {
				String usersList = parameters.get(2);
				String[] usersStrings = usersList.split("" + EMPTY_SPACE);
				initializeUsers(usersStrings);
				// TODO : Remove
				for (IRCUser user : users.getUsers()) {
					System.out.println(user);
				}
			}
		}
	}

	@Override
	public void onMode(String name, List<IRCModeAction> modeActions) {
		if (name == null || modeActions == null) {
			throw new IllegalArgumentException();
		}
		if (name.equals(getName())) {
			rwlock.writeLock().lock();
			try {
				for (IRCModeAction modeAction : modeActions) {
					if (modeAction == null) {
						throw new IllegalArgumentException();
					}
					IRCChannelMode mode = (IRCChannelMode) modeAction.getMode();
					String[] parameters = modeAction.getParameters();
					char action = modeAction.getAction();

					switch (mode) {
					case VOICE:
					case HALFOP:
					case OP:
					case ADMIN:
					case OWNER:
						changeUser(mode, parameters, action);
						break;
					case BAN_MASK:
					case BAN_EXCEPTION_MASK:
					case INVITE_ONLY_MASK:
						changeMask(mode, parameters, action);
						break;
					case KEY:
						changeKey(mode, parameters, action);
						changeMode(mode, action);
						break;
					case USERS_LIMIT:
						changeLimit(mode, parameters, action);
						changeMode(mode, action);
						break;
					case FLOOD_LIMIT:
						changeFloodLimit(mode, parameters, action);
						changeMode(mode, action);
						break;
					default:
						changeMode(mode, action);
						break;
					}
				}
			} finally {
				rwlock.writeLock().unlock();
			}
		}
	}

	private void changeUser(IRCChannelMode mode, String[] parameters,
			char action) {
		if (parameters.length < 1) {
			throw new IllegalArgumentException();
		}
		String userName = parameters[0];
		IRCUser user = client.getUser(userName);
		if (user == null) {
			throw new IllegalArgumentException();
		}
		switch (mode) {
		case VOICE:
			if (action == ADD) {
				users.addVoicedUser(user);
			} else if (action == DEL) {
				users.removeVoicedUser(user);
			}
			break;
		case HALFOP:
			if (action == ADD) {
				users.addHalfOpUser(user);
			} else if (action == DEL) {
				users.removeHalfOpUser(user);
			}
			break;
		case OP:
			if (action == ADD) {
				users.addOpUser(user);
			} else if (action == DEL) {
				users.removeOpUser(user);
			}
			break;
		case ADMIN:
			if (action == ADD) {
				users.addSuperOpUser(user);
			} else if (action == DEL) {
				users.removeSuperOpUser(user);
			}
			break;
		case OWNER:
			if (action == ADD) {
				users.addOwnerUser(user);
			} else if (action == DEL) {
				users.removeOwnerUser(user);
			}
			break;
		default:
			throw new IllegalArgumentException();

		}
	}

	private void changeMask(IRCChannelMode mode, String[] parameters,
			char action) {
		if (parameters.length < 1) {
			throw new IllegalArgumentException();
		}
		String maskName = parameters[0];
		IRCMask mask = new IRCMask(maskName);
		switch (mode) {
		case BAN_MASK:
			if (action == ADD) {
				masks.putBanMask(mask);
			} else if (action == DEL) {
				masks.deleteBanMask(mask);
			}
			break;
		case BAN_EXCEPTION_MASK:
			if (action == ADD) {
				masks.putBanExceptionMask(mask);
			} else if (action == DEL) {
				masks.deleteBanExceptionMask(mask);
			}
			break;
		case INVITE_ONLY_MASK:
			if (action == ADD) {
				masks.putInviteOnlyMask(mask);
			} else if (action == DEL) {
				masks.deleteInviteOnlyMask(mask);
			}
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	private void changeKey(IRCChannelMode mode, String[] parameters, char action) {
		if (parameters.length < 1) {
			throw new IllegalArgumentException();
		}
		String key = parameters[0];
		if (action == ADD) {
			this.key = key;
		} else if (action == DEL) {
			this.key = null;
		}
	}

	private void changeLimit(IRCChannelMode mode, String[] parameters,
			char action) {
		if (parameters.length < 1) {
			throw new IllegalArgumentException();
		}
		try {
			int limit = Integer.valueOf(parameters[0]);
			if (action == ADD) {
				this.limit = limit;
			} else if (action == DEL) {
				this.limit = -1;
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException();
		}
	}

	private void changeFloodLimit(IRCChannelMode mode, String[] parameters,
			char action) {
		if (parameters.length < 1) {
			throw new IllegalArgumentException();
		}
		String floodLimitRegex = parameters[0];
		IRCFloodLimit floodLimit = new IRCFloodLimit(floodLimitRegex);
		if (action == ADD) {
			this.floodLimit = floodLimit;
		} else if (action == DEL) {
			this.floodLimit = null;
		}
	}

	private void changeMode(IRCChannelMode mode, char action) {
		if (action == ADD) {
			modes.putMode(mode);
		} else if (action == DEL) {
			modes.deleteMode(mode);
		}
	}

	private void initializeUsers(String[] usersStrings) {
		rwlock.writeLock().lock();
		try {
			users.initialize();
			for (String userString : usersStrings) {
				char firstChar = userString.charAt(0);
				IRCUser user;
				IRCClientImpl clientImpl = (IRCClientImpl) client;
				switch (firstChar) {
				case HALFOP_CHAR:
					user = clientImpl.getOrAddUser(userString.substring(1,
							userString.length()));
					users.addHalfOpUser(user);
					break;
				case OP_CHAR:
					user = clientImpl.getOrAddUser(userString.substring(1,
							userString.length()));
					users.addOpUser(user);
					break;
				case SUPEROP_CHAR:
					user = clientImpl.getOrAddUser(userString.substring(1,
							userString.length()));
					users.addSuperOpUser(user);
					break;
				case OWNER_CHAR:
					user = clientImpl.getOrAddUser(userString.substring(1,
							userString.length()));
					users.addOwnerUser(user);
					break;
				case VOICED_CHAR:
					user = clientImpl.getOrAddUser(userString.substring(1,
							userString.length()));
					users.addVoicedUser(user);
					break;
				default:
					user = clientImpl.getOrAddUser(userString);
					users.addNormalUser(user);
					break;
				}
			}
		} finally {
			rwlock.writeLock().unlock();
		}
	}
}
