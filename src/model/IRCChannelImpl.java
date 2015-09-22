package model;

import java.util.List;

import client.IRCClient;
import event.IRCEventAdapter;
import util.IRCCommandReplyValues;
import util.IRCValues;

public class IRCChannelImpl extends IRCEventAdapter implements IRCChannel, IRCValues, IRCCommandReplyValues {

	private IRCClient client;

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
		client.addListener(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public IRCTopic getTopic() {
		return topic;
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public IRCFloodLimit getFloodLimit() {
		return floodLimit;
	}

	@Override
	public IRCChannelUsers getChannelUsers() {
		return users;
	}

	@Override
	public IRCChannelModes getChannelModes() {
		return modes;
	}

	@Override
	public IRCChannelMasks getChannelMasks() {
		return masks;
	}

	@Override
	public boolean hasKey() {
		return modes.getChannelModes().contains(IRCChannelMode.KEY);
	}

	@Override
	public boolean hasLimit() {
		return modes.getChannelModes().contains(IRCChannelMode.USERS_LIMIT);
	}

	@Override
	public void setKey(String key) {
		modes.setChannelMode(IRCChannelMode.KEY, key);
	}

	@Override
	public void setTopic(String message) {
		// TODO : Set topic
	}

	@Override
	public void setLimit(int limit) {
		modes.setChannelMode(IRCChannelMode.USERS_LIMIT, String.valueOf(limit));
	}

	@Override
	public void setFloodLimit(IRCFloodLimit floodLimit) {
		modes.setChannelMode(IRCChannelMode.FLOOD_LIMIT, floodLimit.getString());
	}

	@Override
	public void removeKey() {
		modes.unsetChannelMode(IRCChannelMode.KEY, key);
	}

	@Override
	public void removeTopic() {
		// TODO : Remove topic

	}

	@Override
	public void removeLimit() {
		modes.unsetChannelMode(IRCChannelMode.USERS_LIMIT);
	}

	@Override
	public void removeFloodLimit() {
		modes.unsetChannelMode(IRCChannelMode.FLOOD_LIMIT);
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
	public void onJoin(IRCUser user, IRCChannel channel) {
		if (user == null || channel == null) {
			throw new IllegalArgumentException();
		}
		if (channel.equals(this)) {
			users.addNormalUser(user);
		}
	}

	@Override
	public void onCommandReply(int replyNumber, List<String> parameters) {
		if (replyNumber == RPL_NAMREPLY) {
			System.out.println(parameters.get(0));
		}
	}

	@Override
	public void onMode(IRCChannel channel, List<IRCModeAction> channelModeActions) {
		if (channel == null || channelModeActions == null) {
			throw new IllegalArgumentException();
		}
		if (channel.equals(this)) {
			for (IRCModeAction modeAction : channelModeActions) {
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
		}
	}

	private void changeUser(IRCChannelMode mode, String[] parameters, char action) {
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

	private void changeMask(IRCChannelMode mode, String[] parameters, char action) {
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

	private void changeLimit(IRCChannelMode mode, String[] parameters, char action) {
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

	private void changeFloodLimit(IRCChannelMode mode, String[] parameters, char action) {
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
}
